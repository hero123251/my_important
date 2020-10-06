package com.xuecheng;

public class BB  extends AA{

    //创建对象时，该类默认提供一个无参构造，但是如果提供了有参构造，则默认无参构造就没有了，需要手动提供一个
    public BB(){
        System.out.println("BB无参构造");
    }

    public BB(Integer bb){
//        super(2);
        System.out.println("有参构造方法BB...");
    }


    public void test(){
        System.out.println("BB");
    }

    public void move() {
        System.out.println("BBmove....");
    }
}
