package com;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = "1992-10-09";
        Date  parse = simpleDateFormat.parse(date);


        System.out.println("后续代码执行了");
    }
}
