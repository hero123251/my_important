package com.xuecheng.test.freemarker;

import java.util.Objects;

public class People {

    private String usrname;
    private Integer age;

    public People() {
    }

    public People(String usrname, Integer age) {
        this.usrname = usrname;
        this.age = age;
    }

    public String getUsrname() {
        return usrname;
    }

    public void setUsrname(String usrname) {
        this.usrname = usrname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        People people = (People) o;
        return Objects.equals(usrname, people.usrname) &&
                Objects.equals(age, people.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usrname, age);
    }

    @Override
    public String toString() {
        return "People{" +
                "usrname='" + usrname + '\'' +
                ", age=" + age +
                '}';
    }



}
