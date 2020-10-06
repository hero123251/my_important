package cn.itcast.sync;


/**
 * 同步锁的几种实现放方式
 * 区别: 
 *      Synchronized: 采用CPU悲观锁机制(JVM执行), 线程是独占的, 当很多线程进程锁时会引起CPU频繁切换而影响性能
 *      Lock: java写的乐观锁, 每次不加锁假设没有冲突去执行, 如果发生冲突则重试
 * @author 
 */
public class SyncDemo {

    public static void main(String[] args) {
        // 方式1: 同步代码块
        syncTest1();

        // 方式2: 同步方法
//        syncTest2();

        // 方式3: 静态同步方法
       // syncTest3();

    }

    private static void syncTest3() {
        SynchronizedStaticMethod staticMethod = new SynchronizedStaticMethod();

        Thread thread3_1 = new Thread(staticMethod, "1号窗口");
        Thread thread3_2 = new Thread(staticMethod, "2号窗口");

        thread3_1.start();
        thread3_2.start();
    }

    private static void syncTest2() {
        SynchronizedMethod method = new SynchronizedMethod();

        Thread thread2_1 = new Thread(method, "1号窗口");
        Thread thread2_2 = new Thread(method, "2号窗口");

        thread2_1.start();
        thread2_2.start();
    }


    private static void syncTest1() {
        SynchronizedBlock block = new SynchronizedBlock();

        Thread thread1_1 = new Thread(block, "1号窗口 ");
        Thread thread1_2 = new Thread(block, "2号窗口 ");
        Thread thread1_3 = new Thread(block, "3号窗口 ");
        Thread thread1_4 = new Thread(block, "4号窗口 ");

        thread1_1.start();
        thread1_2.start();
        thread1_3.start();
        thread1_4.start();
    }

}