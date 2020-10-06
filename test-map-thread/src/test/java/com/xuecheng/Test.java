package com.xuecheng;

import sun.awt.SunHints;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Test {


    @org.junit.Test
    public void testArray(){
        int[] arr  =new int[10];

    }



    @org.junit.Test
    public void testconcurrentHashMap(){
        ConcurrentHashMap h = new ConcurrentHashMap();

//        Hashtable h  = new Hashtable();

        h.put(12,"aaaa");
        h.put(12,"bbbb");

        System.out.println(h);

    }










    public static void main(String[] args) {
//        Map map = new HashMap(1000000);
//        Map map = new Hashtable();

        ConcurrentHashMap map = new ConcurrentHashMap();




        for (int i = 0; i < 100; i++) {
            new Runner(map).start();
        }


    }




}

class Runner extends Thread {

    Map map;

    public Runner(Map map) {
        this.map = map;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            map.put(this.getName() + i, i);
        }

        System.out.println(this.getName() + ":" + map.size());


    }
}