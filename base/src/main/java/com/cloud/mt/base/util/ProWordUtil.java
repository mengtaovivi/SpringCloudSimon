package com.cloud.mt.base.util;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlObject;

import javax.imageio.ImageIO;
import javax.xml.namespace.QName;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

//生成word的工具
//包括插入文字，图片等功能，方便做word生成的功能
public class ProWordUtil {

	
	
	public static void aaa() throws Exception{
		XWPFDocument doc = new XWPFDocument();
		//创建一个段落
		XWPFParagraph para = doc.createParagraph();
		//一个XWPFRun代表具有相同属性的一个区域：一段文本
		XWPFRun run = para.createRun();
//		run.setBold(true); //加粗
		run.setText("加粗的内容");
		InputStream pic = new FileInputStream("E:\\民事行政\\测试用pdf\\1.png");
		BufferedImage sourceImg =ImageIO.read(new FileInputStream("E:\\民事行政\\测试用pdf\\1.png")); 
		
		XWPFParagraph para1 = doc.createParagraph();
		run=para1.createRun();
		run.addPicture(pic, Document.PICTURE_TYPE_PNG,"22.png", Units.toEMU(sourceImg.getWidth()/4), Units.toEMU(sourceImg.getHeight()/4));
		
		OutputStream os = new FileOutputStream("E:\\testWord\\simpleWrite.docx");
		//把doc输出到输出流
		doc.write(os);
		os.flush();
		os.close();
		doc.close();
	}
	
	
	
	//根据word是否存在自动生成文本
	public static void autoAddText(List<String> text,String path) throws Exception {
		File file=new File(path);
		
		if(file.exists()) {
			addText(text, path);
		}else {
			FileUtils.newFile(path);
;			insertText(text, path);
		}
	}
	
	
	//"E:\\testWord\\simpleWrite.docx"
	//插入文字
	public static void insertText(List<String> text,String path) throws Exception{

		XWPFDocument doc = new XWPFDocument();
		
		if(text!=null&&text.size()>0) {
			for(int i=0;i<text.size();i++) {
				//创建一个段落
				XWPFParagraph para = doc.createParagraph();
				//一个XWPFRun代表具有相同属性的一个区域：一段文本
				XWPFRun run = para.createRun();
				run.setText(text.get(i));
			}
		}
		
		
		OutputStream os = new FileOutputStream(path);
		//把doc输出到输出流
		doc.write(os);
		os.flush();
		os.close();
		doc.close();
	}
	//追加文本
	public static void addText(List<String> text,String path) throws Exception{
		File srcFile=FileUtils.newFile(path);
		XWPFDocument doc = new XWPFDocument(new FileInputStream(srcFile));
		for(int i=0;i<text.size();i++) {
			//创建一个段落
			XWPFParagraph para = doc.createParagraph();
			//一个XWPFRun代表具有相同属性的一个区域：一段文本
			XWPFRun run = para.createRun();
			run.setText(text.get(i));
		}
		OutputStream os = new FileOutputStream(srcFile);
		//把doc输出到输出流
		doc.write(os);
		os.flush();
		os.close();
		doc.close();
	}
	
	
	public static void autoAddPicture(String docPath,String imagePath) throws Exception{
		File file=new File(docPath);
		if(file.exists()) {
			addPicture(docPath, imagePath);
		}else {
			FileUtils.newFile(docPath);
			insertPicture(docPath, imagePath);
		}
	}
	
	
	//"E:\\testWord\\simpleWrite.docx"
	//插入文字
	public static void insertPicture(String docPath,String imagePath) throws Exception{
		XWPFDocument doc = new XWPFDocument();
		//创建一个段落
		XWPFParagraph para = doc.createParagraph();
		XWPFRun run = para.createRun();
		InputStream pic = new FileInputStream(imagePath);
		BufferedImage sourceImg =ImageIO.read(new FileInputStream(imagePath));
		run.addPicture(pic, Document.PICTURE_TYPE_PNG,"22.png", Units.toEMU(sourceImg.getWidth()/4), Units.toEMU(sourceImg.getHeight()/4));
		OutputStream os = new FileOutputStream(docPath);
		//把doc输出到输出流
		doc.write(os);
		os.flush();
		os.close();
	}
	
	
	//追加图片
	public static void addPicture(String docPath,String imagePath) throws Exception{
		File srcfile=FileUtils.newFile(docPath);
		XWPFDocument doc = new XWPFDocument(new FileInputStream(srcfile));
		//创建一个段落
		XWPFParagraph para = doc.createParagraph();
		XWPFRun run = para.createRun();
		InputStream pic = new FileInputStream(imagePath);
		BufferedImage sourceImg =ImageIO.read(new FileInputStream(imagePath));
		run.addPicture(pic, Document.PICTURE_TYPE_PNG,"22.png", Units.toEMU(sourceImg.getWidth()/4), Units.toEMU(sourceImg.getHeight()/4));
		OutputStream os = new FileOutputStream(docPath);
		//把doc输出到输出流
		doc.write(os);
		os.flush();
		os.close();
		doc.close();
	}
	//插入水印
		
