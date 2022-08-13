package com.ffait.util;

import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;

import javax.print.PrintService;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.printing.Scaling;



//打印JFrame
public class PrintFrame {
	public static String tmpPathPDF = "C:\\parameter\\printPDF.pdf";
//	private static String tmpPathPDF = "C:\\REID\\FramePDF.pdf";
	
	public static void main(String[] args) throws Exception {
		PrintPdf(tmpPathPDF); 
	}
	
	public static String  PrintPdf() {
        PrintService[] printServices = PrinterJob.lookupPrintServices();
        if (printServices == null || printServices.length == 0) {
            return "打印失败，未找到可用打印机，请检查。";
        }
        try {
        	System.out.println("打印机数量："+printServices.length);
            File file = new File(tmpPathPDF);
           
            PDDocument document = PDDocument.load(file);
            // 加载成打印文件
//            PDFPrintable printable = new PDFPrintable(document, Scaling.STRETCH_TO_FIT);
            PDFPrintable printable = new PDFPrintable(document, Scaling.SHRINK_TO_FIT);

            Book book = new Book();
            PageFormat pageFormat = new PageFormat();
            //设置打印方向 PORTRAIT 竖向  REVERSE_LANDSCAPE 横向
            pageFormat.setOrientation(PageFormat.REVERSE_LANDSCAPE);
            // 设置纸张
            book.append(printable, pageFormat, 1);

            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPageable(book);
//            job.setPrintable(printable);
            job.defaultPage();
            job.print();
            //打印后删除
            document.removePage(0);
            document.close();
            return  "打印成功";
        } catch (InvalidPasswordException e) {
            System.err.println("打印异常：文档初始化密码失败!");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("打印异常");
            e.printStackTrace();
        } catch (PrinterException e) {
            System.err.println("未正常连接打印机");
            e.printStackTrace();
        }
        return  "打印失败";
    }
	//打印文件
	public static String  PrintPdf(String filePath) {
        PrintService[] printServices = PrinterJob.lookupPrintServices();
        if (printServices == null || printServices.length == 0) {
            return "打印失败，未找到可用打印机，请检查。";
        }
        try {
            File file = new File(filePath);
            PDDocument document = PDDocument.load(file);
            // 加载成打印文件
            PDFPrintable printable = new PDFPrintable(document, Scaling.SCALE_TO_FIT);

            Book book = new Book();
            PageFormat pageFormat = new PageFormat();
            //设置打印方向 PORTRAIT 竖向  REVERSE_LANDSCAPE 横向
            pageFormat.setOrientation(PageFormat.REVERSE_LANDSCAPE);
            // 设置纸张
            book.append(printable, pageFormat, document.getNumberOfPages());

            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPageable(book);
//            job.setPrintable(printable);
            job.defaultPage();
            job.print();
            return  "打印成功";
        } catch (InvalidPasswordException e) {
            System.err.println("打印异常：文档初始化密码失败!");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("打印异常");
            e.printStackTrace();
        } catch (PrinterException e) {
            System.err.println("未正常连接打印机");
            e.printStackTrace();
        }
        return  "打印失败";
    }
	
