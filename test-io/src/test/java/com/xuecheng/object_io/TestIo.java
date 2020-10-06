package com.xuecheng.object_io;

import com.xuecheng.object_io.domain.People;
import org.junit.Test;

import java.io.*;

public class TestIo {

    //对象在网络中传输，要实现序列化
    @Test
    public void testObjectIoOut() throws IOException, InterruptedException {
        ObjectOutputStream oop = new ObjectOutputStream(new FileOutputStream("F:\\Test.txt"));

        People p = new People();
        p.setAge(1);
        p.setUsername("zhangsan");

        oop.writeObject(p);
        oop.close();
    }


    @Test
    public void testObjectIoRead() throws Exception {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("F:\\Test.txt"));
        Object o = ois.readObject();
        System.out.println(o);
        ois.close();
    }
}