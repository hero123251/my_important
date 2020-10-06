package com.xuecheng.aop.service.declareparents;

public class TestI implements TestInter {
    public void eat() {
        System.out.println("被植入对象。。。");
    }
}
