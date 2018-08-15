package com.pdf.image;

import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

/**
 * @author : chenpeng
 * @date : 2018-08-14 14:45 根据pdf 生成image
 *     <p>注意因为jar 包可能引起冲突 故这里将 <dependency> <groupId>org.bouncycastle</groupId>
 *     <artifactId>bcprov-jdk15on</artifactId> <version>1.49</version> </dependency> <dependency>
 *     <groupId>org.bouncycastle</groupId> <artifactId>bcpkix-jdk15on</artifactId>
 *     <version>1.49</version> </dependency> 注释掉.
 */
public class PdfGenerateImage {

  public static void main(String[] args) {

    String path = PdfGenerateImage.class.getResource("/xfasdf.pdf").getPath();
    String outpath = path.substring(1, path.lastIndexOf(".")) + ".jpg";
    System.out.println(outpath);
    PdfGenerateImage.generateBookIamge(path, outpath);
    System.out.println("Success");
  }
  /**
   * 生成一本书的缩略图
   *
   * @param inputFile 需要生成缩略图的书籍的完整路径
   * @param outputFile 生成缩略图的放置路径
   */
  public static void generateBookIamge(String inputFile, String outputFile) {
    Document document = null;

    try {
      float rotation = 0f;
      // 缩略图显示倍数，1表示不缩放，0.5表示缩小到50%
      float zoom = 0.8f;

      document = new Document();
      document.setFile(inputFile);
      // maxPages = document.getPageTree().getNumberOfPages();

      BufferedImage image =
          (BufferedImage)
              document.getPageImage(
                  0, GraphicsRenderingHints.SCREEN, Page.BOUNDARY_CROPBOX, rotation, zoom);

      Iterator iter = ImageIO.getImageWritersBySuffix("jpg");
      ImageWriter writer = (ImageWriter) iter.next();

      FileOutputStream out = new FileOutputStream(new File(outputFile));
      ImageOutputStream outImage = ImageIO.createImageOutputStream(out);

      writer.setOutput(outImage);
      writer.write(new IIOImage(image, null, null));

    } catch (Exception e) {
      System.out.println("to generate thumbnail of a book fail : " + inputFile);
      System.out.println(e);
    }
  }
}
