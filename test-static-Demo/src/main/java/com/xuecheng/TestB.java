package com.xuecheng;

public class TestB extends TestA{
    static {
        System.out.println("TestB-static");
    }


    {
        System.out.println("B同步代码块。。。");
    }
    public TestB(){
        System.out.println("TestB构造方法。。");
    }



}
