package cn.itcast.pool;


import java.util.concurrent.*;

public class ThreadPoolDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {



        //创建线程池的第一种方式（最常用的）
        //创建一个固定大小的线程池
       /* ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            final int j = i;//匿名内部类中访问外部变量时，外部变量必须是常量。
            //执行任务，在线程池中
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+": " + j);
                }
            });
        }*/

        //创建线程池的第二种方式
        //创建一个缓存的线程池（按需创建：如果有线程，拿来执行任务，没有就创建；
        //      这种根据任务来创建线程数，如果太多任务会造成系统压力，导致不可控。所以不采用）
        /*ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10000; i++) {
            final int j = i;
            //执行任务，在线程池中
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+": " + j);
                }
            });
        }*/


        //创建线程池的第三种方式（执行效率低）
        //线程池的单线程，用完一个会归还线程池。而单线程是用完就释放了。
     /*   ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10000; i++) {
            final int j = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+": " + j);
                }
            });
        }
        */






        //创建线程池的第四种方式（调度任务的线程池）
        /*
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);

        //延迟执行, 只执行一次
        executorService.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+": it is time to send mail to leaders");
            }
        // 5 --->delay        TimeUnit.SECONDS  --->  时间单位
        },5, TimeUnit.SECONDS);


        //延迟执行, 周期（间隔）执行
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+": it is time to send mail to leaders");
            }
        },3,2,TimeUnit.SECONDS);
        */





        //submit();执行任务的第二种方法。
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
//                TimeUnit.MILLISECONDS.sleep(5);//睡5秒
                System.out.println(Thread.currentThread().getName() + ": it is time to send mail to leaders");
                return "Hello";
            }
        });

        System.out.println(Thread.currentThread().getName()+"============");
        String s = future.get();//阻塞（【等待】子线程执行完）
        System.out.println(s);


        //停止线程池中的所有线程，但是队列中的任务不关闭。
        executorService.shutdown();
        //停止所有线程正在执行的任务，并且包含队列中的等待中的任务。
        executorService.shutdownNow();



    }

}