	public static void addWaterMark(Object obj, String watermark, String color) {
	      XWPFDocument doc = (XWPFDocument) obj;
	      // create header-footer
	      XWPFHeaderFooterPolicy headerFooterPolicy = doc.getHeaderFooterPolicy();
	      
	      if (headerFooterPolicy == null) 
//	    	  headerFooterPolicy=new XWPFHeaderFooterPolicy(doc);
	    	  headerFooterPolicy = doc.createHeaderFooterPolicy();
	      
	      
	      
	      // create default Watermark - fill color black and not rotated
	      headerFooterPolicy.createWatermark(watermark);
	      
	      // get the default header
	      // Note: createWatermark also sets FIRST and EVEN headers 
	      // but this code does not updating those other headers
	      XWPFHeader header = headerFooterPolicy.getHeader(XWPFHeaderFooterPolicy.DEFAULT);
	      XWPFParagraph paragraph = header.getParagraphArray(0);
	      
	      // get com.microsoft.schemas.vml.CTShape where fill color and rotation is set
	      XmlObject[] xmlobjects = paragraph.getCTP().getRArray(0).getPictArray(0).selectChildren(new QName("urn:schemas-microsoft-com:vml", "shape"));
	      if (xmlobjects.length > 0) {
	        com.microsoft.schemas.vml.CTShape ctshape = (com.microsoft.schemas.vml.CTShape)xmlobjects[0];
	        // set fill color
	        ctshape.setFillcolor(color);
	        // set rotation
	        ctshape.setStyle(ctshape.getStyle() + ";rotation:315");
	      }
	  }

	
	public static void main(String[] args) throws Exception{
		FileUtils.deleteFileByPath("E:\\testWord\\ss.docx");
		String path="E:\\testWord\\tttt.docx";
//		insertText("不喜欢是么，已经厌烦我了是么，那我走，我走还不行么不喜欢是么，已经厌烦我了是么，那我走，我走还不行么不喜欢是么，已经厌烦我了是么，那我走，我走还不行么不喜欢是么，已经厌烦我了是么，那我走，我走还不行么不喜欢是么，已经厌烦我了是么，那我走，我走还不行么不喜欢是么，已经厌烦我了是么，那我走，我走还不行么不喜欢是么，已经厌烦我了是么，那我走，我走还不行么不喜欢是么，已经厌烦我了是么，那我走，我走还不行么不喜欢是么，已经厌烦我了是么，那我走，我走还不行么", "E:\\testWord\\tttt.docx");
		
		XWPFDocument doc = new XWPFDocument(new FileInputStream(path));
		addWaterMark(doc, "华纭众生", "red");
		OutputStream os = new FileOutputStream("E:\\testWord\\ss.docx");
		//把doc输出到输出流
		doc.write(os);
		os.flush();
		os.close();
		doc.close();
		
	}

}
