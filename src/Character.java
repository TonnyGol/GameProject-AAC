import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public abstract class Character {
    private final int CHARACTER_WIDTH = 200;
    private final int CHARACTER_HEIGHT = 250;

    private final int UPPER_BOUNDARY_Y = 165;
    private final int LOWER_BOUNDARY_Y = 790;

    private int x;
    private int y;
    private int dx;
    private int dy;

    private Rectangle hitBox;
    private HashSet<Rectangle> obstacles;

    private int runFrameIndex;
    private int attackFrameIndex;

    private Image defaultFrameRight;
    private Image defaultFrameLeft;

    private List<Image> runRightFrames;
    private List<Image> runLeftFrames;
    private List<Image> attackRightFrames;
    private List<Image> attackLeftFrames;

    private int paintType;

    private boolean isAlive;
    private boolean isCharacterMovingRight;
    private boolean isCharacterMovingLeft;
    private boolean isCharacterAttacking;

    public abstract void paint(Graphics g);
    public abstract void update();
    public abstract boolean canMove();
    public abstract void move();

    public Character(int startX, int startY, HashSet<Rectangle> obstacles) {
        this.x = startX;
        this.y = startY;
        this.dx = 0;
        this.dy = 0;
        this.paintType = 0;

        this.hitBox = null;
        this.obstacles = obstacles;

        this.runFrameIndex = 0;
        this.attackFrameIndex = 0;

        this.defaultFrameLeft = null;
        this.defaultFrameRight = null;

        this.runRightFrames = null;
        this.runLeftFrames = null;
        this.attackRightFrames = null;
        this.attackLeftFrames = null;

        this.isAlive = true;
        this.isCharacterMovingRight = false;
        this.isCharacterMovingLeft = false;
    }

    public List<Image> loadFrames(int frames, String fileName){
        List<Image> frameList = new ArrayList<>(frames);
        for (int i = 1; i <= frames; i++){
            Image frame = new ImageIcon(fileName+i+".png").getImage();
            frameList.add(frame);
        }
        return frameList;
    }

    public int getCHARACTER_WIDTH() {
        return CHARACTER_WIDTH;
    }

    public int getCHARACTER_HEIGHT() {
        return CHARACTER_HEIGHT;
    }

    public int getUPPER_BOUNDARY_Y() {
        return UPPER_BOUNDARY_Y;
    }

    public int getLOWER_BOUNDARY_Y() {
        return LOWER_BOUNDARY_Y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }

    public HashSet<Rectangle> getObstacles() {
        return obstacles;
    }

    public int getRunFrameIndex() {
        return runFrameIndex;
    }

    public void setRunFrameIndex(int runFrameIndex) {
        this.runFrameIndex = runFrameIndex;
    }

    public int getAttackFrameIndex() {
        return attackFrameIndex;
    }

    public void setAttackFrameIndex(int attackFrameIndex) {
        this.attackFrameIndex = attackFrameIndex;
    }

    public Image getDefaultFrameRight() {
        return defaultFrameRight;
    }

    public void setDefaultFrameRight(Image defaultFrameRight) {
        this.defaultFrameRight = defaultFrameRight;
    }

    public Image getDefaultFrameLeft() {
        return defaultFrameLeft;
    }

    public void setDefaultFrameLeft(Image defaultFrameLeft) {
        this.defaultFrameLeft = defaultFrameLeft;
    }

    public List<Image> getRunRightFrames() {
        return runRightFrames;
    }

    public void setRunRightFrames(List<Image> runRightFrames) {
        this.runRightFrames = runRightFrames;
    }

    public List<Image> getRunLeftFrames() {
        return runLeftFrames;
    }

    public void setRunLeftFrames(List<Image> runLeftFrames) {
        this.runLeftFrames = runLeftFrames;
    }

    public List<Image> getAttackRightFrames() {
        return attackRightFrames;
    }

    public void setAttackRightFrames(List<Image> attackRightFrames) {
        this.attackRightFrames = attackRightFrames;
    }

    public List<Image> getAttackLeftFrames() {
        return attackLeftFrames;
    }

    public void setAttackLeftFrames(List<Image> attackLeftFrames) {
        this.attackLeftFrames = attackLeftFrames;
    }

    public int getPaintType() {
        return paintType;
    }

    public void setPaintType(int paintType) {
        this.paintType = paintType;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isCharacterMovingRight() {
        return isCharacterMovingRight;
    }

    public void setCharacterMovingRight(boolean characterMovingRight) {
        isCharacterMovingRight = characterMovingRight;
    }

    public boolean isCharacterMovingLeft() {
        return isCharacterMovingLeft;
    }

    public void setCharacterMovingLeft(boolean characterMovingLeft) {
        isCharacterMovingLeft = characterMovingLeft;
    }

    public boolean isCharacterAttacking() {
        return isCharacterAttacking;
    }

    public void setCharacterAttacking(boolean characterAttacking) {
        isCharacterAttacking = characterAttacking;
    }
}
