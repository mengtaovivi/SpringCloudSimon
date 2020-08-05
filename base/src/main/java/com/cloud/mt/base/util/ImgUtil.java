package com.cloud.mt.base.util;


import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * 用文件头判断。直接读取文件的前几个字节。 常用文件的文件头如下： JPEG (jpg)，文件头：FFD8FF PNG (png)，文件头：89504E47
 * GIF (gif)，文件头：47494638 TIFF (tif)，文件头：49492A00 Windows Bitmap (bmp)，文件头：424D
 * CAD (dwg)，文件头：41433130 Adobe Photoshop (psd)，文件头：38425053 Rich Text Format
 * (rtf)，文件头：7B5C727466 XML (xml)，文件头：3C3F786D6C HTML (html)，文件头：68746D6C3E
 * Email [thorough only] (eml)，文件头：44656C69766572792D646174653A Outlook Express
 * (dbx)，文件头：CFAD12FEC5FD746F Outlook (pst)，文件头：2142444E MS Word/Excel
 * (xls.or.doc)，文件头：D0CF11E0 MS Access (mdb)，文件头：5374616E64617264204A
 * WordPerfect (wpd)，文件头：FF575043 Postscript.
 * (eps.or.ps)，文件头：252150532D41646F6265 Adobe Acrobat (pdf)，文件头：255044462D312E
 * Quicken (qdf)，文件头：AC9EBD8F Windows Password (pwl)，文件头：E3828596 ZIP Archive
 * (zip)，文件头：504B0304 RAR Archive (rar)，文件头：52617221 Wave (wav)，文件头：57415645 AVI
 * (avi)，文件头：41564920 Real Audio (ram)，文件头：2E7261FD Real Media (rm)，文件头：2E524D46
 * MPEG (mpg)，文件头：000001BA MPEG (mpg)，文件头：000001B3 Quicktime (mov)，文件头：6D6F6F76
 * Windows Media (asf)，文件头：3026B2758E66CF11 MIDI (mid)，文件头：4D546864
 *
 */
@Slf4j
public class ImgUtil {

    /**
     * 图片格式保存 K: 文件头    V: 文件格式
     */
    private static final Map<String,String> map = new HashMap<>();


    /**
     * k: 文件头 v:文件格式
     */
    static {
        map.put("FFD8FF","jpg");
        map.put("89504E47","png");
        map.put("47494638","gif");
        map.put("424D","bmp");
    }



    /**
     * byte数组转换成16进制字符串
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 根据文件流读取图片文件真实类型
     *
     * @param
     * @return  jpg、jpeg、png、gif、bmp
     */
    public static String getTypeByStream(InputStream is) {
        byte[] b = new byte[4];
        try {
            is.read(b, 0, b.length);
        } catch (IOException e) {
            log.error("!-ERROR-! {}",e) ;
        }
        String type = bytesToHexString(b).toUpperCase();
        log.info("文件类型:{}",type);
        for (String header : map.keySet()) {
            if (type.startsWith(header)) {
                return map.get(header);
            }
        }
        return type;
    }

    /**
     *
     * @param is
     * @return  jpg、jpeg、png、gif、bmp)
     */
    public static boolean isPicture(InputStream is) {
        try {
            String type = getTypeByStream(is);
            Collection<String> values = map.values();
            if(values.contains(type)){
                return true ;
            }
        } catch (Exception e) {
            log.error("!-ERROR-! {}",e) ;
        }
        return false;
    }





    public static void main(String[] args) throws Exception {
        // String src = "D:/workspace//8129.jpg";
        // String src = "D:/workspace//temp/1.gif";
        String src = "C:\\Users\\Administrator\\Desktop\\小程序appid和密钥.txt";
        InputStream is = new FileInputStream(src);
        // byte[] b = new byte[4];
        // is.read(b, 0, b.length);
        // System.out.println(bytesToHexString(b));

        boolean type = isPicture(is);
        System.out.println(type);
        /*
         * JPEG (jpg)，文件头：FFD8FF PNG (png)，文件头：89504E47 GIF (gif)，文件头：47494638
         * TIFF (tif)，文件头：49492A00 Windows Bitmap (bmp)，文件头：424D
         */
    }
}
