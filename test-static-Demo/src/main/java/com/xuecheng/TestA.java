package com.xuecheng;

public class TestA {
    static {
        System.out.println("TestA-static");
    }

    {
        System.out.println("A同步代码块。。。");
    }

    public TestA(){
        System.out.println("TestA构造方法");
    }


}
