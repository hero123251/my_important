package com.xuecheng.ioc.factory;

import com.xuecheng.TestIocAop;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(TestIocAop.class);
        TestFactory testFactory = app.getBean("testFactory", TestFactory.class);
        Stuent object = testFactory.getObject();
        System.out.println(object);
    }
}
