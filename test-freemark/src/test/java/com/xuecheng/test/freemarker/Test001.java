package com.xuecheng.test.freemarker;

import org.junit.Test;

import java.util.*;

public class Test001 {
    @Test
    public void ss() {
        Address address = new Address();
        address.setAdd("深圳市");
        Student s1 = new Student();
        s1.setAge(12);
        s1.setName("张三");
        s1.setAddress(address);
        Student s2 = (Student) s1.clone();


        address.setAdd("广东省");
        s2.setName("李四");
        System.out.println(s1.toString() + s1.getAddress().getAdd());
        System.out.println(s2.toString() + s2.getAddress().getAdd());
    }


    @Test
    public void testArrayList() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("a");
        arrayList.add("b");


        //判断有几个元素
        int size = arrayList.size();
        //判断该ArrayList是否为空
        boolean empty = arrayList.isEmpty();
        //移除指定元素
        //boolean b = arrayList.remove("b");


        ArrayList arrayList2 = new ArrayList();
        arrayList2.add("c");
        //像另外一个ArrayList添加一个ArrayList
        arrayList.add(arrayList2);

        System.out.println(arrayList.toString());
    }

    @Test
    public void testHashSet() {
        Set s = new HashSet();
        s.add(2);
        s.add(3);
        s.add(1);


        for (Object o : s) {
            System.out.println((Integer) o);
        }
    }

    @Test
    public void treeTreeSet1() {
        TreeSet<People> s =new TreeSet<People>();
        s.add(new People("zs",12));
        s.add(new People("ls",23));
        s.add(new People("zs",12));
        s.add(new People("abc",32));

        for (People p : s) {
            System.out.println(p);
        }



    }



    @Test
    public void testTreeSet(){
        // 利用TreeSet来存储自定义类Person对象
        TreeSet<Person> treeSet = new TreeSet<Person>();
        // Person类实现了Comparable接口，并且重写comparaTo方法
        // 比较规则是先按照 年龄排序，年龄相等的情况按照年龄排序
        treeSet.add(new Person("张山1", 20));
        treeSet.add(new Person("张山2", 16));
        treeSet.add(new Person("张山3", 13));
        treeSet.add(new Person("张山4", 17));
        treeSet.add(new Person("张山5", 20));

        for (Person p : treeSet){
            System.out.println(p);
        }
    }

    @Test
    public void testTreeSet02(){
        // 利用TreeSet来存储自定义类Person对象
        TreeSet<Person> treeSet = new TreeSet<Person>();
        // Person类实现了Comparable接口，并且重写comparaTo方法
        // 比较规则是先按照 年龄排序，年龄相等的情况按照年龄排序
        treeSet.add(new Person("张山1", 20));
        treeSet.add(new Person("张山2", 16));
        treeSet.add(new Person("张山3", 13));
        treeSet.add(new Person("张山4", 17));
        treeSet.add(new Person("张山5", 20));

        for (Person p : treeSet){
            System.out.println(p);
        }
    }

    @Test
    public void testHashMap(){
        HashMap<People,String> hashMap = new HashMap<>();
        hashMap.put(new People("li",23),"lisi");
        hashMap.put(new People("zh",20),"zhangsan");
        hashMap.put(new People("liu",25),"liuliu");

        Set<Map.Entry<People, String>> entries = hashMap.entrySet();
        Iterator<Map.Entry<People, String>> iterator = entries.iterator();
        while (iterator.hasNext()){
            Map.Entry<People, String> entry = iterator.next();
            People key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key+"---"+value);

        }
    }





}