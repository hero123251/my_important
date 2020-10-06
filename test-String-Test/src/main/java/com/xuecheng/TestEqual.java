package com.xuecheng;

public class TestEqual {

    public static void main(String[] args) {
        final String s = "abc";
        final String s2 = new String("abc");
        String s3 = s2 + "d";
        String s4 = "abcd";
        String s5 = s + "d";


        System.out.println(s3 == s4);//false
        System.out.println(s5 == s4);//true


    }
}
