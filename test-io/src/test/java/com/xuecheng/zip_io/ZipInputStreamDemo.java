package com.xuecheng.zip_io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipInputStreamDemo {

    public static void main(String[] args) throws Exception {
        decompressionFile("D:/banner1.zip","D:/tmp/");
    }



    public static void decompressionFile(String srcPath, String outPath) throws Exception {
        //zip读取压缩文件
        FileInputStream fileInputStream = new FileInputStream(srcPath);
        ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);


        //解压文件
        ZipEntry nextEntry = null;

        //不为空进入循环
        while ((nextEntry =zipInputStream.getNextEntry()) != null) {
            String name = nextEntry.getName();
            System.out.println(name);
            File file = new File(outPath+name);


            //如果是目录，创建目录
            if (name.endsWith("/")) {
                file.mkdir();
            } else {
                //文件则写入具体的路径中
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                int n;
                byte[] bytes = new byte[1024];
                while ((n = zipInputStream.read(bytes)) != -1) {
                    bufferedOutputStream.write(bytes, 0, n);
                }
                //关闭流
                bufferedOutputStream.close();
                fileOutputStream.close();
            }
        }
        //关闭流
        zipInputStream.close();
        fileInputStream.close();
    }

}
