package com.xuecheng.test.freemarker.model;

import lombok.Data;

import java.sql.Date;
import java.util.List;
@Data
public class Student {
   private String name;  //姓名     
    private int age;  //年龄
    private Date birthday;//生日
    private Float money;//钱包
    private List<Student> friends;//朋友列表
    private Student bestFriend;//好的朋友
}
