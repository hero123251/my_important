package cn.itcast.sync;

/**
 * 同步代码块
 */
public class SynchronizedBlock implements Runnable{

    private static int x = 100; // 票的数量

    private Object obj = new Object();

    @Override
    public void run() {
        while (true) {
            syncBlock();
        }
    }

    /**
     * 方式1: 同步代码块, 锁对象:任意对象
     */
    public void syncBlock() {
        synchronized (obj) { // 锁对象是new Object();
            if (x > 0) {
//                try {
//                    Thread.sleep(50);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

                System.out.println(Thread.currentThread().getName() + "正在出售第 " + x + " 张票");
                x--;
            }
        }
    }





}