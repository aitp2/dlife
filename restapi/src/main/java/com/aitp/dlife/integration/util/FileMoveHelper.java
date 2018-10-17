package com.aitp.dlife.integration.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileMoveHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileMoveHelper.class);

    public static File moveFileToFolder(File file, String to, String fileName) throws Exception {
        if (file == null){
            LOGGER.error("moveFileToFolder(File file, String to, String fileName), the file is null");
        }else if (StringUtils.isEmpty(to)){
            LOGGER.error("moveFileToFolder(File file, String to, String fileName), the to folder is null");
        }else if (file.isDirectory()){
            LOGGER.error("moveFileToFolder(File file, String to, String fileName), the file is a directory");
        }else if (StringUtils.isEmpty(fileName)){
            LOGGER.error("moveFileToFolder(File file, String to, String fileName), the fileName is a directory");
        }else{
            try{
                File toFile = new File(to +File.separator+ fileName);
                file.renameTo(toFile);
                //file.delete();
                return toFile;
            }catch (Exception e) {
                System.out.println("文件："+ file + "未找到");
            }
        }

        return null;
    }

	/** 
     * 移动指定文件或文件夹(包括所有文件和子文件夹) 
     *  
     * @param from
     *            要移动的文件或文件夹 
     * @param to
     *            目标文件夹 
     * @throws Exception 
     */  
    public static void MoveFolderAndFileWithSelf(String from, String to) throws Exception {  
        try {  
            File dir = new File(from);  
            // 目标  
            if (to.contains("archive")) {
            	to +=  File.separator + dir.getName() + "_" + System.currentTimeMillis(); 
			}else {
				to +=  File.separator + dir.getName(); 
			}
            File moveDir = new File(to);  
            if(dir.isDirectory()){  
                if (!moveDir.exists()) {  
                    moveDir.mkdirs();  
                }  
            }else{  
                File tofile = new File(to);  
                dir.renameTo(tofile);  
                return;  
            }  
              
            // 文件一览  
            File[] files = dir.listFiles();  
            if (files == null)  
                return;  
  
            // 文件移动  
            for (int i = 0; i < files.length; i++) {  
                System.out.println("文件名："+files[i].getName());  
                if (files[i].isDirectory()) {  
                    MoveFolderAndFileWithSelf(files[i].getPath(), to);  
                    // 成功，删除原文件  
                    files[i].delete();  
                }  
                File moveFile = new File(moveDir.getPath() + File.separator + files[i].getName());  
                // 目标文件夹下存在的话，删除  
                if (moveFile.exists()) {  
                    moveFile.delete();  
                }  
                files[i].renameTo(moveFile);  
            }  
            dir.delete();  
        } catch (Exception e) {  
        	 System.out.println("文件："+ from + "未找到");  
        }  
    }  
      
    /** 
     * 复制单个文件(可更名复制) 
     * @param oldPathFile 准备复制的文件源 
     * @param newPathFile 拷贝到新绝对路径带文件名(注：目录路径需带文件名) 
     * @return 
     */  
    public static void CopySingleFile(String oldPathFile, String newPathFile) {  
        try {  
            int bytesum = 0;  
            int byteread = 0;  
            File oldfile = new File(oldPathFile);  
            if (oldfile.exists()) { //文件存在时  
                InputStream inStream = new FileInputStream(oldPathFile); //读入原文件  
                FileOutputStream fs = new FileOutputStream(newPathFile);  
                byte[] buffer = new byte[1444];  
                while ((byteread = inStream.read(buffer)) != -1) {  
                    bytesum += byteread; //字节数 文件大小  
                    fs.write(buffer, 0, byteread);  
                }  
                inStream.close();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * 复制单个文件(原名复制) 
     * @param oldPathFile 准备复制的文件源 
     * @param targetPath 拷贝到新绝对路径带文件名(注：目录路径需带文件名)
     * @return 
     */  
    public static void CopySingleFileTo(String oldPathFile, String targetPath) {  
        try {  
            int bytesum = 0;  
            int byteread = 0;  
            File oldfile = new File(oldPathFile);  
            String targetfile = targetPath + File.separator +  oldfile.getName();  
            if (oldfile.exists()) { //文件存在时  
                InputStream inStream = new FileInputStream(oldPathFile); //读入原文件  
                FileOutputStream fs = new FileOutputStream(targetfile);  
                byte[] buffer = new byte[1444];  
                while ((byteread = inStream.read(buffer)) != -1) {  
                    bytesum += byteread; //字节数 文件大小  
                    //System.out.println(bytesum);  
                    fs.write(buffer, 0, byteread);  
                }  
                inStream.close();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * 复制整个文件夹的内容(含自身) 
     * @param oldPath 准备拷贝的目录 
     * @param newPath 指定绝对路径的新目录 
     * @return 
     */  
    public static void copyFolderWithSelf(String oldPath, String newPath) {  
        try {  
            new File(newPath).mkdirs(); //如果文件夹不存在 则建立新文件夹  
            File dir = new File(oldPath);  
            // 目标  
            newPath +=  File.separator + dir.getName();  
            File moveDir = new File(newPath);  
            if(dir.isDirectory()){  
                if (!moveDir.exists()) {  
                    moveDir.mkdirs();  
                }  
            }  
            String[] file = dir.list();  
            File temp = null;  
            for (int i = 0; i < file.length; i++) {  
                if (oldPath.endsWith(File.separator)) {  
                    temp = new File(oldPath + file[i]);  
                } else {  
                    temp = new File(oldPath + File.separator + file[i]);  
                }  
                if (temp.isFile()) {  
                    FileInputStream input = new FileInputStream(temp);  
                    FileOutputStream output = new FileOutputStream(newPath +  
                            "/" +  
                            (temp.getName()).toString());  
                    byte[] b = new byte[1024 * 5];  
                    int len;  
                    while ((len = input.read(b)) != -1) {  
                        output.write(b, 0, len);  
                    }  
                    output.flush();  
                    output.close();  
                    input.close();  
                }  
                if (temp.isDirectory()) { //如果是子文件夹  
                    copyFolderWithSelf(oldPath + "/" + file[i], newPath);  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}
