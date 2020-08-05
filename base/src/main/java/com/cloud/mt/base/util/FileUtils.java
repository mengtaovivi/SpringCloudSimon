package com.cloud.mt.base.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
public class FileUtils {
	
	//创建file
	public static File newFile(String filePath) throws IOException {
		File file=new File(filePath);
		File fileParent=file.getParentFile();
		if(!fileParent.exists()&&!fileParent.isFile()) {
			fileParent.mkdirs();
		}
		if(!file.exists()) {
			file.createNewFile();
		}
		return file;
	}
	
	//将MultipartFile转化为服务器上的文件
	public static File multipartFileToFile(MultipartFile file,String filePath) throws Exception {
        File toFile = null;
        if(StringUtils.isBlank(file.getOriginalFilename()) || file.getSize()<=0){
            file = null;
        }else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = newFile(filePath);
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }


    public static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            log.error("!-ERROR-! {}",e) ;
        }
    }
    
    //将byte转化为json在转化为File文件
    public static void objToJsonToFile(Object obj,String filePath)throws Exception {
    	
    	File file=newFile(filePath);
    	String objStr=JSON.toJSONString(obj);
    	
    	FileOutputStream fos=new FileOutputStream(file);
    	
    	fos.write(objStr.getBytes());
    	fos.close();
    }
    
    //根据文件路径强制删除文件
    public static void deleteFileByPath(String filePath) throws IOException {
    	File file=new File(filePath);
    	if(file.exists()) {
    		org.apache.commons.io.FileUtils.forceDelete(file);
    	}
    }
    
    //复制文件到一个文件夹中
    public static void copyFileToDirectory(String srcFilePath,String descDirPath) throws IOException{
    	//目标文件夹
    	File descDir=new File(descDirPath);
    	if(descDir.isDirectory()&&!descDir.exists()) {
    		descDir.mkdirs();
    	}
    	org.apache.commons.io.FileUtils.copyFileToDirectory(new File(srcFilePath), descDir);
    }
    
    public static void copyFile(String srcFilePath,String destFilePath)throws IOException {
    	File srcFile=new File(srcFilePath);
    	File destFile=newFile(destFilePath);
    	org.apache.commons.io.FileUtils.copyFile(srcFile, destFile);
    }
    
    
    //获取jar包应用本地文件
    public static InputStream resourceLoader(String fileFullPath) throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        return resourceLoader.getResource(fileFullPath).getInputStream();
    }
    //根据路径判断文件是否存在
    public static Boolean exists(String path) {
    	File file=new File(path);
    	return file.exists();
    }
}
