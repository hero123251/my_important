package cn.itcast.security;

import java.util.concurrent.atomic.AtomicInteger;

public class Test001 {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.set(12);

        System.out.println(atomicInteger.get());
    }

}
