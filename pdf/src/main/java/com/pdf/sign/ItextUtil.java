package com.pdf.sign;

import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.BouncyCastleDigest;
import com.itextpdf.text.pdf.security.DigestAlgorithms;
import com.itextpdf.text.pdf.security.ExternalDigest;
import com.itextpdf.text.pdf.security.ExternalSignature;
import com.itextpdf.text.pdf.security.MakeSignature;
import com.itextpdf.text.pdf.security.PrivateKeySignature;
import com.pdf.encryption.SignatureInfo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;

/**
 * @author : chenpeng
 * @date : 2018-08-08 09:56
 */
public class ItextUtil {
  public static final char[] PASSWORD = "123456".toCharArray(); // keystory密码

  @SuppressWarnings("resource")
  public void sign(String src, String target, SignatureInfo signatureInfo) {
    InputStream inputStream = null;
    FileOutputStream outputStream = null;
    ByteArrayOutputStream result = new ByteArrayOutputStream();
    try {
      // 原有资源
      inputStream = new FileInputStream(src);
      // 字节数组输出流在内存
      ByteArrayOutputStream tempArrayOutputStream = new ByteArrayOutputStream();
      // 读取原文件内容
      PdfReader reader = new PdfReader(inputStream);
      // 创建签章工具PdfStamper ，最后一个boolean参数是否允许被追加签名
      // false的话，pdf文件只允许被签名一次，多次签名，最后一次有效,写入签署人名称到内容
      // true的话，pdf可以被追加签名，验签工具可以识别出每次签名之后文档是否被修改

      // 读取pdf内容,字节数组流,pdf版本,生成文件如果文件不存在创建一个字节数组 ,是否能后追加签名,先将一部分数据写入到内存中()
      PdfStamper stamper =
          PdfStamper.createSignature(reader, tempArrayOutputStream, '\0', null, true);
      // 获取数字签章属性对象
      PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
      appearance.setReason(signatureInfo.getReason());
      appearance.setLocation(signatureInfo.getLocation());
      // 设置签名的位置，页码，签名域名称，多次追加签名的时候，签名预名称不能一样 图片大小受表单域大小影响（过小导致压缩）
      // 签名的位置，是图章相对于pdf页面的位置坐标，原点为pdf页面左下角
      // 四个参数的分别是，图章左下角x，图章左下角y，图章右上角x，图章右上角y
      appearance.setVisibleSignature(
          new Rectangle(
              signatureInfo.getRectllx(),
              signatureInfo.getRectlly(),
              signatureInfo.getRecturx(),
              signatureInfo.getRectury()),
          1,
          signatureInfo.getFieldName());
      // 读取图章图片
      Image image = Image.getInstance(signatureInfo.getImagePath());
      appearance.setSignatureGraphic(image);
      appearance.setCertificationLevel(signatureInfo.getCertificationLevel());
      // 设置图章的显示方式，如下选择的是只显示图章（还有其他的模式，可以图章和签名描述一同显示）
      appearance.setRenderingMode(signatureInfo.getRenderingMode());
      // 摘要算法
      ExternalDigest digest = new BouncyCastleDigest();
      // 签名算法
      ExternalSignature signature =
          new PrivateKeySignature(signatureInfo.getPk(), signatureInfo.getDigestAlgorithm(), null);
      // 调用itext签名方法完成pdf签章 //数字签名格式，CMS,CADE
      MakeSignature.signDetached(
          appearance,
          digest,
          signature,
          signatureInfo.getChain(),
          null,
          null,
          null,
          0,
          MakeSignature.CryptoStandard.CADES);

      inputStream = new ByteArrayInputStream(tempArrayOutputStream.toByteArray());
      // 定义输入流为生成的输出流内容，以完成多次签章的过程
      result = tempArrayOutputStream;

      outputStream = new FileOutputStream(new File(target));
      outputStream.write(result.toByteArray());
      outputStream.flush(); 
      outputStream.close();
      inputStream.close();
        
      result.close();
    } catch (Exception e) {
      e.printStackTrace();
      outputStream.close();
      inputStream.close();
      result.close();
    }
  }

  public static void main(String[] args) {
    try {
      ItextUtil app = new ItextUtil();
      // 将证书文件放入指定路径，并读取keystore ，获得私钥和证书链
      String pkPath = "D:\\keys\\client1.p12";
      KeyStore ks = KeyStore.getInstance("PKCS12");
      ks.load(new FileInputStream(pkPath), PASSWORD);
      String alias = ks.aliases().nextElement();
      PrivateKey pk = (PrivateKey) ks.getKey(alias, PASSWORD);
      // 得到证书链
      Certificate[] chain = ks.getCertificateChain(alias);
      // 需要进行签章的pdf
      String path = "D:\\temp\\myFirstPDF.pdf";
      // 封装签章信息
      SignatureInfo signInfo = new SignatureInfo();
      signInfo.setReason("理由");
      signInfo.setLocation("位置");
      signInfo.setPk(pk);
      signInfo.setChain(chain);
      signInfo.setCertificationLevel(PdfSignatureAppearance.NOT_CERTIFIED);
      signInfo.setDigestAlgorithm(DigestAlgorithms.SHA1);
      signInfo.setFieldName("demo");
      // 签章图片
      signInfo.setImagePath("D:\\temp\\123.png");
      signInfo.setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC);
      signInfo.setRectllx(100); // 值越大，代表向x轴坐标平移 缩小 （反之，值越小，印章会放大）
      signInfo.setRectlly(200); // 值越大，代表向y轴坐标向上平移（大小不变）
      signInfo.setRecturx(400); // 值越大   代表向x轴坐标向右平移  （大小不变）
      signInfo.setRectury(100); // 值越大，代表向y轴坐标向上平移（大小不变）
      // 签章后的pdf路径
      app.sign(path, "D:/demo3.pdf", signInfo);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
