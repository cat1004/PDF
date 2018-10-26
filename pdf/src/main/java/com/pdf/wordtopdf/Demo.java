package com.pdf.wordtopdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.BouncyCastleDigest;
import com.itextpdf.text.pdf.security.ExternalDigest;
import com.itextpdf.text.pdf.security.ExternalSignature;
import com.itextpdf.text.pdf.security.MakeSignature;
import com.itextpdf.text.pdf.security.PrivateKeySignature;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;

import javax.swing.*;

/** @Autherchenpeng @Date2018/10/13 12:11 @Description: */
public class Demo {
  public static final char[] PASSWORD = "123456".toCharArray();

  public static void main(String[] args) { // 利用word 生成的表单模板 产生新的pdf

    // 模板路径
    String templatePath = "/data/log/dev/evidenceplatform-evidence/template.pdf";
    // 生成的新文件路径
    String newPDFPath = "/data/log/dev/evidenceplatform-evidence/template1.pdf";
    PdfReader reader;
    FileOutputStream out;
    ByteArrayOutputStream bos;
    PdfStamper stamper;
    try {
      out = new FileOutputStream(newPDFPath); // 输出流
      reader = new PdfReader(templatePath); // 读取pdf模板
      bos = new ByteArrayOutputStream();
      stamper = new PdfStamper(reader, bos);
      AcroFields form = stamper.getAcroFields();

      String[] str = {
        "f6900f73-90f3-450c-a832-e4fc3dc9e538",
        "2018年09月05日 21:13:59",
        "cf06dd92cb494d292a3fc397fa8c7f7903ac346b",
        "我是十恶",
        "mb.pdf",
        "01e43bbf-cdfb-11e8-a43d-c85b76d64ba2"
      };
      int i = 0;
      java.util.Iterator<String> it = form.getFields().keySet().iterator();
      while (it.hasNext()) {
        String name = it.next();
        System.out.println(name);
        System.out.println(str[i]);
        form.setField(name, str[i++]);
      }
      stamper.setFormFlattening(true); // 如果为false那么生成的PDF文件还能编辑，一定要设为true
      stamper.close();

      Document doc = new Document();
      PdfCopy copy = new PdfCopy(doc, out);
      doc.open();
      PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
      copy.addPage(importPage);

      doc.close();

      // 签名

      PrivateKey pk = null;
      Certificate[] chain = null;

      String keystore = "/data/log/dev/evidenceplatform-evidence/beiming.p12";
      KeyStore ks = KeyStore.getInstance("PKCS12");
      ks.load(new FileInputStream(keystore), PASSWORD);
      String alias = (String) ks.aliases().nextElement();
      pk = (PrivateKey) ks.getKey(alias, PASSWORD);
      chain = ks.getCertificateChain(alias);
      reader = new PdfReader(newPDFPath);
      String dest = "/data/log/dev/evidenceplatform-evidence/singn.pdf";
      FileOutputStream os = new FileOutputStream(dest);
      stamper = PdfStamper.createSignature(reader, os, '\000');

      PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
      appearance.setReason("认证");
      appearance.setLocation("认证中心");

      // 四个参数的分别是，图章左下角x，图章左下角y，图章右上角x，图章右上角y
      appearance.setVisibleSignature(new Rectangle(400, 80, 510, 200), 1, "sig1");
      String img = "C:\\Users\\chenpeng\\Desktop\\sig.png";
      Image image = Image.getInstance(img);
      appearance.setSignatureGraphic(image);
      appearance.setCertificationLevel(0);

      appearance.setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC);

      ExternalDigest digest = new BouncyCastleDigest();
      ExternalSignature signature = new PrivateKeySignature(pk, "SHA-1", null);
      MakeSignature.signDetached(
          appearance,
          digest,
          signature,
          chain,
          null,
          null,
          null,
          0,
          MakeSignature.CryptoStandard.CMS);
    } catch (Exception e) {
      System.out.println(e);
      e.printStackTrace();
    }
  }
}
