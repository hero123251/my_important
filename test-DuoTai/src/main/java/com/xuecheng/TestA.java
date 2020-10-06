package com.xuecheng;

public class TestA extends TestAbstract{

    Integer a = 1;

    public void eat(){
        System.out.println("A...eat"+a);
    }


    public void play() {
        System.out.println("普通类的play方法。。。");
    }
}