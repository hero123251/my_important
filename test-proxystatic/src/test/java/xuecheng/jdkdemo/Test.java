package xuecheng.jdkdemo;



public class Test {
    public static void main(String[] args) {
        Person p = new Student();
        ProxyObject proxyObject = new ProxyObject(p);
        Person person = (Person) proxyObject.getNewObject();
        person.sleep();
    }
}