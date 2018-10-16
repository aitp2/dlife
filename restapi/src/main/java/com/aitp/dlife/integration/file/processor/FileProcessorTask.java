package com.aitp.dlife.integration.file.processor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.aitp.dlife.integration.file.dto.ExcelImportErrorData;
import com.aitp.dlife.integration.util.ExcelWriteHelper;
import com.aitp.dlife.integration.util.FileMoveHelper;
import com.aitp.dlife.repository.UserRepository;
import com.aitp.dlife.service.UserService;
import com.aitp.dlife.web.rest.errors.LoginAlreadyUsedException;
import com.aitp.dlife.web.rest.vm.ManagedUserVM;

public class FileProcessorTask {

	private final Logger LOGGER = LoggerFactory.getLogger(FileProcessorTask.class);

	private static final String HEADER_FILE_NAME = "file_name";
	private static final String FILE_ORIGINAL_FILE = "file_originalFile";

	@Autowired
	private File inboundRootDirectory;

	@Autowired
	private File inboundProcessingDirectory;

	@Autowired
	private File inboundErrorDirectory;

	@Autowired
	private File inboundArchiveDirectory;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	/**
	 * process the import task
	 *
	 * @param msg
	 */
	public void process(Message<List<List<Map<String, String>>>> msg) {
		File fileOriginalFile = (File) msg.getHeaders().get(FILE_ORIGINAL_FILE);

		List<List<Map<String, String>>> content = msg.getPayload();
		// LOGGER.info("The read content is:" + (new Gson()).toJson(content));

		// move the file to processing folder
		fileOriginalFile = moveFileToFolder(fileOriginalFile, inboundProcessingDirectory.getPath(),
				fileOriginalFile.getName());

		// import the users to the database
		Map<Long, List<ExcelImportErrorData>> errorLists = importUserFromSheets(content);

		// record the import result
		if (MapUtils.isNotEmpty(errorLists)) {
			LOGGER.error("There have some error when import the users");

			// move the file to error folder
			fileOriginalFile = writeErrorMessageToFile(fileOriginalFile, errorLists);

		} else {
			LOGGER.info("Import User success");
			// move the file to archive folder
			fileOriginalFile = archiveFile(fileOriginalFile);
		}

	}

	/**
	 * write the error message to file
	 *
	 * @param file
	 * @param errorLists
	 * @return
	 */
	protected File writeErrorMessageToFile(File file, Map<Long, List<ExcelImportErrorData>> errorLists) {

		try {
			// write the error message
			ExcelWriteHelper.writeExcel(file.getPath(), errorLists);

			// move the file
			return moveFileToFolder(file, inboundErrorDirectory.getPath(),
					file.getName().substring(0, file.getName().lastIndexOf(".")) + (new Date()).getTime()
							+ "error.xlsx");
		} catch (IOException e) {
			LOGGER.error("Failed to write the error message to file");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * archive the imported file
	 *
	 * @param file
	 * @return
	 */
	protected File archiveFile(File file) {
		return moveFileToFolder(file, inboundArchiveDirectory.getPath(),
				file.getName().substring(0, file.getName().lastIndexOf(".")) + (new Date()).getTime() + ".xlsx");
	}

	/**
	 * move the file to folder
	 *
	 * @param file
	 * @param to
	 * @param fileName
	 * @return
	 */
	protected File moveFileToFolder(File file, String to, String fileName) {
		try {
			return FileMoveHelper.moveFileToFolder(file, to, fileName);
		} catch (Exception e) {
			LOGGER.error("Failed to move the file from root folder to processing folder. to:" + to);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * import the user from sheets
	 *
	 * @param sheets
	 * @return
	 */
	protected Map<Long, List<ExcelImportErrorData>> importUserFromSheets(List<List<Map<String, String>>> sheets) {

		Map<Long, List<ExcelImportErrorData>> errorLists = new HashMap<>();

		if (CollectionUtils.isEmpty(sheets)) {
			LOGGER.error("The read excel is empty, can not import the user");
			return errorLists;
		}

		int i = 0;
		for (List<Map<String, String>> sheet : sheets) {
			List<ExcelImportErrorData> errorList = importUserFromSheet(sheet);

			if (!CollectionUtils.isEmpty(errorList)) {
				errorLists.put(Long.valueOf(i), errorList);
			}

			i++;
		}

		return errorLists;

	}

	/**
	 * import the users from sheet
	 *
	 * @param sheet
	 * @return
	 */
	protected List<ExcelImportErrorData> importUserFromSheet(List<Map<String, String>> sheet) {

		List<ExcelImportErrorData> errorList = new ArrayList<>();

		if (CollectionUtils.isEmpty(sheet)) {
			LOGGER.error("The read excel sheet is empty, can not import the users");
			return errorList;
		}

		// need to import two sheet , one is for tag ,another is for user
		int i = 0;
		for (Map<String, String> line : sheet) {
			i++;

			// validate the line
			String validateResult = validateLine(line);
			if (!StringUtils.isEmpty(validateResult)) {
				LOGGER.error(
						"Fail to validate the line number:" + i + "line:" + line + " errorMessage:" + validateResult);
				ExcelImportErrorData errorData = buildImportErrorData(line, validateResult, Long.valueOf(i));
				errorList.add(errorData);
				continue;
			}

			// if user sheet .do this
			ManagedUserVM userDTO = new ManagedUserVM();
			userDTO.setLogin(line.get("EID"));
			userDTO.setPassword(line.get("SAPID"));
			userDTO.setActivated(true);

			String importResult = saveUserInfo(userDTO);
			if (!StringUtils.isEmpty(importResult)) {
				LOGGER.error("Fail to import the line:" + line + " errorMessage:" + importResult);
				ExcelImportErrorData errorData = buildImportErrorData(line, importResult, Long.valueOf(i));
				errorList.add(errorData);
				continue;
			}

		}

		return errorList;
	}

	private String saveUserInfo(ManagedUserVM userDTO) {
		String result = "";
		try {
			userRepository.findOneByLogin(userDTO.getLogin()).ifPresent(u -> {
				throw new LoginAlreadyUsedException();
			});
			userService.registerUser(userDTO, userDTO.getPassword());
		} catch (Exception e) {
			result = userDTO.getLogin() + "已经存在，勿重复导入";
		}
		return result;
	}

	/**
	 * build the import error data
	 *
	 * @param line
	 * @param errorMessage
	 * @param lineNum
	 * @return
	 */
	protected ExcelImportErrorData buildImportErrorData(Map<String, String> line, String errorMessage, Long lineNum) {

		if (MapUtils.isNotEmpty(line) && !StringUtils.isEmpty(errorMessage) && lineNum != null) {

			ExcelImportErrorData result = new ExcelImportErrorData();
			result.setLine(line);
			result.setErrorMessage(errorMessage);
			result.setLineNum(lineNum);

			return result;
		}

		return null;
	}

	/**
	 * validate the line to check is there have some data issue.
	 *
	 * @param line
	 * @return
	 */
	protected String validateLine(Map<String, String> line) {

		StringBuilder errorMessage = new StringBuilder();

		if (!line.containsKey("SAPID") || StringUtils.isEmpty(line.get("SAPID"))) {
			errorMessage.append("SAPID is empty.");
		}

		if (!line.containsKey("EID") || StringUtils.isEmpty(line.get("EID"))) {
			errorMessage.append("EID is empty.");
		}

		return errorMessage.toString();
	}

}
