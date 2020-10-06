package cn.itcast.sync;

/**
 * 同步方法
 */
public class SynchronizedMethod implements Runnable{
    private static int x = 100; // 票的数量

    @Override
    public void run() {
        while(true){
            syncMethod();
        }
    }

    /**
     * 方式2: 同步方法: 锁对象:this
     */
    private synchronized void syncMethod() {  //锁对象是 this
        if (x > 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "正在出售第 " + (x--) + " 张票");
        }
    }

}
