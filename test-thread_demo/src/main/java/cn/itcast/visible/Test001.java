package cn.itcast.visible;

public class Test001 {
    public static void main(String[] args) {
        String s1 = new String("abc");
        String s2 = new String("def");
        String s3 = s1.intern() + s2.intern();
        String s4 = "abcdef";
        System.out.println(s3 == s4);

    }
}