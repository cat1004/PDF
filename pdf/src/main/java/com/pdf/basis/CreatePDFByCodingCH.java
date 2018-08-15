package com.pdf.basis;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author : chenpeng
 * @date : 2018-08-07 11:30
 *     <p>中文解决方案 总结一下,基本上有三种方法解决iText显示中文问题。
 *     <p>方法一:使用Windows系统字体(TrueType)
 *     <p>方法二:使用iTextAsian.jar中的字体
 *     <p>方法三:使用资源字体(ClassPath)
 */
public class CreatePDFByCodingCH {

  private static final String DEST = "/xfasdf.pdf";

  public static void main(String[] args) throws IOException, DocumentException {

    // 设置纸张
    Rectangle rect = new Rectangle(PageSize.A4);

    BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

    Font textFont = new Font(bfChinese, 11, Font.NORMAL); // 正常

    // 创建文档实例
    Document document = new Document(rect);
    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DEST));
    document.open();
    // Font f1 = FontFactory.getFont(FONT, BaseFont.WINANSI, BaseFont.EMBEDDED, 12);

    // 使用Windows系统字体(TrueType)
    // BaseFont baseFont =
    // BaseFont.createFont("C://Windows//Fonts//simsunb.ttf",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);

    // 方法二:使用iTextAsian.jar中的字体
    // BaseFont baseFont =
    // BaseFont.createFont("STSong-Light",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);

    // 方法三:使用资源字体(ClassPath)
    // BaseFont baseFont =
    // BaseFont.createFont("/SIMYOU.TTF",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
    document.add(new Paragraph("hello world,我是鲁家宁", textFont));
    document.close();
    writer.close();
  }
}
