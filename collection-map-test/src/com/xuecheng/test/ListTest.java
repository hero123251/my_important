package com.xuecheng.test;


import java.util.ArrayList;

public class ListTest {
    public static void main(String[] args) {


        int [] arr = new int[]{1,2,3};
        int [] arr02 = new int[3];
        for (int i : arr02) {
            System.out.println(i);
        }

        System.out.println("-----------");
        System.arraycopy(arr,0,arr02,0,3);
        for (int i : arr02) {
            System.out.println(i);
        }
        System.out.println(arr02);


    }


}
