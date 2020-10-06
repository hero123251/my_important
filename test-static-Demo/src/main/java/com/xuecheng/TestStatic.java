package com.xuecheng;

public class TestStatic {


    public static void main(String[] args) {
//        TestA testA = new TestA();
        TestB testB = new TestB();
        //TestA-static   TestA构造方法    TestB-static  TestB构造方法。。
        //TestA-static   TestB-static    TestA构造方法  TestB构造方法。。
        //TestA-static   TestB-static    A同步代码块。。。  TestA构造方法  B同步代码块。。。  TestB构造方法。。
        //TestA testA = new TestA();  TestB testB = new TestB();  TestA-static   A同步代码块。。。  TestA构造方法   TestB-static    A同步代码块。。。  TestA构造方法 B同步代码块。。。  TestB构造方法。。
    }
}