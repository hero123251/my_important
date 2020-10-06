package xuecheng;

import org.junit.Test;

public class Tank2 extends Tank {
    @Override
    public void move()  {
        long start = System.currentTimeMillis();
        super.move();
        long end = System.currentTimeMillis();
        System.out.println("记录时间"+(end-start));
    }

    @Test
    public void testMove() {
        this.move();
    }
}
