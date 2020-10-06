package cn.itcast.visible;

import java.util.concurrent.atomic.AtomicInteger;

//volatile[ˈvɑ:lətl] 原解释: 保证修改的值会立即被更新到主内存,当其他线程读取时,会去主内存中读取最新值
public class VisibleDemo extends Thread {

//    private static boolean flag = false;

    private volatile static boolean flag = false;

    public void run() {
//        while (!flag){
//        }
        if (flag == true) {
            int[] arr = new int[]{1, 2, 3};
        }

    }

    public static void main(String[] args) throws Exception {
        new VisibleDemo().start();


//        Thread.sleep(2000);
        flag = true;
//        System.out.println(flag);
//        flag = true;
    }


}