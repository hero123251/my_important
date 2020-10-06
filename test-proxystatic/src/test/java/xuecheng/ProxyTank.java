package xuecheng;

public class ProxyTank implements Moveable {
    Moveable moveable;

    public ProxyTank(Moveable moveable) {
        this.moveable = moveable;
    }


    public void move(){
        moveable.move();
    }
}
