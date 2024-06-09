import java.awt.*;
import java.util.HashSet;

public abstract class Character {
    protected final int CHARACTER_WIDTH = 200;
    protected final int CHARACTER_HEIGHT = 250;

    protected final int UPPER_BOUNDARY_Y = 165;
    protected final int LOWER_BOUNDARY_Y = 790;

    protected int x;
    protected int y;
    protected int dx;
    protected int dy;

    protected Rectangle hitBox;
    protected HashSet<Rectangle> obstacles;

    protected Image defaultFrameRight;
    protected Image defaultFrameLeft;

    protected int paintType;

    protected boolean isAlive;
    protected boolean isCharacterStanding;
    protected boolean isCharacterMovingRight;
    protected boolean isCharacterMovingLeft;

    public abstract void paint(Graphics g);
    public abstract void update();
    public abstract boolean canMove();
    public abstract void move();

    protected void setIsAlive(boolean isAlive){
        this.isAlive = isAlive;
    }
    protected boolean isAlive(){
        return this.isAlive;
    }

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
