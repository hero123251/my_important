package cn.itcast.lock;

import java.util.LinkedHashSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock是Java5之后加入的
 */
public class LockBlock implements Runnable{

    private int x = 100; // 票的数量
    private final Lock lock = new ReentrantLock();


    @Override
    public void run() {
        while (true) {
            lockBlock();
        }
    }


    public void lockBlock() {


        lock.lock();//加锁
//        lock.tryLock();
        try{
            if (x > 0) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "正在出售第 " + x + " 张票");
                x--;
            }
        }finally {
            lock.unlock();//释放锁
        }
    }
}