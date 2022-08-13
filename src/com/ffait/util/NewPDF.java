package com.ffait.util;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @Auther: NF
 * @Date: 2022/06/12/19:17
 * @Description:
 */
public class NewPDF {
    private static Font titlefont;
    private static Font headfont;
    private static Font keyfont;
    private static Font textfont;
    private static Font Namefont;
    // 最大宽度
    private static int maxWidth = 520;
    // 静态代码块
    static {
        try {
            // 不同字体（这里定义为同一种字体：包含不同字号、不同style）
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            // 楷体字
            BaseFont bfComic = BaseFont.createFont("c:\\windows\\fonts\\simkai.ttf",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            //  黑体
            BaseFont bfComic5 = BaseFont.createFont("c:\\windows\\fonts\\simhei.ttf",BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			/*
			 * // 方正舒体 BaseFont bfComic2 =
			 * BaseFont.createFont("c:\\windows\\fonts\\FZSTK.TTF",BaseFont.IDENTITY_H,
			 * BaseFont.NOT_EMBEDDED); // 方正姚体 BaseFont bfComic3 =
			 * BaseFont.createFont("c:\\windows\\fonts\\FZYTK.TTF", BaseFont.IDENTITY_H,
			 * BaseFont.NOT_EMBEDDED); // 仿宋体 BaseFont bfComic4 =
			 * BaseFont.createFont("c:\\windows\\fonts\\SIMFANG.TTF",BaseFont.IDENTITY_H,
			 * BaseFont.NOT_EMBEDDED);
			 * 
			 * // 华文彩云 BaseFont bfComic6 =
			 * BaseFont.createFont("c:\\windows\\fonts\\STCAIYUN.TTF", BaseFont.IDENTITY_H,
			 * BaseFont.NOT_EMBEDDED); //华文仿宋 BaseFont bfComic7 =
			 * BaseFont.createFont("c:\\windows\\fonts\\STFANGSO.TTF", BaseFont.IDENTITY_H,
			 * BaseFont.NOT_EMBEDDED); // 华文细黑 BaseFont bfComic8 =
			 * BaseFont.createFont("c:\\windows\\fonts\\STXIHEI.TTF",BaseFont.IDENTITY_H,
			 * BaseFont.NOT_EMBEDDED); // 华文新魏 BaseFont bfComic9=
			 * BaseFont.createFont("c:\\windows\\fonts\\STXINWEI.TTF",BaseFont.IDENTITY_H,
			 * BaseFont.NOT_EMBEDDED); //华文行楷 BaseFont bfComic0 =
			 * BaseFont.createFont("c:\\windows\\fonts\\STXINGKA.TTF",BaseFont.IDENTITY_H,
			 * BaseFont.NOT_EMBEDDED); // 华文中宋 BaseFont bfComic99 =
			 * BaseFont.createFont("c:\\windows\\fonts\\STZHONGS.TTF",BaseFont.IDENTITY_H,
			 * BaseFont.NOT_EMBEDDED); //隶书 BaseFont bfComic11=
			 * BaseFont.createFont("c:\\windows\\fonts\\SIMLI.TTF",BaseFont.IDENTITY_H,
			 * BaseFont.NOT_EMBEDDED);
			 */

//            titlefont = new Font(bfComic5, 22, Font.BOLD);
//            headfont = new Font(bfComic5, 14, Font.BOLD);
//            keyfont = new Font(bfComic, 20, Font.BOLD);
            textfont = new Font(bfComic, 20, Font.BOLD);
            Namefont = new Font(bfComic5, 22, Font.BOLD);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生产一个PDF文档
     * @param backgroundPath 背景图片路径
     * @param facePath      人脸图片路径
     * @param tName         姓名
     * @param tInfo         第二段描述文本
     * @param tReport       项目文本
     * @throws Exception
     */
    public static void createPDF(String backgroundPath,String facePath,String tName,String tInfo,String tReport) throws Exception {
		System.out.println("q:");

		System.out.println(tName);		
		System.out.println("----------------------------");

		System.out.println(tInfo);
		System.out.println("----------------------------");

		System.out.println(tReport);
		
		System.out.println("z:");


    	
        try {
        	//后四个数为打印边距
            Document document = new Document(PageSize.A4.rotate());

            File file = new File(PrintFrame.tmpPathPDF);
            if(!file.exists()) {
            	file.createNewFile();
            }
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            // 3.打开文档
            document.open();
            // 4.向文档中添加内容
            Image image = Image.getInstance(backgroundPath);
            image.setAlignment(Image.ALIGN_CENTER);
            image.scalePercent(80, 80); //依照比例缩放
            /* 设置图片的位置 */
            image.setAbsolutePosition(0, 0);
            /* 设置图片的大小 */
            image.scaleAbsolute(842, 595);
            document.add(image);

            Image face = Image.getInstance(facePath);
            face.setAbsolutePosition(713, 433);
            face.scaleAbsolute(71, 99);
            document.add(face);


            Paragraph empty = new Paragraph("\n\n\n\n\n", textfont);
            empty.setAlignment(1); //设置文字居中 0靠左   1，居中     2，靠右

            document.add(empty);
            // 段落
            Paragraph textName = new Paragraph(tName, Namefont);
            textName.setAlignment(1); //设置文字居中 0靠左   1，居中     2，靠右
            textName.setIndentationLeft(12); //设置左缩进
            textName.setIndentationRight(12); //设置右缩进
//            textName.setFirstLineIndent(24); //设置首行缩进
            textName.setLeading(20f); //行间距
            textName.setSpacingBefore(5f); //设置段落上空白
            textName.setSpacingAfter(10f); //设置段落下空白
            document.add(textName);

            Paragraph textInfo = new Paragraph(tInfo+"\n\n", textfont);
            textInfo.setAlignment(1); //设置文字居中 0靠左   1，居中     2，靠右
            textInfo.setIndentationLeft(12); //设置左缩进
            textInfo.setIndentationRight(12); //设置右缩进
            textInfo.setFirstLineIndent(24); //设置首行缩进
            textInfo.setLeading(20f); //行间距
            textInfo.setSpacingBefore(5f); //设置段落上空白
            textInfo.setSpacingAfter(10f); //设置段落下空白
            document.add(textInfo);

            Paragraph textReport = new Paragraph(tReport, textfont);
            textReport.setAlignment(1); //设置文字居中 0靠左   1，居中     2，靠右
            textReport.setIndentationLeft(12); //设置左缩进
            textReport.setIndentationRight(12); //设置右缩进
//            textReport.setFirstLineIndent(24); //设置首行缩进
            textReport.setLeading(20f); //行间距
            textReport.setSpacingBefore(5f); //设置段落上空白
            textReport.setSpacingAfter(10f); //设置段落下空白
            document.add(textReport);

            // 5.关闭文档
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
