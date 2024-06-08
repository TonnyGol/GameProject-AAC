import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Enemy extends Character {
    private final int CHARACTER_WIDTH = 200;
    private final int CHARACTER_HEIGHT = 250;
    private final int CHARACTER_SPEED = 5;

    private final String DEFAULT_FRAME_FILE_PATH = "resources\\Images\\WolfWalk1";
    private final String RUN_IMAGES_PATH = "resources\\Images\\WolfWalk";
    private final String RUN_BACK_IMAGES_PATH = "resources\\Images\\WolfWalkBack";
    private final String ATTACK_IMAGES_PATH = "resources\\Images\\Attack";

    private int paintType;
    private boolean isCharacterMovingRight;
    private final int MOVE_RIGHT_CODE = 1;
    private boolean isCharacterMovingLeft;
    private final int MOVE_LEFT_CODE = 2;
    private boolean isCharacterAttacking;
    private final int ATTACK_RIGHT_CODE = 5;
    private final int ATTACK_LEFT_CODE = 6;
    private int x;
    private int y;
    private int dx;
    private int dy;
    private int runFrameIndex;
    private int attackFrameIndex;
    private Player player;


    private Image defaultFrame;
    private Image defaultFrameBack;
    private List<Image> runFrames;
    private List<Image> runBackFrames;
    private List<Image> attackFrames;
    private List<Image> attackBackFrames;

    public Enemy(int startX, int startY, Player player) {
        this.isCharacterMovingRight = false;
        this.isCharacterMovingLeft = false;
        this.isCharacterAttacking = false;
        this.x = startX;
        this.y = startY;
        this.dx = 0;
        this.dy = 0;
        this.runFrameIndex = 0;
        this.defaultFrame = new ImageIcon(DEFAULT_FRAME_FILE_PATH).getImage();
        this.defaultFrameBack = new ImageIcon(DEFAULT_FRAME_FILE_PATH).getImage();
        this.runFrames = loadFrames(11, RUN_IMAGES_PATH);
        this.runBackFrames = loadFrames(11, RUN_BACK_IMAGES_PATH);
        this.attackFrames = loadFrames(4, ATTACK_IMAGES_PATH);
        //this.attackBackFrames = loadFrames(4, ATTACK_IMAGES_PATH);
        this.player = player;
    }

    private List<Image> loadFrames(int frames, String fileName) {
        List<Image> frameList = new ArrayList<>(frames);
        for (int i = 1; i <= frames; i++) {
            Image frame = new ImageIcon(fileName + i + ".png").getImage();
            frameList.add(frame);
        }
        return frameList;
    }


    @Override
    public void paint(Graphics g) {
        switch (this.paintType) {
            case 1:
                g.drawImage(this.runFrames.get(this.runFrameIndex),
                        this.x, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT, null);
                break;
            case 2:
                g.drawImage(this.runBackFrames.get(this.runFrameIndex),
                        this.x, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT, null);
                break;
            case 3:
                g.drawImage(this.attackFrames.get(this.attackFrameIndex),
                        this.x + 30, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT, null);
                break;
            case 4:
                g.drawImage(this.attackBackFrames.get(this.attackFrameIndex),
                        this.x - 30, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT, null);
                break;
            case 0:
                g.drawImage(this.defaultFrame,
                        this.x, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT, null);
                break;
            case -1:
                g.drawImage(this.defaultFrameBack,
                        this.x, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT, null);
                break;
        }
    }

    public void update(){
        if (this.x > this.player.getX()){
            this.isCharacterMovingLeft = true;
            this.isCharacterMovingRight = false;
            paintType = MOVE_LEFT_CODE;
        } else {
            this.isCharacterMovingLeft = false;
            this.isCharacterMovingRight = true;
            paintType = MOVE_RIGHT_CODE;
        }
        moveTowardsPlayer();
    }

    protected void loopBetweenFrames() {
            this.setRunFrameIndex(this.getRunFrameIndex() + 1);
            if (this.getRunFrameIndex() % 11 == 0) {
                this.setRunFrameIndex(0);
            }
    }

    public void moveTowardsPlayer() {
        int distanceX = this.x - this.player.getX();
        int distanceY = this.y - this.player.getY();
        if (distanceX > 0) {
            this.x -= CHARACTER_SPEED;
        } else {
            this.x += CHARACTER_SPEED;
        }
        if (distanceY > 0){
            this.y -= CHARACTER_SPEED;
        } else {
            this.y += CHARACTER_SPEED;
        }
    }


    public void move(){
        this.x += this.dx;
        this.y += this.dy;
    }

    public int getDx() {
        return dx;
    }

    public int getCHARACTER_SPEED() {
        return CHARACTER_SPEED;
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

    public void setShootFrameIndex(int shootFrameIndex) {
        this.attackFrameIndex = shootFrameIndex;
    }

    public int getShootFrameIndex() {
        return attackFrameIndex;
    }

    public int getRunFrameIndex() {
        return runFrameIndex;
    }

    public void setRunFrameIndex(int runFrameIndex) {
        this.runFrameIndex = runFrameIndex;
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

    @Override
    public boolean canMove() {
        return false;
    }
}

