package com.xuecheng.aop.service;

import org.springframework.stereotype.Component;

@Component("arg")
public class ArgsTest {

    public void test001(Teacher teacher){
        System.out.println("参数目标方法执行。。");
    }
}
