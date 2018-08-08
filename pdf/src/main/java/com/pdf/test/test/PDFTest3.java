package com.pdf.test.test;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.text.Document;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author : chenpeng
 * @date : 2018-08-07 11:09
 * 根据网页内容生成pdf
 */
public class PDFTest3 {
    private static final String DEST = "/HelloWorld_CN_HTML.pdf";
    private static final String FONT = "c://windows//fonts//simsun.ttc";
    private static final String HTML = "D:\\github\\Temple\\pdf\\src\\main\\resources\\template.html";

    public static void main(String[] args) throws IOException, DocumentException {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DEST));
        // step 3
        document.open();
        // step 4
        XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
        fontImp.register(FONT);
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new FileInputStream(HTML));
        // step 5
        document.close();
    }
}