	/*
	 * public static void generatePDF() throws Exception { try { // 1.新建document对象
	 * // Document document = new Document(PageSize.A4); // 建立一个Document对象 Document
	 * document = new Document(PageSize.A4.rotate());
	 * 
	 * // 2.建立一个书写器(Writer)与document对象关联 File file = new File(tmpPathPDF);
	 * file.createNewFile(); PdfWriter writer = PdfWriter.getInstance(document, new
	 * FileOutputStream(file)); // writer.setPageEvent(new
	 * Watermark("HELLO ITEXTPDF"));// 水印 // writer.setPageEvent(new
	 * MyHeaderFooter());// 页眉/页脚
	 * 
	 * // 3.打开文档 document.open(); document.addTitle("Title@PDF-Java");// 标题
	 * document.addAuthor("Author@NF");// 作者
	 * document.addSubject("Subject@iText pdf sample");// 主题
	 * document.addKeywords("Keywords@iTextpdf");// 关键字
	 * document.addCreator("Creator@umiz`s");// 创建者
	 * 
	 * // 4.向文档中添加内容 // wirtePDF(document);
	 * 
	 * com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(tmpPath);
	 * image.setAlignment(Image.ALIGN_CENTER); image.scalePercent(80,80); //依照比例缩放
	 * document.add(image); // 5.关闭文档 document.close(); } catch (Exception e) {
	 * e.printStackTrace(); } } //弃用 public PrintFrame(JFrame frame) throws
	 * HeadlessException { Toolkit kit = Toolkit.getDefaultToolkit(); Properties
	 * props = new Properties();
	 * 
	 * PrintJob printJob = kit.getPrintJob(frame, "Printe", props);
	 * 
	 * Graphics g = printJob.getGraphics(); Graphics graphics = g.create(0,0, 1024,
	 * 960); // 210mm×297mm try { frame.paintAll(graphics); } finally { g.dispose();
	 * } printJob.end(); }
	 * 
	 * 
	 * public static void savePic(JFrame jf){ //得到窗口内容面板 Container
	 * content=jf.getContentPane(); //创建缓冲图片对象 BufferedImage img=new BufferedImage(
	 * jf.getWidth(),jf.getHeight(),BufferedImage.TYPE_INT_RGB); //得到图形对象 Graphics2D
	 * g2d = img.createGraphics(); //将窗口内容面板输出到图形对象中 content.printAll(g2d); //保存为图片
	 * File f=new File(tmpPath); try { ImageIO.write(img, "jpg", f);
	 * cut(0,0,1000,744,tmpPath,tmpPath); } catch (IOException e) {
	 * e.printStackTrace(); } //释放图形对象 g2d.dispose(); }
	 * 
	 * public static void cut(int x, int y, int width, int height, String srcpath,
	 * String subpath) throws IOException {//裁剪方法 FileInputStream is = null;
	 * ImageInputStream iis = null; try { is = new FileInputStream(srcpath);
	 * //读取原始图片 Iterator<ImageReader> it =
	 * ImageIO.getImageReadersByFormatName("jpg"); //ImageReader声称能够解码指定格式
	 * ImageReader reader = it.next(); iis = ImageIO.createImageInputStream(is);
	 * //获取图片流 reader.setInput(iis, true); //将iis标记为true（只向前搜索）意味着包含在输入源中的图像将只按顺序读取
	 * ImageReadParam param = reader.getDefaultReadParam(); //指定如何在输入时从 Java Image
	 * I/O框架的上下文中的流转换一幅图像或一组图像 Rectangle rect = new Rectangle(x, y, width, height);
	 * //定义空间中的一个区域 param.setSourceRegion(rect); //提供一个 BufferedImage，将其用作解码像素数据的目标。
	 * BufferedImage bi = reader.read(0, param); //读取索引imageIndex指定的对象
	 * ImageIO.write(bi, "jpg", new File(subpath)); //保存新图片 } finally { if (is !=
	 * null) is.close(); if (iis != null) iis.close(); } }
	 */
	/*
	 * public static void savePic(JFrame jf){ //得到窗口内容面板 Container
	 * content=jf.getContentPane(); //创建缓冲图片对象 BufferedImage img=new BufferedImage(
	 * jf.getWidth(),jf.getHeight(),BufferedImage.TYPE_INT_RGB); //得到图形对象 Graphics2D
	 * g2d = img.createGraphics(); //将窗口内容面板输出到图形对象中 content.printAll(g2d); //保存为图片
	 * File f=new File("C:\\REID\\Fream.jpg"); try { ImageIO.write(img, "jpg", f); }
	 * catch (IOException e) { e.printStackTrace(); } //释放图形对象 g2d.dispose(); }
	 */
}
