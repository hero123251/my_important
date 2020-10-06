package com.xuecheng.aop;

import com.xuecheng.aop.config.AopConfig;
import com.xuecheng.aop.service.AnnonTest;
import com.xuecheng.aop.service.ArgsTest;
import com.xuecheng.aop.service.Teacher;
import com.xuecheng.aop.service.TestService;
import com.xuecheng.aop.service.declareparents.TestI;
import com.xuecheng.aop.service.declareparents.TestInter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Demo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AopConfig.class);
        TestService s = (TestService) applicationContext.getBean("s");
//        ArgsTest arg = (ArgsTest) applicationContext.getBean("arg");
//        TestInter s = (TestInter) applicationContext.getBean("s");
        s.ss("String参数。。");

    }
}