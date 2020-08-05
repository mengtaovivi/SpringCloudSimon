package com.cloud.mt.base.util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.util.Matrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @Author HAI
 * @Date 2019/7/5 14:57
 * @Version 1.0
 */
@Slf4j
public class PDFUtil {

    public static void main(String[] args) throws Exception{
    	String resourcePath = "E:\\testWord\\案件1.pdf";
//    	File file=new File(resourcePath);
    	FileOutputStream fos=new FileOutputStream(new File("E:\\testWord\\2222.pdf"));
//    	FileUtils.deleteFileByPath("E:\\testWord\\2222.pdf");
    	addWatermarktext(resourcePath, fos, "华云中盛");
//    	watermarkPDF(resourcePath,"华云众生",fos);
    }


    /***
     * PDF文件转PNG图片，全部页数
     *
     * @param PdfFilePath pdf完整路径
     * @param dstImgFolder 图片存放的文件夹
     * @param dpi dpi越大转换后越清晰，相对转换速度越慢
     * @return
     */
    public static void pdfImage(String PdfFilePath, String dstImgFolder, int dpi) {
        File file = new File(PdfFilePath);
        PDDocument pdDocument;
        try {
            String imgPDFPath = file.getParent();
            int dot = file.getName().lastIndexOf('.');
            String imagePDFName = file.getName().substring(0, dot); // 获取图片文件名
            String imgFolderPath = null;
            if (dstImgFolder.equals("")) {
                imgFolderPath = imgPDFPath + File.separator + imagePDFName;// 获取图片存放的文件夹路径
            } else {
                imgFolderPath = dstImgFolder + File.separator + imagePDFName;
            }

            if (createDirectory(imgFolderPath)) {

                pdDocument = PDDocument.load(file);
                PDFRenderer renderer = new PDFRenderer(pdDocument);
                /* dpi越大转换后越清晰，相对转换速度越慢 */
                int pages = pdDocument.getNumberOfPages();
                StringBuffer imgFilePath = null;
                for (int i = 0; i < pages; i++) {
                    String imgFilePathPrefix = imgFolderPath + File.separator + imagePDFName;
                    imgFilePath = new StringBuffer();
                    imgFilePath.append(imgFilePathPrefix);
                    imgFilePath.append("_");
                    imgFilePath.append(String.valueOf(i + 1));
                    imgFilePath.append(".png");
                    File dstFile = new File(imgFilePath.toString());
                    BufferedImage image = renderer.renderImageWithDPI(i, dpi);
                    ImageIO.write(image, "png", dstFile);
                }
                log.info("PDF文档转PNG图片成功！");

            } else {
                log.info("PDF文档转PNG图片失败：" + "创建" + imgFolderPath + "失败");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean createDirectory(String folder) {
        File dir = new File(folder);
        if (dir.exists()) {
            return true;
        } else {
            return dir.mkdirs();
        }
    }





    /**
    *
    * @param fileName
    *            生成pdf文件
    * @param imagesPath
    *            需要转换的图片路径的数组
    * @param saveFilePath
    * 			 保存生成的pdf文件路径
    */
   public static File imagesToPdf(String fileName, String imagesPath,String saveFilePath) {
       try {
           fileName = saveFilePath+"/"+fileName+".pdf";

           File file = new File(fileName);
           if(file.exists()) {
        	   file.delete();
           }
           file=FileUtils.newFile(fileName);


           // 第一步：创建一个document对象。
           Document document = new Document();
           document.setMargins(0, 0, 0, 0);
           // 第二步：
           // 创建一个PdfWriter实例，
           PdfWriter.getInstance(document, new FileOutputStream(file));
           // 第三步：打开文档。
           document.open();
           // 第四步：在文档中增加图片。
           System.out.println(imagesPath);
           
           File files = new File(imagesPath);
           String[] imageNames=files.list();
//           files.list()
           String nameRegrex="^\\d+.jpg$";
           
           String[] images = new String[imageNames.length];
           
           for(int i=0;i<imageNames.length;i++) {
        	   
        	   
        	   //根据文件名排序,计数排序
        	   //文件全名+后缀
//        	   String nameAll=imageNames[i].substring(imageNames[i].lastIndexOf("/"));
        	   String nameAll=imageNames[i];
        	   //匹配名字
        	   if(nameAll.matches(nameRegrex)) {
        		   //数字文件全名不加后缀
        		   String name=nameAll.split("\\.")[0];
        		   images[Integer.parseInt(name)]=nameAll;
        	   }
           }
           
           int len = images.length;

           for (int i = 0; i < len; i++)
           {
               if (images[i].toLowerCase().endsWith(".bmp")
                       || images[i].toLowerCase().endsWith(".jpg")
                       || images[i].toLowerCase().endsWith(".jpeg")
                       || images[i].toLowerCase().endsWith(".gif")
                       || images[i].toLowerCase().endsWith(".png")) {
                   String temp = imagesPath + "/" + images[i];
                   Image img = Image.getInstance(temp);
                   img.setAlignment(Image.ALIGN_CENTER);
                   // 根据图片大小设置页面，一定要先设置页面，再newPage（），否则无效
                   document.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
                   document.newPage();
                   document.add(img);
               }
           }
           // 第五步：关闭文档。
           document.close();
           return file;
       } catch (Exception e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }

	return null;


   }
   
   //拆分pdf
   
   public static void pdfOfSplit(String pdfFilePath,String outputFilePath){
		String resourcePath = pdfFilePath;
		File resource = new File(resourcePath);		
		try {
			//加载文档
			PDDocument doc = PDDocument.load(resource);
			//分割操作
			Splitter  split = new Splitter ();
			//设置从那页开始分割
			split.setStartPage(1);
			//设置每个文件多少页
			split.setSplitAtPage(1);
			//分割文档
			List<PDDocument> docs = split.split(doc);
			int i =1;
			//生成分割文档
			for(PDDocument document:docs){
				String fileName = outputFilePath+"dd"+i+".pdf";
				File file = new File(fileName);
				if(!file.exists()){
					file.createNewFile();
				}
				FileOutputStream fos = new FileOutputStream(file);
				document.save(fos);
				document.close();
				i++;
				
			}
			doc.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
   //合并pdf
   public static void mergerPdf(String firstPath,String secondPath,String fileOutPath) throws Exception {
		// 接收合并后的pdf文件流对象
		 FileOutputStream fos=new FileOutputStream(new File(fileOutPath));
		 // 创建PDF合并对象
		 PDFMergerUtility merger = new PDFMergerUtility();
		 // byteList 为待合并的pdf列表
		 
		 if(FileUtils.exists(firstPath)) {
			 merger.addSource(new File(firstPath));
		 }
		 if(FileUtils.exists(secondPath)) {
			 merger.addSource(new File(secondPath));
		 }
		 
		 // 设定合并后的输出流对象
		 merger.setDestinationStream(fos);
		 try {
		     // 合并PDF
		     merger.mergeDocuments(null);
		     fos.close();
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
	}
   
   
   //给pdf添加水印
   public static void watermarkPDF (String filePath,String shuiYinText,OutputStream os) throws Exception {
	   File fileStored=FileUtils.newFile(filePath);
//       File tmpPDF;
       PDDocument doc;
//       tmpPDF = new File(fileStored.getParent() + System.getProperty("file.separator") +"Tmp_"+fileStored.getName());
       doc = PDDocument.load(fileStored);
       doc.setAllSecurityToBeRemoved(true);
       for(PDPage page:doc.getPages()){
           PDPageContentStream cs = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, true);
           PDFont font = PDType1Font.HELVETICA_OBLIQUE;
           float fontSize = 50.0f;
           PDResources resources = page.getResources();
           PDExtendedGraphicsState r0 = new PDExtendedGraphicsState();
           // 透明度
           r0.setNonStrokingAlphaConstant(0.2f);
           r0.setAlphaSourceFlag(true);
           cs.setGraphicsStateParameters(r0);
           cs.setNonStrokingColor(200,0,0);//Red
           cs.beginText();
           cs.setFont(font, fontSize);
           // 获取旋转实例
           cs.setTextMatrix(Matrix.getRotateInstance(20,200f,390f));
           cs.showText(shuiYinText);
           cs.endText();
           cs.close();
       }
       doc.save(os);
   }
   
   
   public static boolean addWatermarktext(String filePath,OutputStream os,String text ) {

	   try {
		   FileInputStream fis=new FileInputStream(new File(filePath));
		   PdfReader reader =new PdfReader(fis);
		   //修改后的PDF将会存入bao
		   ByteArrayOutputStream bao =new ByteArrayOutputStream();
		   PdfStamper stamper =new PdfStamper(reader,bao);
	   //      PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outfile));
		   // 字体设置支持中文
		   BaseFont base =BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.EMBEDDED);
		   int total =reader.getNumberOfPages() +1;
		   PdfContentByte under=null;
		   Rectangle pageRect =null;
		   for(int i =1 ; i< total; i++){
			   pageRect =stamper.getReader().getPageSizeWithRotation(i);
			   //计算水印的位置
			   float x = pageRect.getWidth()/3 -50;
			   float y = pageRect.getHeight()/3 -20;
			   // 获得PDF最顶层
			   under =stamper.getOverContent(i);
			   under.saveState();
			   // 设置透明度为0.3
			   PdfGState gs =new PdfGState();
			   gs.setFillOpacity(0.3f);
			   under.setGState(gs);
			   //开始加入水印
			   under.beginText();
			   under.setFontAndSize(base,100);
			   under.setColorFill(BaseColor.RED);
			   // 水印文字成45度角倾斜
			   under.showTextAligned(Element.ALIGN_LEFT, text,x,y,45);
			   // 添加水印文字结束
			   under.endText();
			   under.setLineWidth(1f);
			   under.stroke();
			}
		   stamper.close();
		   byte[] byteArray =bao.toByteArray();
		   
		   try {
	            os.write(byteArray);
	            os.flush();
	            os.close();
//	            bao.close();
//	            fis.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		   return true;
		   }catch (Exception e) {
			   e.printStackTrace();
			   return false;
		   }

	   }
}
