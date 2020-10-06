package cn.itcast.thread;

public class ThreadDemo {

    public static void main(String[] args) {
        // 方式一: 继承Thread类
        run1();

        // 方式三: 实现Runnable接口
        run3();

        // 方式四: 实现Runnable接口2
        run4();
    }


    /**
     * 方式一: 继承Thread类
     */
    private static void run1() {
        MyThreadClass thread1_1 = new MyThreadClass();
        MyThreadClass thread1_2 = new MyThreadClass("thread 1_2");

        thread1_1.setName("thread 1_1");

        thread1_1.start();
        thread1_2.start();      
    }

    /**
     * 方式二: 实现Runnable接口
     */
    private static void run3() {
        Thread thread2_1 = new Thread(new MyRunnable());
        thread2_1.setName("thread 2_1");
        thread2_1.start();

        Thread thread2_2 = new Thread(new MyRunnable(), "thread 2_2");
        thread2_2.start();      
    }

    /**
     * 方式三: 实现Runnable接口2
     */
    private static void run4() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int x = 0; x < 100; x++) {
                    System.out.println(Thread.currentThread().getName() + ": " + x);
                }
            }
        }, "thread 3").start();


    }
}