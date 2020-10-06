package xuecheng.cglibdemo;

public class Test {
    public static void main(String[] args) {
        TargetDemo targetDemo = new TargetDemo();
        ProxyFactory proxyFactory = new ProxyFactory(targetDemo);
        TargetDemo target = (TargetDemo) proxyFactory.getProxyInstance();
        target.behaver();
    }
}
