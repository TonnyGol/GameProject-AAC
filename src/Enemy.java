import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Enemy extends Character {
    private final int CHARACTER_SPEED = 5;

    private final String DEFAULT_FRAME_FILE_PATH = "resources\\Images\\WolfWalk1";
    private final String RUN_IMAGES_PATH = "resources\\Images\\WolfWalk";
    private final String RUN_BACK_IMAGES_PATH = "resources\\Images\\WolfWalkBack";
    private final String ATTACK_IMAGES_PATH = "resources\\Images\\WolfAttack";
    private final String ATTACK_BACK_PATH = "resources\\Images\\WolfAttackBack";

    private final int MOVE_RIGHT_CODE = 1;
    private final int MOVE_LEFT_CODE = 2;
    private boolean isCharacterAttacking;
    private final int ATTACK_RIGHT_CODE = 3;
    private final int ATTACK_LEFT_CODE = 4;
    private int runFrameIndex;
    private int attackFrameIndex;
    private Player player;

    private List<Image> runRightFrames;
    private List<Image> runLeftFrames;
    private List<Image> attackFrames;
    private List<Image> attackBackFrames;

    public Enemy(int startX, int startY, Player player, HashSet<Rectangle> obstacles) {
        this.isCharacterMovingRight = false;
        this.isCharacterMovingLeft = false;
        this.isCharacterAttacking = false;
        this.x = startX;
        this.y = startY;
        this.dx = 0;
        this.dy = 0;
        this.runFrameIndex = 0;
        this.attackFrameIndex = 0;
        this.obstacles = obstacles;
        this.hitBox = new Rectangle(this.x + CHARACTER_WIDTH / 4 - 10,
                this.y + 100, CHARACTER_WIDTH / 2 + 15, (CHARACTER_HEIGHT + 50)/2);
        this.defaultFrameRight = new ImageIcon(DEFAULT_FRAME_FILE_PATH).getImage();
        this.defaultFrameLeft = new ImageIcon(DEFAULT_FRAME_FILE_PATH).getImage();
        this.runRightFrames = loadFrames(11, RUN_IMAGES_PATH);
        this.runLeftFrames = loadFrames(11, RUN_BACK_IMAGES_PATH);
        //this.attackFrames = loadFrames(4, ATTACK_IMAGES_PATH);
        //this.attackBackFrames = loadFrames(4, ATTACK_BACK_PATH);
        this.player = player;
    }

    private List<Image> loadFrames(int frames, String fileName) {
        List<Image> frameList = new ArrayList<>(frames);
        for (int i = 1; i <= frames; i++) {
            Image frame = new ImageIcon(fileName+i+".png").getImage();
            frameList.add(frame);
        }
        return frameList;
    }


    @Override
    public void paint(Graphics g) {
        g.fillRect(this.x + CHARACTER_WIDTH / 4 - 10,
                this.y + 100, CHARACTER_WIDTH / 2 + 15, (CHARACTER_HEIGHT + 50)/2);
        switch (this.paintType) {
            case 1:
                g.drawImage(this.runRightFrames.get(this.runFrameIndex),
                        this.x, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT, null);
                break;
            case 2:
                g.drawImage(this.runLeftFrames.get(this.runFrameIndex),
                        this.x, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT, null);
                break;
            case 3:
                g.drawImage(this.attackFrames.get(this.attackFrameIndex),
                        this.x, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT, null);
                break;
            case 4:
                g.drawImage(this.attackBackFrames.get(this.attackFrameIndex),
                        this.x, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT, null);
                break;
            case 0:
                g.drawImage(this.defaultFrameRight,
                        this.x, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT, null);
                break;
            case -1:
                g.drawImage(this.defaultFrameLeft,
                        this.x, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT, null);
                break;
        }
    }

    public void update(){
        if (this.x > this.player.getX()){
            this.isCharacterMovingLeft = true;
            this.isCharacterMovingRight = false;
            this.paintType = MOVE_LEFT_CODE;
        } else {
            this.isCharacterMovingLeft = false;
            this.isCharacterMovingRight = true;
            this.paintType = MOVE_RIGHT_CODE;
        }
        //if(this.hitBox.intersects(player.hitBox)){
            //this.isCharacterAttacking = true;
            //if (this.isCharacterMovingLeft){
                //this.paintType = ATTACK_LEFT_CODE;
            //} else {
                //this.paintType = ATTACK_RIGHT_CODE;
            //}
        //} else {
            //this.isCharacterAttacking = false;
        //}
        this.moveTowardsPlayer();
        this.loopBetweenFrames();
    }

    protected void loopBetweenFrames() {
        this.setRunFrameIndex(this.getRunFrameIndex() + 1);
        if (this.getRunFrameIndex() % 11 == 0) {
            this.setRunFrameIndex(0);
        }
        this.setAttackFrameIndex(this.getAttackFrameIndex() + 1);
        if (this.getAttackFrameIndex() % 4 == 0){
            this.setAttackFrameIndex(0);
        }
    }

    public void moveTowardsPlayer() {
        int distanceX = this.x - this.player.getX();
        int distanceY = this.y - this.player.getY();
        if(this.canMove()) {
            if (distanceX > 0) {
                this.dx = -CHARACTER_SPEED; // move left
            } else {
                this.dx = CHARACTER_SPEED; // move right
            }
            if (distanceY > 0) {
                this.dy = -CHARACTER_SPEED; // move up
            } else {
                this.dy = CHARACTER_SPEED; // move down
            }
            this.move();
        }else {
            if (distanceX > 0){ // if he goes left and got stuck
                this.dx = -CHARACTER_SPEED;
                this.dy = CHARACTER_SPEED;
            }else if (distanceX < 0){ // if he goes right and got stuck
                this.dx = CHARACTER_SPEED;
                this.dy = CHARACTER_SPEED;
            }
            else if (distanceY > 0){ // if he goes up and got stuck
                this.dy = CHARACTER_SPEED;
                this.dx = CHARACTER_SPEED;
            }else if (distanceY < 0){ // if he goes down and got stuck
                this.dy = -CHARACTER_SPEED;
                this.dx = CHARACTER_SPEED;
            }
            if(this.y <= LOWER_BOUNDARY_Y){
                this.dy = -CHARACTER_SPEED;
            }
            this.move();
        }

    }

    public boolean canMove(){
        int yPosition = this.y + this.dy;
        boolean yPositionOk = yPosition <= LOWER_BOUNDARY_Y && yPosition >= UPPER_BOUNDARY_Y;
        this.hitBox.setLocation((int) (this.hitBox.getX() + dx), (int) (this.hitBox.getY() + dy));
//        this.hitBox.setBounds((int) (this.hitBox.getX() + dx),
//                (int) (this.hitBox.getY() + dy), (int) this.hitBox.getWidth(), (int) this.hitBox.getHeight());
        for (Rectangle obstacle : this.obstacles){
            if (this.hitBox.intersects(obstacle)){
                System.out.println("Hit");
                return false;
            }
        }
        return yPositionOk;
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

    public void setAttackFrameIndex(int attackFrameIndex){
        this.attackFrameIndex = attackFrameIndex;
    }
    public int getAttackFrameIndex(){
        return this.attackFrameIndex;
    }

}

