package cn.itcast.lock;

public class Lockemo {
    public static void main(String[] args) {

        // Lock锁
        LockBlock lockBlock = new LockBlock();

        new Thread(lockBlock, "1号窗口").start();
        new Thread(lockBlock, "2号窗口").start();

    }
}