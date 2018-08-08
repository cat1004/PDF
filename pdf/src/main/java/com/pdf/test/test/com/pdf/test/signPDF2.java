/*
package com.pdf.test.test.com.pdf.test;

import com.itextpdf.text.DocumentException;
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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;

import javax.swing.*;

import static com.itextpdf.tool.xml.html.HTML.Attribute.SRC;

*/
/**
 * @author : chenpeng
 * @date : 2018-08-07 15:12
 * 有错
 *//*

public class signPDF2 {

    public void sign(String src, String dest,
                     Certificate[] chain,
                     PrivateKey pk, String digestAlgorithm, String provider,
                     MakeSignature.CryptoStandard subfilter,
                     String reason, String location)
            throws GeneralSecurityException, IOException, DocumentException {
        // Creating the reader and the stamper
        PdfReader reader = new PdfReader(src);
        FileOutputStream os = new FileOutputStream(dest);
        PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0');
        // Creating the appearance
        PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
        appearance.setReason(reason);
        appearance.setLocation(location);
        appearance.setVisibleSignature(new Rectangle(36, 748, 144, 780), 1, "sig");
        // Creating the signature
        ExternalDigest digest = new BouncyCastleDigest();
        ExternalSignature signature = new PrivateKeySignature(pk, digestAlgorithm, provider);
        MakeSignature.signDetached(appearance, digest, signature, chain, null, null, null, 0, subfilter);
    }

    public static void sign(String markImagePath,SignPDF signInfo) throws Exception {

        KeyStore ks = KeyStore.getInstance("pkcs12");
        ks.load(new FileInputStream(signInfo.getKeySorePath()), signInfo.getPassword().toCharArray());
        String alias = (String)ks.aliases().nextElement();
        PrivateKey pk = (PrivateKey)ks.getKey(alias, signInfo.getPassword().toCharArray());
        Certificate[] chain = ks.getCertificateChain(alias);
        BouncyCastleProvider provider = new BouncyCastleProvider();
        Security.addProvider(provider);
        // Creating the reader and the stamper

        PdfReader reader = new PdfReader(signInfo.getPdfPath());

        Rectangle pageSize = reader.getPageSize(signInfo.getPageNum());

        String tempPath = signInfo.getPdfPath().replace(".", "_new.");
        FileOutputStream os = new FileOutputStream(tempPath);

        PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0');
        // Creating the appearance
        PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
        // appearance.setReason(signInfo.getReason());
        appearance.setLocation("this is the location");
        Image img = Image.getInstance(markImagePath);

        //float newHeight = (float) (signInfo.getHeight()*0.8f);
        //float newWeight = (float) (signInfo.getWidth()*0.8f);
        //img.scaleAbsolute(newWeight, newHeight);
        float width = pageSize.getWidth();
        float height = pageSize.getHeight();
        float realWidth = (float) (width*signInfo.getWidth());
        float realHeight = (float) (height*signInfo.getHeight());
        float[] start = new float []{(float) signInfo.getPointX()*width,(float) signInfo.getPointY()*height};
        appearance.setVisibleSignature(
                new Rectangle(
                        (float) start[0],
                        (float) (start[1]-realHeight),
                        (float) (start[0]+realWidth),
                        (float) (start[1])),signInfo.getPageNum(),"Signature1");
        // Custom text and background image
        appearance.setLayer2Text("");
        appearance.setImage(img);
        appearance.setImageScale((float) ((signInfo.getWidth())/img.getWidth()));
        // Creating the signature
        PrivateKeySignature pks = new PrivateKeySignature(pk, DigestAlgorithms.SHA256, provider.getName());
        ExternalDigest digest = new BouncyCastleDigest();
        MakeSignature.signDetached(appearance, digest, pks, chain, null, null, null, 0, MakeSignature.CryptoStandard.CMS);
        //File srcFile = new File(signInfo.getPdfPath());
        //srcFile.delete();
        //File desFile = new File(tempPath);
        //desFile.renameTo(srcFile);

    }
    }
*/
