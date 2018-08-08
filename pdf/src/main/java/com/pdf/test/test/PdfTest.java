package com.pdf.test.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.IOException;
import java.nio.channels.FileChannel;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import javax.imageio.ImageIO;

/**
 * @author : chenpeng
 * @date : 2018-08-07 09:54
 * 修改pdf
 */
public class PdfTest {
    public static void main(String[] args) throws IOException, DocumentException {
        String tmp="";
        PdfTest.fileChannelCopy(new File("D:\\temp\\myFirstPDF.pdf"),new File("D:\\temp\\myFirstPDF2.pdf"));
        PdfReader reader = new PdfReader("D:\\temp\\myFirstPDF.pdf");
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("D:\\temp\\myFirstPDF2.pdf"));
        PdfContentByte overContent = stamper.getOverContent(1);

        //添加文字
        //BaseFont font = BaseFont.createFont( "c://windows//fonts//simsun.ttc,1" , BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        BaseFont font = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        overContent.beginText();
        overContent.setFontAndSize(font, 10);
        overContent.setTextMatrix(200, 200);
        overContent.showTextAligned(Element.ALIGN_CENTER,"需要添加的文字",0,0,0);
        overContent.endText();

        //添加图片
        PdfDictionary pdfDictionary = reader.getPageN(1);
        PdfObject pdfObject =  pdfDictionary.get(new PdfName("MediaBox"));
        PdfArray pdfArray = (PdfArray) pdfObject;
        Image image = Image.getInstance("D:\\book\\tim.jpg");
        image.scaleToFit(100,100);

        image.setAbsolutePosition(0,0);
        overContent.addImage(image);

        //添加一个红圈
        overContent.setRGBColorStroke(0xFF, 0x00, 0x00);
        overContent.setLineWidth(5f);
        overContent.ellipse(0, 0, 0, 0);
        overContent.stroke();

        //签名





        stamper.close();


    }

    public static void fileChannelCopy(File sources, File dest) {
        try {
            FileInputStream inputStream = new FileInputStream(sources);
            FileOutputStream outputStream = new FileOutputStream(dest);
            FileChannel fileChannelin = inputStream.getChannel();//得到对应的文件通道
            FileChannel fileChannelout = outputStream.getChannel();//得到对应的文件通道
            fileChannelin.transferTo(0, fileChannelin.size(), fileChannelout);//连接两个通道，并且从in通道读取，然后写入out通道

            inputStream.close();
            fileChannelin.close();
            outputStream.close();
            fileChannelout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
