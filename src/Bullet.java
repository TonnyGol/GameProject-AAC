import java.awt.*;

public class Bullet extends Rectangle {
    private final int BULLET_SPEED = 100;
    private final int BULLET_WIDTH = 20;
    private final int BULLET_HEIGHT = 3;
    private int direction;

    public Bullet(int x, int y, int direction) {
        super(x, y, 0, 0);
        this.setBounds((int) this.getX(), (int) this.getY(), BULLET_WIDTH, BULLET_HEIGHT);
        this.direction = direction;
    }
    public void fly(){
        this.x += BULLET_SPEED * direction;
    }
}
