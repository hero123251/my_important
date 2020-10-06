package com.xuecheng.zip_io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipOutputStreamDemo {

    public static void main(String[] args) throws Exception {
        compressFile("D:/tmp","D:/b.zip");
    }

    /**
     * 提供给用户使用的基本压缩类
     * @param srcPath
     * @param outPath
     * @throws Exception
     */
    public static void compressFile(String srcPath, String outPath) throws Exception {
        //读取源文件
        File srcFile = new File(srcPath);
        //判断输出路径是否正确
        File outFile = new File(outPath);

        //读取文件流
        FileOutputStream fileOutputStream = new FileOutputStream(outPath);
        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);

        //压缩文件
        compressFile(srcFile, srcFile.getName(),zipOutputStream);

        //关闭流
        zipOutputStream.close();
        fileOutputStream.close();
    }

    /**
     * 迭代方式进行文件压缩
     * @param file
     * @param fileName
     * @param outputStream
     * @throws Exception
     */
    private static void compressFile(File file, String fileName, final ZipOutputStream outputStream) throws Exception {
        //如果是目录
        if (file.isDirectory()) {
            //创建文件夹
            outputStream.putNextEntry(new ZipEntry(fileName+"/"));

            //迭代判断，并且加入对应文件路径
            File[] files = file.listFiles();
            Iterator<File> iterator = Arrays.asList(files).iterator();
            while (iterator.hasNext()) {
                File f = iterator.next();
                compressFile(f, fileName+"/"+f.getName(), outputStream);
            }
        } else {
            //创建文件
            outputStream.putNextEntry(new ZipEntry(fileName));
            //读取文件并写出
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            byte[] bytes = new byte[1024];
            int n;
            while ((n = bufferedInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, n);
            }
            //关闭流
            fileInputStream.close();
            bufferedInputStream.close();
        }
    }

}
