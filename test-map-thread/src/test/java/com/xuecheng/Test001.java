package com.xuecheng;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Test001 {
    public static void main(String[] args) {
//        HashMap h = new HashMap();

        ConcurrentHashMap h = new ConcurrentHashMap();

        for (int i = 0; i < 100; i++) {
            new MTest(h).start();
        }
    }

}


class MTest extends Thread{

    Map map;

    public MTest(Map map){
        this.map=map;
    }

    @Override
    public void run() {

        for (int i = 0; i < 100; i++) {
            map.put(this.getName()+i,i);
        }

        System.out.println(this.getName()+":"+map.size());
    }
}
