package xuecheng.staticdemo;

public class Tigger extends Cat {


    @Override
    public void eat() {
        System.out.println("日志记录开始。。。");
        super.eat();
        System.out.println("日志记录结束。。。");
    }
}
