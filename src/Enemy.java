import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Enemy extends Character {
    private final int CHARACTER_SPEED = 5;

    private final String RUN_RIGHT_IMAGES_PATH = "resources\\Images\\WolfWalk";
    private final String RUN_LEFT_IMAGES_PATH = "resources\\Images\\WolfWalkBack";
    private final String ATTACK_RIGHT_IMAGES_PATH = "resources\\Images\\WolfAttack";
    private final String ATTACK_LEFT_IMAGES_PATH = "resources\\Images\\WolfAttackBack";
    private final String DIE_RIGHT_IMAGES_PATH = "resources\\Images\\WolfDead";
    private final String DIE_LEFT_IMAGES_PATH = "resources\\Images\\WolfDeadBack";

    private final int MOVE_RIGHT_CODE = 1;
    private final int MOVE_LEFT_CODE = 2;
    private boolean isCharacterAttacking;
    private final int ATTACK_RIGHT_CODE = 3;
    private final int ATTACK_LEFT_CODE = 4;
    private final int DIE_RIGHT_CODE = 5;
    private final int DIE_LEFT_CODE = 6;
    private int runFrameIndex;
    private int attackFrameIndex;
    private int deathFrameIndex;
    private Player player;

    private List<Image> runRightFrames;
    private List<Image> runLeftFrames;
    private List<Image> attackRightFrames;
    private List<Image> attackLeftFrames;
    private List<Image> deathRightFrames;
    private List<Image> deathLeftFrames;

    public Enemy(int startX, int startY, Player player, HashSet<Rectangle> obstacles) {
        this.isCharacterMovingRight = false;
        this.isCharacterMovingLeft = false;
        this.isCharacterAttacking = false;
        this.x = startX;
        this.y = startY;
        this.dx = 0;
        this.dy = 0;
        this.isAlive = true;
        this.runFrameIndex = 0;
        this.attackFrameIndex = 0;
        this.deathFrameIndex = 0;
        this.obstacles = obstacles;
        this.hitBox = new Rectangle(this.x + CHARACTER_WIDTH / 4 - 10,
                this.y + 100, CHARACTER_WIDTH / 2 + 15, (CHARACTER_HEIGHT + 50)/2);
        this.runRightFrames = loadFrames(11, RUN_RIGHT_IMAGES_PATH);
        this.runLeftFrames = loadFrames(11, RUN_LEFT_IMAGES_PATH);
        this.attackRightFrames = loadFrames(4, ATTACK_RIGHT_IMAGES_PATH);
        this.attackLeftFrames = loadFrames(4, ATTACK_LEFT_IMAGES_PATH);
        this.deathRightFrames = loadFrames(2, DIE_RIGHT_IMAGES_PATH);
        this.deathLeftFrames = loadFrames(2, DIE_LEFT_IMAGES_PATH);
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
//        g.fillRect(this.x + CHARACTER_WIDTH / 4 - 10,
//                this.y + 100, CHARACTER_WIDTH / 2 + 15, (CHARACTER_HEIGHT + 50)/2);
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
                g.drawImage(this.attackRightFrames.get(this.attackFrameIndex),
                        this.x, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT, null);
                break;
            case 4:
                g.drawImage(this.attackLeftFrames.get(this.attackFrameIndex),
                        this.x, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT, null);
                break;
            case 5:
                for (int i = 0; i < this.deathRightFrames.size(); i++){
                    g.drawImage(this.deathRightFrames.get(i),
                            this.x, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT, null);
                }
                break;
            case 6:
                for (int i = 0; i < this.deathLeftFrames.size(); i++){
                    g.drawImage(this.deathLeftFrames.get(i),
                            this.x, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT, null);
                }
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
        if(this.hitBox.intersects(player.hitBox)){
            this.isCharacterAttacking = true;
            if (this.isCharacterMovingLeft){
                this.paintType = ATTACK_LEFT_CODE;
            } else {
                this.paintType = ATTACK_RIGHT_CODE;
            }
        } else {
            this.isCharacterAttacking = false;
        }
        for (Bullet bullet : this.player.getBullets()){
            if (this.hitBox.intersects(bullet)){
                this.setIsAlive(false);
                System.out.println("Dead");
                //this.player.addPoint();
            }
        }
        if (this.isAlive){
            this.moveTowardsPlayer();
        }else {
            if (this.isCharacterMovingLeft){
                this.paintType = DIE_LEFT_CODE;
            }else {
                this.paintType = DIE_RIGHT_CODE;
            }
        }
        if (this.isAlive){
            this.loopBetweenFrames();
        }
    }

    protected void loopBetweenFrames() {
        this.setRunFrameIndex(this.getRunFrameIndex() + 1);
        if (this.getRunFrameIndex() % 11 == 0) {
            this.setRunFrameIndex(0);
        }
        this.setAttackFrameIndex(this.getAttackFrameIndex() + 1);
        if (this.getAttackFrameIndex() % this.attackRightFrames.size() == 0){
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
//            if(this.y >= UPPER_BOUNDARY_Y){
//                this.dy = CHARACTER_SPEED;
//            }
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

    public int getDeathFrameIndex() {
        return deathFrameIndex;
    }

    public void setDeathFrameIndex(int deathFrameIndex) {
        this.deathFrameIndex = deathFrameIndex;
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

