package xuecheng;

public class TestLogTank {




    public static void main(String[] args){
        Moveable tank = new Tank();

        ProxyTank proxyTank = new ProxyTank(tank);
        long start = System.currentTimeMillis();
        System.out.println("日志记录：Tank的move方法开始。。。");
        proxyTank.move();
        System.out.println("日志记录：Tank的move方法结束。。。");
        long end = System.currentTimeMillis();
        System.out.println("move方法执行时间："+(end - start));
    }




}