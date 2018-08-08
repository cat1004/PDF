package com.pdf.test.test;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author : chenpeng
 * @date : 2018-08-07 10:22
 * 创建pdf
 */
public class PdfTest2 {
    public static void main(String[] args) {
        PdfTest2 pdf = new PdfTest2();
        String fileName = "D:/temp/myFirstPDF.pdf";
        File file = new File(fileName);

//创建一个文件之前判断他的父路径的文件夹是否存在，不存在需要创建

        if (!new File(file.getParent()).exists())

            new File(file.getParent()).mkdirs();

        try {

//创建该文件夹
            file.createNewFile();
        } catch (IOException e1) {
// TODO Auto-generated catch block
            e1.printStackTrace();
        }
        pdf.createPDF(fileName);
    }
    //生成PDF文件中的内容
    public void createPDF(String fileName){
        File file = new File(fileName);
        FileOutputStream out = null ;
        Document document = new Document(PageSize.A4,50,50,50,50);
        try{
            //实例化文档对象
            out = new FileOutputStream(file);
            //创建写入器
            @SuppressWarnings("unused")
            PdfWriter writer = PdfWriter.getInstance(document,out);
            //打开文档准备写入内容
            document.open();
            document.add(new Paragraph("list-中国"));
            //关闭文档
            document.close();
            System.out.println("PDF文件生成成功，PDF文件名："+file.getAbsolutePath());
        } catch(DocumentException e){
            System.out.println("PDF文件"+file.getAbsolutePath()+"生成失败！"+e);
            e.printStackTrace();
        } catch(IOException ee){
            System.out.println("PDF文件"+file.getAbsolutePath()+"生成失败！"+ee);
            ee.printStackTrace();
        } finally{
            if(out!=null){
                try{
                    //关闭输出文件流
                    out.close();
                }catch(IOException e1){
                    e1.printStackTrace();
                }
            }
        }
    }


}
