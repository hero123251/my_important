package cn.itcast.sync;

/**
 * 静态同步方法
 *
 */
public class SynchronizedStaticMethod implements Runnable{
    private static int x = 100; // 票的数量

    @Override
    public void run() {
        while(true){
            syncStaticMethod();
        }
    }

    /**
     * 方式3: 静态同步方法: 锁对象: 类.class 字节码文件对象
     */
    private static synchronized void syncStaticMethod(){ //锁对象是 Ticket.class
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