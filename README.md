# PDF
有关PDF相关内容
basis:基础pdf 生成文件
引用jar为:

        <dependency>
            <groupId>com.itextpdf</groupId>
            <artifactId>itextpdf</artifactId>
            <version>5.4.3</version>
        </dependency>
        <dependency>
            <groupId>com.itextpdf.tool</groupId>
            <artifactId>xmlworker</artifactId>
            <version>5.5.11</version>
        </dependency>
        <dependency>
            <groupId>com.itextpdf</groupId>
            <artifactId>itext-asian</artifactId>
            <version>5.2.0</version>

        </dependency>
        以下是解决中文字体写入不进去问题,引入的包,如果用自带的字体库,不用引入
       <dependency>
            <groupId>org.bouncycastle</groupId>
            <artifactId>bcprov-jdk15on</artifactId>
            <version>1.49</version>
        </dependency>
        
encryption:签名验证相关内容
sign:签名相关,图章相关
image:生成图片相关内容


PDF图片预览jar包:
<dependency>
            <groupId>org.icepdf.os</groupId>
            <artifactId>icepdf-core</artifactId>
            <version>6.2.2</version>
        </dependency>
        <!--pdf文件转图片-->
        <!-- https://mvnrepository.com/artifact/org.apache.pdfbox/pdfbox -->
        <dependency>
            <groupId>org.apache.pdfbox</groupId>
            <artifactId>pdfbox</artifactId>
            <version>2.0.8</version>
        </dependency>
有以上两个请对应,引入.
另外PdfGenertelImageMany.jar文件运行会有报错,但不影响使用.目前尚为解决.
        
        
