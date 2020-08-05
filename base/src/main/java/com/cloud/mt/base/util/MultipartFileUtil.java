package com.cloud.mt.base.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author created by kwy on 2019/6/28
 * 上传文件 工具类
 */
@Slf4j
public class MultipartFileUtil {

    private MultipartFileUtil(){}

    /**
     * bufferImage->base64
     */
    public static String  multipartFileToBase65(MultipartFile multipartFile) throws Exception {
        BufferedImage image = ImageIO.read(multipartFile.getInputStream());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(outputStream.toByteArray());
    }
    /**
     * MultipartFile 转 File
     * @param file
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile file) throws Exception {
        File toFile = null;
        if(StringUtils.isBlank(file.getOriginalFilename()) || file.getSize()<=0){
            file = null;
        }else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
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
}
