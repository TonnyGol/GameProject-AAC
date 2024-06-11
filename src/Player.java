import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Player extends Character {
    private final String DEFAULT_FRAME_RIGHT_PATH = "resources\\Images\\Stand.png";
    private final String DEFAULT_FRAME_LEFT_PATH = "resources\\Images\\StandBack.png";
    private final String RUN_RIGHT_IMAGES_PATH = "resources\\Images\\Run";
    private final String RUN_LEFT_IMAGES_PATH = "resources\\Images\\RunBack";
    private final String SHOOT_RIGHT_IMAGES_PATH = "resources\\Images\\Shoot";
    private final String SHOOT_LEFT_IMAGES_PATH = "resources\\Images\\ShootBack";
    private final String ATTACK_RIGHT_IMAGES_PATH = "resources\\Images\\Attack";
    private final String ATTACK_LEFT_IMAGES_PATH = "resources\\Images\\AttackBack";
    private final String RECHARGE_RIGHT_IMAGES_PATH = "resources\\Images\\Recharge";
    private final String RECHARGE_LEFT_IMAGES_PATH = "resources\\Images\\RechargeBack";

    private final int CHARACTER_SPEED = 15;
    private final int AMMO_CAPACITY = 15;
    private final int RIGHT_BOUNDARY_X = 1740;
    private final int LEFT_BOUNDARY_X = -45;

    private boolean isCharacterStanding;
    private boolean isCharacterShooting;
    private boolean isCharacterRecharging;

    private int shootFrameIndex;
    private int rechargeFrameIndex;

    private final int STAND_RIGHT_CODE = 0;
    private final int STAND_LEFT_CODE = -1;
    private final int MOVE_RIGHT_CODE = 1;
    private final int MOVE_LEFT_CODE = 2;
    private final int SHOOT_RIGHT_CODE = 3;
    private final int SHOOT_LEFT_CODE = 4;
    private final int ATTACK_RIGHT_CODE = 5;
    private final int ATTACK_LEFT_CODE = 6;
    private final int RECHARGE_RIGHT_CODE = 7;
    private final int RECHARGE_LEFT_CODE = 8;

    private List<Bullet> bullets;
    private final List<Image> shootRightFrames;
    private final List<Image> shootLeftFrames;
    private final List<Image> rechargeRightFrames;
    private final List<Image> rechargeLeftFrames;

    public Player(int startX, int startY, HashSet<Rectangle> obstacles){
        super(startX, startY, obstacles);
        this.setHitBox(new Rectangle(this.getX() + this.getCHARACTER_WIDTH() / 4 - 10,
                this.getY() + 150, this.getCHARACTER_WIDTH() / 2 + 15, (this.getCHARACTER_HEIGHT() - 50)/2));

        this.setDefaultFrameRight(new ImageIcon(DEFAULT_FRAME_RIGHT_PATH).getImage());
        this.setDefaultFrameLeft(new ImageIcon(DEFAULT_FRAME_LEFT_PATH).getImage());

        this.setRunRightFrames(this.loadFrames(8, RUN_RIGHT_IMAGES_PATH));
        this.setRunLeftFrames(this.loadFrames(8, RUN_LEFT_IMAGES_PATH));
        this.setAttackRightFrames(this.loadFrames(3, ATTACK_RIGHT_IMAGES_PATH));
        this.setAttackLeftFrames(this.loadFrames(3, ATTACK_LEFT_IMAGES_PATH));

        this.isCharacterStanding = true;
        this.bullets = new LinkedList<>();
        this.shootFrameIndex = 0;
        this.rechargeFrameIndex = 0;
        this.isCharacterShooting = false;
        this.isCharacterRecharging = false;
        this.shootRightFrames = loadFrames(4, SHOOT_RIGHT_IMAGES_PATH);
        this.shootLeftFrames = loadFrames(4, SHOOT_LEFT_IMAGES_PATH);
        this.rechargeRightFrames = loadFrames(13, RECHARGE_RIGHT_IMAGES_PATH);
        this.rechargeLeftFrames = loadFrames(13, RECHARGE_LEFT_IMAGES_PATH);
    }
    @Override
    public void paint(Graphics g){
        //g.fillRect(this.getX() + CHARACTER_WIDTH / 4 - 10, this.getY() + 150, CHARACTER_WIDTH / 2 + 15, (CHARACTER_HEIGHT - 50)/2);
        switch (this.getPaintType()){
            case 1:
                g.drawImage(this.getRunRightFrames().get(this.getRunFrameIndex()),
                        this.getX(), this.getY(), this.getCHARACTER_WIDTH(), this.getCHARACTER_HEIGHT(),null);
                break;
            case 2:
                g.drawImage(this.getRunLeftFrames().get(this.getRunFrameIndex()),
                        this.getX(), this.getY(), this.getCHARACTER_WIDTH(), this.getCHARACTER_HEIGHT(),null);
                break;
            case 3:
                g.drawImage(this.shootRightFrames.get(this.shootFrameIndex),
                        this.getX() + 30, this.getY(), this.getCHARACTER_WIDTH(), this.getCHARACTER_HEIGHT(),null);
                break;
            case 4:
                g.drawImage(this.shootLeftFrames.get(this.shootFrameIndex),
                        this.getX() - 30, this.getY(), this.getCHARACTER_WIDTH(), this.getCHARACTER_HEIGHT(),null);
                break;
            case 5:
                g.drawImage(this.getAttackRightFrames().get(this.getAttackFrameIndex()),
                        this.getX(), this.getY(), this.getCHARACTER_WIDTH(), this.getCHARACTER_HEIGHT(),null);
                break;
            case 6:
                g.drawImage(this.getAttackLeftFrames().get(this.getAttackFrameIndex()),
                        this.getX(), this.getY(), this.getCHARACTER_WIDTH(), this.getCHARACTER_HEIGHT(),null);
                break;
            case 7:
                g.drawImage(this.rechargeRightFrames.get(this.getRechargeFrameIndex()),
                        this.getX(), this.getY(), this.getCHARACTER_WIDTH(), this.getCHARACTER_HEIGHT(),null);
                break;
            case 8:
                g.drawImage(this.rechargeLeftFrames.get(this.getRechargeFrameIndex()),
                        this.getX(), this.getY(), this.getCHARACTER_WIDTH(), this.getCHARACTER_HEIGHT(),null);
                break;
            case 0:
                g.drawImage(this.getDefaultFrameRight(),
                        this.getX(), this.getY(), this.getCHARACTER_WIDTH(), this.getCHARACTER_HEIGHT(),null);
                break;
            case -1:
                g.drawImage(this.getDefaultFrameLeft(),
                        this.getX(), this.getY(), this.getCHARACTER_WIDTH(), this.getCHARACTER_HEIGHT(),null);
                break;
        }
        for (Bullet bullet : this.bullets){
            g.fillRect((int) bullet.getX(), (int) bullet.getY(), (int) bullet.getWidth(), (int) bullet.getHeight());
        }
        this.loopBetweenFrames();
    }

    public void update(){
        this.checkIfReloading();
        this.checkIfShooting();
        this.checkIfAttacking();
        this.checkMovementDirection();
        this.checkIfStanding();
    }

    private void checkIfReloading(){
        if(this.isCharacterRecharging){
            if (this.isCharacterMovingLeft()){
                this.setPaintType(RECHARGE_LEFT_CODE);
            }else {
                this.setPaintType(RECHARGE_RIGHT_CODE);
            }
        }
        if (this.bullets.size() == AMMO_CAPACITY){
            this.isCharacterRecharging = true;
            boolean allBulletsGone = true; // if all the bullets are out of bounds of window
            for (Bullet bullet : this.bullets){
                if (bullet.getX() > RIGHT_BOUNDARY_X + 180 || bullet.getX() < LEFT_BOUNDARY_X + 45){
                    allBulletsGone = true;
                } else { allBulletsGone = false; }
            }
            if (allBulletsGone){
                this.bullets.clear();
            }
        }
        for (Bullet bullet : this.bullets){
            bullet.fly();
        }
    }
    private void checkIfShooting(){
        if (this.isCharacterShooting) {
            if (!this.isCharacterRecharging){
                if (this.isCharacterMovingLeft()){
                    this.createAndShootBullet(this.getX() - 30, this.getY() + 105, -1);
                    this.setPaintType(SHOOT_LEFT_CODE);
                }
                else {
                    this.createAndShootBullet(this.getX() + 105, this.getY() + 105, 1);
                    this.setPaintType(SHOOT_RIGHT_CODE);
                }
            }
        }
    }
    private void checkIfAttacking(){
        if(this.isCharacterAttacking()){
            if (!this.isCharacterRecharging && !this.isCharacterShooting){
                if (this.isCharacterMovingLeft()){
                    this.setPaintType(ATTACK_LEFT_CODE);
                }else {
                    this.setPaintType(ATTACK_RIGHT_CODE);
                }
            }
        }
    }
    private void checkMovementDirection(){
        if (this.isCharacterMovingRight()){
            if (!this.isCharacterRecharging && !this.isCharacterShooting && !this.isCharacterAttacking()){
                if(this.canMove()){
                    this.move();
                    if (this.isCharacterMovingLeft()){
                        this.setPaintType(MOVE_LEFT_CODE);
                    }
                    else{
                        this.setPaintType(MOVE_RIGHT_CODE);
                    }
                }
            }
        }
    }
    private void checkIfStanding(){
        if(this.isCharacterStanding){
            if (!this.isCharacterRecharging && !this.isCharacterShooting &&
                    !this.isCharacterAttacking() && !this.isCharacterMovingRight()){
                if (this.isCharacterMovingLeft()){
                    this.setPaintType(STAND_LEFT_CODE);
                }
                else{
                    this.setPaintType(STAND_RIGHT_CODE);
                }
            }
        }
    }

    private void createAndShootBullet(int xBullet, int yBullet, int direction) {
        if (!this.isCharacterRecharging){
            Bullet newBullet = new Bullet(xBullet, yBullet, direction);
            this.bullets.add(newBullet);
        }
    }

    public void move(){
        this.setX(this.getX() + this.getDx());
        this.setY(this.getY() + this.getDy());
    }

    public boolean canMove(){
        int xPosition = this.getX() + this.getDx();
        int yPosition = this.getY() + this.getDy();
        boolean xPositionOk = xPosition >= LEFT_BOUNDARY_X && xPosition <= RIGHT_BOUNDARY_X;
        boolean yPositionOk = yPosition <= this.getLOWER_BOUNDARY_Y() && yPosition >= this.getUPPER_BOUNDARY_Y();
        this.getHitBox().setBounds(xPosition + this.getCHARACTER_WIDTH() / 4 - 10,
                yPosition + 150, this.getCHARACTER_WIDTH() / 2 + 15, (this.getCHARACTER_HEIGHT() - 50)/2);
        for (Rectangle obstacle : this.getObstacles()){
            if (this.getHitBox().intersects(obstacle)){
                System.out.println("Hit");
                return false;
            }
        }
        return xPositionOk && yPositionOk;
    }

    private void loopBetweenFrames(){
        if (this.isCharacterMovingRight()){
            this.setRunFrameIndex(this.getRunFrameIndex() + 1);
            if (this.getRunFrameIndex() % this.getRunRightFrames().size() == 0){
                this.setRunFrameIndex(0);
            }
        }
        if (this.isCharacterShooting){
            this.setShootFrameIndex(this.getShootFrameIndex() + 1);
            if (this.getShootFrameIndex() % this.shootRightFrames.size() == 0){
                this.setShootFrameIndex(0);
            }
        }
        if (this.isCharacterAttacking()){
            this.setAttackFrameIndex(this.getAttackFrameIndex() + 1);
            if (this.getAttackFrameIndex() % this.getAttackRightFrames().size() == 0){
                this.setAttackFrameIndex(0);
            }
        }
        if (this.isCharacterRecharging){
            this.setRechargeFrameIndex(this.getRechargeFrameIndex() + 1);
            if (this.getRechargeFrameIndex() % this.rechargeRightFrames.size() == 0){
                this.isCharacterRecharging = false;
                this.setRechargeFrameIndex(0);
            }
        }
    }

    public List<Bullet> getBullets(){
        return this.bullets;
    }
    public int getCHARACTER_SPEED() {
        return CHARACTER_SPEED;
    }
    public void setShootFrameIndex(int shootFrameIndex) {
        this.shootFrameIndex = shootFrameIndex;
    }
    public int getShootFrameIndex() {
        return shootFrameIndex;
    }
    public void setCharacterStanding(boolean characterStanding) {
        isCharacterStanding = characterStanding;
    }
    public void setCharacterShooting(boolean characterShooting) {
        isCharacterShooting = characterShooting;
    }

    public int getRechargeFrameIndex() {
        return rechargeFrameIndex;
    }
    public boolean isCharacterRecharging(){
        return isCharacterRecharging;
    }

    public void setRechargeFrameIndex(int rechargeFrameIndex) {
        this.rechargeFrameIndex = rechargeFrameIndex;
    }
    public boolean isCharacterShooting() {
        return isCharacterShooting;
    }
    public void setCharacterRecharging(boolean characterRecharging) {
        isCharacterRecharging = characterRecharging;
    }
    public boolean isCharacterStanding() {
        return isCharacterStanding;
    }
}
