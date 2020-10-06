package com.xuecheng.aop.service;

import com.xuecheng.aop.annontation.ZiDin;
import org.springframework.stereotype.Component;

@Component("a")
public class AnnonTest {
    @ZiDin
    public void sssss(){
        System.out.println("AnnonTest目标类执行。。。。");
    }
}
