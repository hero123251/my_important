package com.xuecheng.aop.service;

import org.springframework.stereotype.Component;

@Component("s")
public class TestService  {
    public String ss(String s){
        System.out.println("目标方法执行..." +s);
        Teacher teacher = new Teacher();
        return "成功返回值";
    }
}
