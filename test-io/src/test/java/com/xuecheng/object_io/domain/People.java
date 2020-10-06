package com.xuecheng.object_io.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class People implements Serializable {
    private String username;
    private Integer age;
    //没有必要序列化的属性，使用关键字transient
    private transient Integer password;
}
