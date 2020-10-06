package com.xuecheng.ioc.factory;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;


public class TestFactory implements FactoryBean<Stuent> {

    public Stuent getObject() throws Exception {
        Stuent s = new Stuent();
        s.setPassword("123");
        s.setUsername("abc");
        return s;
    }

    public Class<?> getObjectType() {
        return Stuent.class;
    }
}
