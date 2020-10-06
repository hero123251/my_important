package xuecheng.cglibdemo;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class ProxyFactory implements MethodInterceptor {

    TargetDemo targetDemo;

    public ProxyFactory(TargetDemo targetDemo){
        this.targetDemo=targetDemo;
    }


    //返回一个代理对象：是target 对象的代理对象
    public Object getProxyInstance() {
        //1.创建一个工具类
        Enhancer enhancer = new Enhancer();
        //2.设置父类
        enhancer.setSuperclass(targetDemo.getClass());
        //3.设置回调函数
        enhancer.setCallback(this);
        //4.创建子类对象，即代理对象
        return enhancer.create();
    }



    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        System.out.println("Cglib对象开始。。。");
        Object invoke = method.invoke(targetDemo, objects);
        System.out.println("Cglib对象结束。。。");

        return invoke;
    }
}