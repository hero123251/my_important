package cn.itcast.thread;

public class MyThreadClass extends Thread{

    public MyThreadClass(){};

    public MyThreadClass(String name){
        super(name);
    };

    @Override
    public void run(){ //继承Thread类并重写run()方法
        for(int x = 0; x < 100; x++){
            System.out.println(getName() + ": " + x);
        }
    }
}