package cn.itcast.threadlocal;

public class ThreadLocalDemo {

    //TheadLocal -------------> set , get  , remove
    public static void main(String[] args) {

        ThreadLocal<String> local = new ThreadLocal<String>();



        local.set("Hello Java");
        String s = local.get();
        System.out.println("1- " + Thread.currentThread().getName()+": "+s);


        new Thread(new Runnable() {
            @Override
            public void run() {

                local.set("Hello Solr");

                String s1 = local.get();
                System.out.println(Thread.currentThread().getName()+": "+s1);
            }
        }).start();


        String s2 = local.get();
        System.out.println("2- " + Thread.currentThread().getName()+": "+s2);

        local.remove();
        s2 = local.get();
        System.out.println("3- " + Thread.currentThread().getName()+": "+s2);

    }

}
