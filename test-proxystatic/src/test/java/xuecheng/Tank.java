package xuecheng;

import java.util.Random;

public class Tank implements Moveable {
    public void move() {
        System.out.println("Tank move。。。");
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
