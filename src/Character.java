import java.awt.*;
import java.util.HashSet;

public abstract class Character {
    private final int CHARACTER_WIDTH = 250;
    private final int CHARACTER_HEIGHT = 150;
    private final int CHARACTER_SPEED = 15;

    private final int UPPER_BOUNDARY_Y = 165;
    private final int LOWER_BOUNDARY_Y = 790;

    private int x;
    private int y;
    private int dx;
    private int dy;

    private Rectangle hitBox;
    private HashSet<Rectangle> obstacles;

    private Image defaultFrameRight;
    private Image defaultFrameLeft;

    private int paintType;

    private boolean isCharacterStanding;
    private boolean isCharacterMovingRight;
    private boolean isCharacterMovingLeft;

    public abstract void paint(Graphics g);
    public abstract void update();
    public abstract boolean canMove();
    public abstract void move();
//    protected abstract void loopBetweenFrames();
//
//    public abstract int getCHARACTER_WIDTH();
//
//    public abstract int getCHARACTER_HEIGHT();
//
//    public abstract int getCHARACTER_SPEED();
//
//    public abstract int getUPPER_BOUNDARY_Y();
//
//    public abstract int getLOWER_BOUNDARY_Y();
//
//    public abstract int getX();
//
//    public abstract void setX(int x);
//
//    public abstract int getY();
//
//    public abstract void setY(int y);
//
//    public abstract int getDx();
//
//    public abstract void setDx(int dx);
//
//    public abstract int getDy();
//
//    public abstract void setDy(int dy);
//
//    public abstract Rectangle getHitBox();
//
//    public abstract void setHitBox(Rectangle hitBox);
//
//    public abstract HashSet<Rectangle> getObstacles();
//
//    public abstract void setObstacles(HashSet<Rectangle> obstacles);
//
//    public abstract Image getDefaultFrameRight();
//
//    public abstract void setDefaultFrameRight(Image defaultFrameRight);
//
//    public abstract Image getDefaultFrameLeft();
//
//    public abstract void setDefaultFrameLeft(Image defaultFrameLeft);
//
//    public abstract int getPaintType();
//
//    public abstract void setPaintType(int paintType);
//
//    public abstract boolean isCharacterStanding();
//
//    public abstract void setCharacterStanding(boolean characterStanding);
//
//    public abstract boolean isCharacterMovingRight();
//
//    public abstract void setCharacterMovingRight(boolean characterMovingRight);
//
//    public abstract boolean isCharacterMovingLeft();
//
//    public abstract void setCharacterMovingLeft(boolean characterMovingLeft);
}
