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
    private final String RELOAD_RIGHT_IMAGES_PATH = "resources\\Images\\Recharge";
    private final String RELOAD_LEFT_IMAGES_PATH = "resources\\Images\\RechargeBack";
    private final String DEATH_RIGHT_IMAGES_PATH = "resources\\Images\\Death";
    private final String DEATH_LEFT_IMAGES_PATH = "resources\\Images\\DeathBack";

    private final int CHARACTER_SPEED = 15;
    private final int AMMO_CAPACITY = 15;
    private final int RIGHT_BOUNDARY_X = 1740;
    private final int LEFT_BOUNDARY_X = -45;
    private final int RELOAD_FRAME_COUNT = 13;
    private final int SHOOT_FRAME_COUNT = 4;
    private final int ATTACK_FRAME_COUNT = 3;
    private final int RUN_FRAME_COUNT = 8;
    private final int DEATH_FRAME_COUNT = 4;

    private boolean isCharacterStanding;
    private boolean isCharacterShooting;
    private boolean isCharacterReloading;

    private int shootFrameIndex;
    private int reloadFrameIndex;

    private List<Bullet> bullets;
    private final List<Image> shootRightFrames;
    private final List<Image> shootLeftFrames;
    private final List<Image> reloadRightFrames;
    private final List<Image> reloadLeftFrames;

    public Player(int startX, int startY, HashSet<Rectangle> obstacles){
        super(startX, startY, obstacles);
        this.setHitBox(new Rectangle(this.getX() + this.getCHARACTER_WIDTH() / 4 - 10,
                this.getY() + 150, this.getCHARACTER_WIDTH() / 2 + 15, (this.getCHARACTER_HEIGHT() - 50)/2));

        this.setDefaultFrameRight(new ImageIcon(DEFAULT_FRAME_RIGHT_PATH).getImage());
        this.setDefaultFrameLeft(new ImageIcon(DEFAULT_FRAME_LEFT_PATH).getImage());
        this.setRunRightFrames(Main.loadFrames(RUN_FRAME_COUNT, RUN_RIGHT_IMAGES_PATH));
        this.setRunLeftFrames(Main.loadFrames(RUN_FRAME_COUNT, RUN_LEFT_IMAGES_PATH));
        this.setAttackRightFrames(Main.loadFrames(ATTACK_FRAME_COUNT, ATTACK_RIGHT_IMAGES_PATH));
        this.setAttackLeftFrames(Main.loadFrames(ATTACK_FRAME_COUNT, ATTACK_LEFT_IMAGES_PATH));
        this.setDeathRightFrames(Main.loadFrames(DEATH_FRAME_COUNT, DEATH_RIGHT_IMAGES_PATH));
        this.setDeathLeftFrames(Main.loadFrames(DEATH_FRAME_COUNT, DEATH_LEFT_IMAGES_PATH));
        this.setCurrentFrame(this.getDefaultFrameRight());

        this.isCharacterStanding = true;
        this.bullets = new LinkedList<>();
        this.shootFrameIndex = 0;
        this.reloadFrameIndex = 0;
        this.isCharacterShooting = false;
        this.isCharacterReloading = false;
        this.shootRightFrames = Main.loadFrames(SHOOT_FRAME_COUNT, SHOOT_RIGHT_IMAGES_PATH);
        this.shootLeftFrames = Main.loadFrames(SHOOT_FRAME_COUNT, SHOOT_LEFT_IMAGES_PATH);
        this.reloadRightFrames = Main.loadFrames(RELOAD_FRAME_COUNT, RELOAD_RIGHT_IMAGES_PATH);
        this.reloadLeftFrames = Main.loadFrames(RELOAD_FRAME_COUNT, RELOAD_LEFT_IMAGES_PATH);
    }
    @Override
    public void paint(Graphics g){
        //g.fillRect(this.getX() + CHARACTER_WIDTH / 4 - 10, this.getY() + 150, CHARACTER_WIDTH / 2 + 15, (CHARACTER_HEIGHT - 50)/2);
        g.drawImage(this.getCurrentFrame(),
                this.getX(), this.getY(), this.getCHARACTER_WIDTH(), this.getCHARACTER_HEIGHT(),null);
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
        this.checkIfAlive();
    }

    private void checkIfReloading(){
        if(this.isCharacterReloading){
            if (this.isCharacterMovingLeft()){
                this.setCurrentFrame(this.reloadLeftFrames.get(this.reloadFrameIndex));
            }else {
                this.setCurrentFrame(this.reloadRightFrames.get(this.reloadFrameIndex));
            }
        }
        if (this.bullets.size() == AMMO_CAPACITY){
            this.isCharacterReloading = true;
            boolean allBulletsGone = true; // if all the bullets are out of bounds of window
            for (Bullet bullet : this.bullets){
                allBulletsGone = bullet.getX() > RIGHT_BOUNDARY_X + 180 || bullet.getX() < LEFT_BOUNDARY_X + 45;
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
            if (!this.isCharacterReloading){
                if (this.isCharacterMovingLeft()){
                    this.createAndShootBullet(this.getX() - 30, this.getY() + 105, -1);
                    this.setCurrentFrame(this.shootLeftFrames.get(this.shootFrameIndex));
                }
                else {
                    this.createAndShootBullet(this.getX() + 105, this.getY() + 105, 1);
                    this.setCurrentFrame(this.shootRightFrames.get(this.shootFrameIndex));
                }
            }
        }
    }
    private void checkIfAttacking(){
        if(this.isCharacterAttacking()){
            if (!this.isCharacterReloading && !this.isCharacterShooting){
                if (this.isCharacterMovingLeft()){
                    this.setCurrentFrame(this.getAttackLeftFrames().get(this.getAttackFrameIndex()));
                }else {
                    this.setCurrentFrame(this.getAttackRightFrames().get(this.getAttackFrameIndex()));
                }
            }
        }
    }
    private void checkMovementDirection(){
        if (this.isCharacterMovingRight()){
            if (!this.isCharacterReloading && !this.isCharacterShooting && !this.isCharacterAttacking()){
                if(this.canMove()){
                    this.move();
                    if (this.isCharacterMovingLeft()){
                        this.setCurrentFrame(this.getRunLeftFrames().get(this.getRunFrameIndex()));
                    }
                    else{
                        this.setCurrentFrame(this.getRunRightFrames().get(this.getRunFrameIndex()));
                    }
                }else {
                    if (this.isCharacterMovingLeft()){
                        this.setCurrentFrame(this.getDefaultFrameLeft());
                    }
                    else{
                        this.setCurrentFrame(this.getDefaultFrameRight());
                    }
                }
            }
        }
    }
    private void checkIfStanding(){
        if(this.isCharacterStanding){
            if (!this.isCharacterReloading && !this.isCharacterShooting &&
                    !this.isCharacterAttacking() && !this.isCharacterMovingRight()){
                if (this.isCharacterMovingLeft()){
                    this.setCurrentFrame(this.getDefaultFrameLeft());
                }
                else{
                    this.setCurrentFrame(this.getDefaultFrameRight());
                }
            }
        }
    }
    private void checkIfAlive(){
        if(!this.isAlive()){
            if (this.isCharacterMovingLeft()){
                this.setCurrentFrame(this.getDeathLeftFrames().get(this.getDeathFrameIndex()));
            }else {
                this.setCurrentFrame(this.getDeathRightFrames().get(this.getDeathFrameIndex()));
            }
        }
    }

    private void createAndShootBullet(int xBullet, int yBullet, int direction) {
        if (!this.isCharacterReloading){
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
        if (this.isCharacterReloading){
            this.setReloadFrameIndex(this.getReloadFrameIndex() + 1);
            if (this.getReloadFrameIndex() % this.reloadRightFrames.size() == 0){
                this.isCharacterReloading = false;
                this.setReloadFrameIndex(0);
            }
        }
        if (!this.isAlive() && this.getDeathFrameIndex() != 3){
            this.setDeathFrameIndex(this.getDeathFrameIndex() + 1);
            if (this.getDeathFrameIndex() % this.getDeathRightFrames().size() == 0){
                this.setDeathFrameIndex(3);
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

    public int getReloadFrameIndex() {
        return reloadFrameIndex;
    }
    public boolean isCharacterReloading(){
        return isCharacterReloading;
    }

    public void setReloadFrameIndex(int reloadFrameIndex) {
        this.reloadFrameIndex = reloadFrameIndex;
    }
    public boolean isCharacterShooting() {
        return isCharacterShooting;
    }
    public void setCharacterReloading(boolean characterReloading) {
        isCharacterReloading = characterReloading;
    }
    public boolean isCharacterStanding() {
        return isCharacterStanding;
    }
}
