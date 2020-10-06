package cn.itcast.security;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程下数据安全访问的几种方式(第3,4个线程安全, 第1,2个线程不安全)
 * 以下方式中, 只有 synchronized 能保证数据访问安全
 */
public class DataSecurity implements Runnable{

    private static int num1 = 100;
    public int getNum1(){
        return num1--;
    }


    private static int num2 = 100;
    public synchronized int getNum2(){
        num2--;
        return num2;
    }




    private AtomicInteger num3 = new AtomicInteger(100);
    public int getNum3(){
        return num3.getAndDecrement();
    }



    @Override
    public void run() {
        int number = Integer.MAX_VALUE;

        while(number > 0){
            try {
                Thread.sleep(5);
                number = getNum3(); // 原始方式
                System.out.println(Thread.currentThread().getName() + ": " + number);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}