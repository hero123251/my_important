package com.xuecheng.manage_media;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ManageTest {

    //文件的分块操作
    @Test
    public void testChunk() throws Exception {
        //源文件路径
        File sourceFile = new File("E:\\amyFile03\\15学成在线\\day14 媒资管理\\资料\\lucene.avi");
        //分块后的文件的位置
//        File chunkpath = new File("E:\\amyFile03");
        String chunkpath = "E:\\amyFile03\\";

        //每块文件的大小
        long chunkSize = 1 * 1024 * 1024;

        //分的块数
        long chunkNum = (long) Math.ceil(sourceFile.length() * 1.0 / chunkSize);

        //用RandomAccessFile读源文件
        RandomAccessFile read = new RandomAccessFile(sourceFile, "r");


        //缓冲区
        byte[] b = new byte[1024];
        //遍历分块文件
        for (long i = 0; i < chunkNum; i++) {
            //创建分块文件
            File file = new File(chunkpath + i);
            boolean newFile = file.createNewFile();
            if (newFile) {
                //向分块文件中写数据
                RandomAccessFile write = new RandomAccessFile(file, "rw");
                int len = -1;
                while ((len = read.read(b)) != -1) {
                    write.write(b, 0, len);
                    //当每块文件达到1MB时，停止读写，进入下一块文件的读取
                    if (file.length() >= chunkSize) {
                        break;
                    }

                }
                write.close();
            }

        }

        read.close();

    }

    //分块文件的合并
    @Test
    public void merageChunks() throws Exception {
        //分块文件的路径
        String chunkpath = "E:\\amyFile03\\fill\\";

        File sourseFile = new File(chunkpath);
        //分块文件的列表
        File[] files = sourseFile.listFiles();
        //为了排序，将数组转为集合
        //注意这块，Arrays的包，要正确导入
        List<File> filelist = Arrays.asList(files);
        Collections.sort(filelist, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (Integer.parseInt(o1.getName()) < Integer.parseInt(o2.getName())) {
                    return -1;
                }

                return 1;
            }
        });


        //合并后的文件路径
        File sourceFile = new File("E:\\amyFile03\\15学成在线\\day14 媒资管理\\资料\\luceneMerage.avi");



        //写文件对象
        RandomAccessFile randomwrite = new RandomAccessFile(sourceFile,"rw");

        //缓冲区
        byte[] b = new byte[1024];

        //合并文件
        for (File file : filelist) {

            //读文件对象
            RandomAccessFile randomread =new RandomAccessFile(file,"rw");
            int len =-1;
            while ((len=randomread.read(b))!=-1){
                randomwrite.write(b,0,len);
            }
            randomread.close();
        }


        randomwrite.close();
    }
}
