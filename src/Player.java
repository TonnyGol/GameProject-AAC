import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Player extends Character {
    private final String DEFAULT_FRAME_FILE_PATH = "resources\\Images\\Stand.png";
    private final String DEFAULT_BACK_FRAME_PATH = "resources\\Images\\StandBack.png";
    private final String RUN_IMAGES_PATH = "resources\\Images\\Run";
    private final String RUN_BACK_IMAGES_PATH = "resources\\Images\\RunBack";
    private final String SHOOT_IMAGES_PATH = "resources\\Images\\Shoot";
    private final String SHOOT_BACK_IMAGES_PATH = "resources\\Images\\ShootBack";
    private final String ATTACK_IMAGES_PATH = "resources\\Images\\Attack";
    private final String ATTACK_BACK_IMAGES_PATH = "resources\\Images\\AttackBack";

    private final int CHARACTER_SPEED = 15;
    private final int RIGHT_BOUNDARY_X = 1740;
    private final int LEFT_BOUNDARY_X = -45;

    private int runFrameIndex;
    private int shootFrameIndex;
    private int attackFrameIndex;

    private final int STAND_RIGHT_CODE = 0;
    private final int STAND_LEFT_CODE = -1;
    private final int MOVE_RIGHT_CODE = 1;
    private final int MOVE_LEFT_CODE = 2;
    private boolean isCharacterShooting;
    private final int SHOOT_RIGHT_CODE = 3;
    private final int SHOOT_LEFT_CODE = 4;
    private boolean isCharacterAttacking;
    private final int ATTACK_RIGHT_CODE = 5;
    private final int ATTACK_LEFT_CODE = 6;

    private final List<Image> runRightFrames;
    private final List<Image> runLeftFrames;
    private final List<Image> shootRightFrames;
    private final List<Image> shootLeftFrames;
    private final List<Image> attackRightFrames;
    private final List<Image> attackLeftFrames;

    public Player(int startX, int startY, HashSet<Rectangle> obstacles){
        this.x = startX;
        this.y = startY;
        this.dx = 0;
        this.dy = 0;
        this.runFrameIndex = 0;
        this.shootFrameIndex = 0;
        this.attackFrameIndex = 0;
        this.obstacles = obstacles;
        this.isCharacterStanding = true;
        this.isCharacterMovingRight = false;
        this.isCharacterMovingLeft = false;
        this.isCharacterShooting = false;
        this.isCharacterAttacking = false;
        this.hitBox = new Rectangle(this.x + CHARACTER_WIDTH / 4 - 10,
                this.y + 150, CHARACTER_WIDTH / 2 + 15, (CHARACTER_HEIGHT - 50)/2);
        this.defaultFrameRight = new ImageIcon(DEFAULT_FRAME_FILE_PATH).getImage();
        this.defaultFrameLeft = new ImageIcon(DEFAULT_BACK_FRAME_PATH).getImage();
        this.runRightFrames = loadFrames(8, RUN_IMAGES_PATH);
        this.runLeftFrames = loadFrames(8, RUN_BACK_IMAGES_PATH);
        this.shootRightFrames = loadFrames(4, SHOOT_IMAGES_PATH);
        this.shootLeftFrames = loadFrames(4, SHOOT_BACK_IMAGES_PATH);
        this.attackRightFrames = loadFrames(3, ATTACK_IMAGES_PATH);
        this.attackLeftFrames = loadFrames(3, ATTACK_BACK_IMAGES_PATH);
    }

    public void paint(Graphics g){
        //g.fillRect(this.x + CHARACTER_WIDTH / 4 - 10, this.y + 150, CHARACTER_WIDTH / 2 + 15, (CHARACTER_HEIGHT - 50)/2);
        switch (this.paintType){
            case 1:
                g.drawImage(this.runRightFrames.get(this.runFrameIndex),
                        this.x, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT,null);
                break;
            case 2:
                g.drawImage(this.runLeftFrames.get(this.runFrameIndex),
                        this.x, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT,null);
                break;
            case 3:
                g.drawImage(this.shootRightFrames.get(this.shootFrameIndex),
                        this.x + 30, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT,null);
                break;
            case 4:
                g.drawImage(this.shootLeftFrames.get(this.shootFrameIndex),
                        this.x - 30, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT,null);
                break;
            case 5:
                g.drawImage(this.attackRightFrames.get(this.attackFrameIndex),
                        this.x, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT,null);
                break; // YOU HAVE TO PUT THIS BREAK SO THE CODE WILL WORK !! ! ! !! !
            case 6:
                g.drawImage(this.attackLeftFrames.get(this.attackFrameIndex),
                        this.x, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT,null);
                break; // YOU HAVE TO PUT THIS BREAK SO THE CODE WILL WORK !! ! ! !! !
            case 0:
                g.drawImage(this.defaultFrameRight,
                        this.x, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT,null);
                break;
            case -1:
                g.drawImage(this.defaultFrameLeft,
                        this.x, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT,null);
                break;
        }
    }
    public void update(){
        if(this.isCharacterStanding){
            if (this.isCharacterMovingLeft){
                this.paintType = STAND_LEFT_CODE;
            }
            else{
                this.paintType = STAND_RIGHT_CODE;
            }
            Main.sleep(40);
        }
        else if (this.isCharacterMovingRight){
            if(this.canMove()){
                this.move();
                if (this.isCharacterMovingLeft){
                    this.paintType = MOVE_LEFT_CODE;
                }
                else{
                    this.paintType = MOVE_RIGHT_CODE;
                }
            }
            Main.sleep(40);
            this.loopBetweenFrames();
        }
        else if (this.isCharacterShooting) {
            if (this.isCharacterMovingLeft){
                this.paintType = SHOOT_LEFT_CODE;
            }
            else {
                this.paintType = SHOOT_RIGHT_CODE;
            }
            Main.sleep(40);
            this.loopBetweenFrames();
        }
        else if(this.isCharacterAttacking){
            if (this.isCharacterMovingLeft){
                this.paintType = ATTACK_LEFT_CODE;
            }else {
                this.paintType = ATTACK_RIGHT_CODE;
            }
            Main.sleep(65);
            this.loopBetweenFrames();
        }
    }


    private void loopBetweenFrames(){
        if (this.isCharacterMovingRight){
            this.setRunFrameIndex(this.getRunFrameIndex() + 1);
            if (this.getRunFrameIndex() % 8 == 0){
                this.setRunFrameIndex(0);
            }
        }
        if (this.isCharacterShooting){
            this.setShootFrameIndex(this.getShootFrameIndex() + 1);
            if (this.getShootFrameIndex() % 4 == 0){
                this.setShootFrameIndex(0);
            }
        }
        if (this.isCharacterAttacking){
            this.setAttackFrameIndex(this.getAttackFrameIndex() + 1);
            if (this.getAttackFrameIndex() % 3 == 0){
                this.setAttackFrameIndex(0);
            }
        }
    }

    private List<Image> loadFrames(int frames, String fileName){
        List<Image> frameList = new ArrayList<>(frames);
        for (int i = 1; i <= frames; i++){
            Image frame = new ImageIcon(fileName+i+".png").getImage();
            frameList.add(frame);
        }
        return frameList;
    }

    public void move(){
        this.x += this.dx;
        this.y += this.dy;
    }

    public boolean canMove(){
        int xPosition = this.x + dx;
        int yPosition = this.y + dy;
        boolean xPositionOk = xPosition >= LEFT_BOUNDARY_X && xPosition <= RIGHT_BOUNDARY_X;
        boolean yPositionOk = yPosition <= LOWER_BOUNDARY_Y && yPosition >= UPPER_BOUNDARY_Y;
        this.hitBox.setBounds(xPosition + CHARACTER_WIDTH / 4 - 10,
                yPosition + 150, CHARACTER_WIDTH / 2 + 15, (CHARACTER_HEIGHT - 50)/2);
        for (Rectangle obstacle : this.obstacles){
            if (this.hitBox.intersects(obstacle)){
                System.out.println("Hit");
                return false;
            }
        }
        return xPositionOk && yPositionOk;
    }

    public int getDx() {
        return dx;
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

    public int getCHARACTER_SPEED() {
        return CHARACTER_SPEED;
    }

    public int getCHARACTER_WIDTH() {
        return CHARACTER_WIDTH;
    }

    public int getCHARACTER_HEIGHT() {
        return CHARACTER_HEIGHT;
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
        this.shootFrameIndex = shootFrameIndex;
    }
    public int getShootFrameIndex() {
        return shootFrameIndex;
    }

    public int getRunFrameIndex() {
        return runFrameIndex;
    }
    public void setRunFrameIndex(int runFrameIndex) {
        this.runFrameIndex = runFrameIndex;
    }

    public void setCharacterMovingRight(boolean characterMovingRight) {
        isCharacterMovingRight = characterMovingRight;
    }
    public void setCharacterMovingLeft(boolean characterMovingLeft) {
        isCharacterMovingLeft = characterMovingLeft;
    }

    public void setCharacterShooting(boolean characterShooting) {
        isCharacterShooting = characterShooting;
    }

    public void setCharacterAttack(boolean characterAttacking) {
        isCharacterAttacking = characterAttacking;
    }

    public void setCharacterStanding(boolean characterStanding){
        this.isCharacterStanding = characterStanding;
    }

    public int getAttackFrameIndex(){
        return this.attackFrameIndex;
    }
    public void setAttackFrameIndex(int attackFrameIndex){
        this.attackFrameIndex = attackFrameIndex;
    }

}
