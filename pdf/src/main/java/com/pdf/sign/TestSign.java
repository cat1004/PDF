package com.pdf.sign;

import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;

/**
 * @author : chenpeng
 * @date : 2018-08-07 15:54 将证书以及 签章内容放入 pdf
 */
public class TestSign {

  public static void sign() throws Exception {
    KeyStore ks = KeyStore.getInstance("pkcs12");
    ks.load(new FileInputStream("demo.p12"), "123456".toCharArray()); // 123456为私钥密码
    String alias = (String) ks.aliases().nextElement();
    PrivateKey key = (PrivateKey) ks.getKey(alias, "123456".toCharArray());
    Certificate[] chain = ks.getCertificateChain(alias);
    PdfReader reader = new PdfReader("demo.pdf"); // 源文件
    FileOutputStream fout = new FileOutputStream("z.pdf");
    PdfStamper stp = PdfStamper.createSignature(reader, fout, '\0');
    PdfSignatureAppearance sap = stp.getSignatureAppearance();
    // sap.setCrypto(key, chain, null, PdfSignatureAppearance.VERISIGN_SIGNED);
    sap.setReason("");
    sap.setLocation(""); // 添加位置信息，可为空
    sap.setContact("http://swordshadow.iteye.com/");
    Image image = Image.getInstance("sign.png"); // 使用png格式透明图片

    sap.setSignatureGraphic(image);
    sap.setAcro6Layers(true);
    sap.setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC);
    sap.setVisibleSignature(new Rectangle(300, 600, 400, 675), 1, null); // 300和600 是对应x轴和y轴坐标
    stp.getWriter().setCompressionLevel(5);
    if (stp != null) {
      stp.close();
    }
    if (fout != null) {
      fout.close();
    }
    if (reader != null) {
      reader.close();
    }
  }

  public static void main(String[] args) {
    try {
      sign();
      System.out.println("done!!");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
