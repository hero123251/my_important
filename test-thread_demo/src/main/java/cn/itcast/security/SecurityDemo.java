package cn.itcast.security;

public class SecurityDemo {
    public static void main(String[] args) {
		
        // 数据安全访问的几种方式
        DataSecurity data = new DataSecurity();

        Thread thread5_1 = new Thread(data, "thread 1");
        Thread thread5_2 = new Thread(data, "thread 2");
        Thread thread5_3 = new Thread(data, "thread 2");
        Thread thread5_4 = new Thread(data, "thread 2");
        Thread thread5_5 = new Thread(data, "thread 2");

        thread5_1.start();
        thread5_2.start();
        thread5_3.start();
        thread5_4.start();
        thread5_5.start();
    }
}