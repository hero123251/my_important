package com.xuecheng;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestJvm {
    public static void main(String[] args) {
        a();
        System.out.println("main方法。。");
    }

    private static void a() {
        b();
    }

    private static void b() {
        c();
    }

    private static void c() {
        System.out.println("ccccc");
    }

    int i =1;
    @Test
    public void testStack(){

        i++;
        System.out.println(i);
        testStack();

    }

    @Test
    public void testHeap(){
        List<String> l = new ArrayList<String>();
        String s = "abc";
        String ss = "123";
        while (true){
            s+=ss;
            l.add(s);
        }
    }



}
