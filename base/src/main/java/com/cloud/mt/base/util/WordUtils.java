package com.cloud.mt.base.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author created by kwy on 2019/7/11
 *  参考链接 https://www.cnblogs.com/duanrantao/p/8682897.html
 *  通过word模板生成新的word工具类
 *
 *  HWPF 是 POI 支持 Word(97-2003) 的 Java 组件，支持读写Word文档，但是写功能目前只实现一部分；它也提供更早版本的Word6和Word95版本的简单的文本摘录功能。
 *  XWPF是 POI 支持 Word 2007+ 的 Java组件，提供简单文件的读写功能。
 */
@Slf4j
public class WordUtils {

    /**
     * 根据模板生成word
     * @param path     模板的路径
     * @param params   需要替换的参数
     * @param tableList   需要插入的参数
     * @param fileName 生成word文件的文件名
     * @param response
     */
    public void getWord(String path, Map<String, Object> params, List<String[]> tableList, String fileName, HttpServletResponse response) throws Exception {
        File file = new File(path);
        InputStream is = new FileInputStream(file);
        CustomXWPFDocument doc = new CustomXWPFDocument(is);
        this.replaceInPara(doc, params);    //替换文本里面的变量
        this.replaceInTable(doc, params, tableList); //替换表格里面的变量
        OutputStream os = response.getOutputStream();
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);
        doc.write(os);
        this.close(os);
        this.close(is);
    }
    public void getWord (String path, Map<String, Object> params, String fileName, HttpServletResponse response) throws Exception {
        this.getWord(path,params,null,fileName,response);
    }

    /**
     * 替换段落里面的变量
     * @param doc    要替换的文档
     * @param params 参数
     */
    private void replaceInPara(CustomXWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
        XWPFParagraph para;
        while (iterator.hasNext()) {
            para = iterator.next();
            this.replaceInPara(para, params, doc);
        }
    }

    /**
     * 替换段落里面的变量
     *
     * @param para   要替换的段落
     * @param params 参数
     */
    private void replaceInPara(XWPFParagraph para, Map<String, Object> params, CustomXWPFDocument doc) {
        List<XWPFRun> runs;
        Matcher matcher;
        if (this.matcher(para.getParagraphText()).find()) {
            runs = para.getRuns();
            int start = -1;
            int end = -1;
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < runs.size(); i++) {
                XWPFRun run = runs.get(i);
                String runText = run.toString();
                if(runText.length() < 2 ){
                    continue;
                }
                if ('$' == runText.charAt(0) && '{' == runText.charAt(1)) {
                    start = i;
                }
                if (start == -1) {
                   continue;
                }
                str.append(runText);
                if ('}' == runText.charAt(runText.length() - 1)) {
                    end = i;
                    break;
                }
            }

            for (int i = start; i <= end; i++) {
                para.removeRun(i);
                i--;
                end--;
            }

            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                if (str.indexOf(key) != -1) {
                    Object value = entry.getValue();
                    // 无样式的文本
                    if (value instanceof String) {
                        str = new StringBuilder(str.toString().replace(key, value.toString()));
                        para.createRun().setText(str.toString(), 0);
                        break;
                    } else if (value instanceof Map) {
                        Map map = (Map) value;
                        String type = map.get("type").toString();

                        // 有样式的文本
                        if("text".equals(type)){
                            boolean bold = Boolean.valueOf(map.get("bold").toString());
                            int fontSize = Integer.valueOf(map.get("fontSize").toString());
                            String content = String.valueOf(map.get("content").toString());
                            // 字体样式设置 参考链接 https://blog.csdn.net/jifenglie/article/details/88413990
                            str = new StringBuilder(str.toString().replace(key, content));
                            XWPFRun run = para.createRun();
                            run.setText(str.toString(), 0);
                            run.getVerticalAlignment();
                            run.setBold(bold);//加粗
                            run.setFontSize(fontSize);//字体大小
                        // 图片
                        }else{
                            int picType = getPictureType(type);
                            int width = Integer.parseInt(map.get("width").toString());
                            int height = Integer.parseInt(map.get("height").toString());
                            byte[] byteArray = (byte[]) map.get("content");

                            str = new StringBuilder(str.toString().replace(key, ""));

                            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteArray);
                            try {
                                //int ind = doc.addPicture(byteInputStream,picType);
                                //doc.createPicture(ind, width , height,para);
                                doc.addPictureData(byteInputStream, picType);
                                doc.createPicture(doc.getAllPictures().size() - 1, width, height, para);
                                para.createRun().setText(str.toString(), 0);
                                break;
                            } catch (Exception e) {
                                log.error("!-ERROR-! {}",e) ;
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * 为表格插入数据，行数不够添加新行
     *
     * @param table     需要插入数据的表格
     * @param tableList 插入数据集合
     */
    private static void insertTable(XWPFTable table, List<String[]> tableList) {
        if(tableList == null){
            return;
        }

        //创建行,根据需要插入的数据添加新行，不处理表头
        for (int i = 0; i < tableList.size(); i++) {
            XWPFTableRow row = table.createRow();
        }
        //遍历表格插入数据
        List<XWPFTableRow> rows = table.getRows();
        int length = table.getRows().size();
        for (int i = 1; i < length - 1; i++) {
            XWPFTableRow newRow = table.getRow(i);
            List<XWPFTableCell> cells = newRow.getTableCells();
            for (int j = 0; j < cells.size(); j++) {
                XWPFTableCell cell = cells.get(j);
                String s = tableList.get(i - 1)[j];
                cell.setText(s);
            }
        }
    }

    /**
     * 替换表格里面的变量
     * @param doc    要替换的文档
     * @param params 参数
     */
    private void replaceInTable(CustomXWPFDocument doc, Map<String, Object> params, List<String[]> tableList) {
        Iterator<XWPFTable> iterator = doc.getTablesIterator();
        XWPFTable table;
        List<XWPFTableRow> rows;
        List<XWPFTableCell> cells;
        List<XWPFParagraph> paras;
        while (iterator.hasNext()) {
            table = iterator.next();
            if (table.getRows().size() > 1) {
                //判断表格是需要替换还是需要插入，判断逻辑有$为替换，表格无$为插入
                if (this.matcher(table.getText()).find()) {
                    rows = table.getRows();
                    for (XWPFTableRow row : rows) {
                        cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
                            paras = cell.getParagraphs();
                            for (XWPFParagraph para : paras) {
                                this.replaceInPara(para, params, doc);
                            }
                        }
                    }
                } else {
                    insertTable(table, tableList);  //插入数据
                }
            }
        }
    }


    /**
     * 正则匹配字符串
     *
     * @param str
     * @return
     */
    private Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }


    /**
     * 根据图片类型，取得对应的图片类型代码
     *
     * @param picType
     * @return int
     */
    private static int getPictureType(String picType) {
        int res = CustomXWPFDocument.PICTURE_TYPE_PICT;
        if (picType != null) {
            if (picType.equalsIgnoreCase("png")) {
                res = CustomXWPFDocument.PICTURE_TYPE_PNG;
            } else if (picType.equalsIgnoreCase("dib")) {
                res = CustomXWPFDocument.PICTURE_TYPE_DIB;
            } else if (picType.equalsIgnoreCase("emf")) {
                res = CustomXWPFDocument.PICTURE_TYPE_EMF;
            } else if (picType.equalsIgnoreCase("jpg") || picType.equalsIgnoreCase("jpeg")) {
                res = CustomXWPFDocument.PICTURE_TYPE_JPEG;
            } else if (picType.equalsIgnoreCase("wmf")) {
                res = CustomXWPFDocument.PICTURE_TYPE_WMF;
            }
        }
        return res;
    }

    /**
     * 将输入流中的数据写入字节数组
     *
     * @param in
     * @return
     */
    public static byte[] inputStream2ByteArray(InputStream in, boolean isClose) {
        byte[] byteArray = null;
        try {
            int total = in.available();
            byteArray = new byte[total];
            in.read(byteArray);
        } catch (IOException e) {
            log.error("!-ERROR-! {}",e) ;
        } finally {
            if (isClose) {
                try {
                    in.close();
                } catch (Exception e2) {
                    e2.getStackTrace();
                }
            }
        }
        return byteArray;
    }




    /**
     * 关闭输入流
     *
     * @param is
     */
    private void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                log.error("!-ERROR-! {}",e) ;
            }
        }
    }

    /**
     * 关闭输出流
     *
     * @param os
     */
    private void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                log.error("!-ERROR-! {}",e) ;
            }
        }
    }

}