package xuecheng.jdkdemo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class ProxyObject {
    Object target;

    public ProxyObject(Object target){
        this.target=target;
    }


    public Object getNewObject(){
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("JDK代理开始。。。");
                Object invoke = method.invoke(target, args);
                System.out.println("JDK代理结束。。。");

                return invoke;
            }
        });
    }


}