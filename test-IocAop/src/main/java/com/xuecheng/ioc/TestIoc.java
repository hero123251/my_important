package com.xuecheng.ioc;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.xuecheng.TestIocAop;
import com.xuecheng.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestIoc {

    public static void main(String[] args) {



        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestIocAop.class);
        User user = applicationContext.getBean("user", User.class);
        People people1 = applicationContext.getBean("people", People.class);
//        user.setUsername("小程程");
//        User user1 = new User();
//        user1.setUsername("对象小程程");
        People people2 = new People();
        System.out.println("Bean:" + people1);


        System.out.println("对象：" + people2);
    }
}