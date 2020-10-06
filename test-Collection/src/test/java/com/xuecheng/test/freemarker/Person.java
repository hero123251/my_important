package com.xuecheng.test.freemarker;

public class Person implements Comparable<Person>{
    // 属性
    private String name;
    private int age;

    // 构造方法
    public Person() {
        super();

    }
    public Person(String name, int age) {
        super();
        this.name = name;
        this.age = age;
    }

    // 要让哈希表存储不重复的元素，就必须重写hasCode和equals方法
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + age;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Person other = (Person) obj;
        if (age != other.age)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "Person [name=" + name + ", age=" + age + "]";
    }



    public int compareTo(Person o) {
        int result = this.age - o.age;
        if (result == 0){
            return this.name.compareTo(o.name);
        }
        return result;
    }


}

