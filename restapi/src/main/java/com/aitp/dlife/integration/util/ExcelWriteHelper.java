package com.aitp.dlife.integration.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.StringUtils;

import com.aitp.dlife.integration.file.dto.ExcelImportErrorData;

public class ExcelWriteHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelWriteHelper.class);
    private static final int errorCellNum = 7;
    private static final String errorCellTitle = "ErrorMessage";

    /**
     * excel导出到输出流
     * 谁调用谁负责关闭输出流
     * @param filePath excel文件的扩展名，支持xls和xlsx，不带点号
     * @param datas
     * @throws IOException
     */
    public static void writeExcel(String filePath, Map<Long,List<ExcelImportErrorData>> datas) throws IOException {

        if (MapUtils.isEmpty(datas)){
            LOGGER.error("Can not write the excel, because of the datas is empty");
            return;
        }

        if (StringUtils.isEmpty(filePath)){
            LOGGER.error("Can not write the excel, because of the filePath is empty");
            return;
        }

        FileInputStream fis = null;
        FileOutputStream fos = null;
        Workbook workbook = null;
        try {
            fis=new FileInputStream(filePath);

            if (filePath.endsWith("xls")) {
                workbook = new HSSFWorkbook(fis);
            } else if (filePath.endsWith("xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else {
                throw new Exception("当前文件不是excel文件");
            }

            for(Map.Entry<Long,List<ExcelImportErrorData>> sheetErrorData: datas.entrySet()){
                Sheet sheet = workbook.getSheetAt(sheetErrorData.getKey().intValue());

                //Create the error title
                Row titleRow = sheet.getRow(0);
                Cell titleCell = titleRow.createCell(errorCellNum);
                titleCell.setCellValue(errorCellTitle);

                for(ExcelImportErrorData errorData : CollectionUtils.emptyIfNull(sheetErrorData.getValue())){
                    Row row = sheet.getRow(errorData.getLineNum().intValue());
                    if (row != null){
                        Cell cell = row.createCell(errorCellNum);
                        cell.setCellValue(errorData.getErrorMessage());
                    }else{
                        LOGGER.error("Can not write the error message, because of the row is null");
                    }

                }

            }
            fis.close();//关闭文件输入流

            fos = new FileOutputStream(filePath);
            workbook.write(fos);
            fos.close();//关闭文件输出流

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }

            if (workbook != null) {
                workbook.close();
            }

            if (fos != null) {
                fos.close();
            }
        }
    }
}
