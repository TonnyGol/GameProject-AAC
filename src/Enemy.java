import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Enemy extends Character {
    private final String RUN_RIGHT_IMAGES_PATH = "resources\\Images\\WolfWalk";
    private final String RUN_LEFT_IMAGES_PATH = "resources\\Images\\WolfWalkBack";
    private final String ATTACK_RIGHT_IMAGES_PATH = "resources\\Images\\WolfAttack";
    private final String ATTACK_LEFT_IMAGES_PATH = "resources\\Images\\WolfAttackBack";
    private final String DIE_RIGHT_IMAGES_PATH = "resources\\Images\\WolfDead";
    private final String DIE_LEFT_IMAGES_PATH = "resources\\Images\\WolfDeadBack";

    private final int CHARACTER_SPEED = 5;
    private final int RUN_FRAME_COUNT = 11;
    private final int ATTACK_FRAME_COUNT = 4;
    private final int DEATH_FRAME_COUNT = 2;

    private final Player player;

    public Enemy(int startX, int startY, Player player, HashSet<Rectangle> obstacles) {
        super(startX, startY, obstacles);
        this.setHitBox(new Rectangle(this.getX() + (this.getCHARACTER_WIDTH() / 4) - 10,
                this.getY() + 15, (this.getCHARACTER_WIDTH() / 2) + 50, this.getCHARACTER_HEIGHT()));

        this.setRunRightFrames(Main.loadFrames(RUN_FRAME_COUNT, RUN_RIGHT_IMAGES_PATH));
        this.setRunLeftFrames(Main.loadFrames(RUN_FRAME_COUNT, RUN_LEFT_IMAGES_PATH));
        this.setAttackRightFrames(Main.loadFrames(ATTACK_FRAME_COUNT, ATTACK_RIGHT_IMAGES_PATH));
        this.setAttackLeftFrames(Main.loadFrames(ATTACK_FRAME_COUNT, ATTACK_LEFT_IMAGES_PATH));
        this.setDeathRightFrames(Main.loadFrames(DEATH_FRAME_COUNT, DIE_RIGHT_IMAGES_PATH));
        this.setDeathLeftFrames(Main.loadFrames(DEATH_FRAME_COUNT, DIE_LEFT_IMAGES_PATH));
        this.setCurrentFrame(this.getDefaultFrameRight());
        this.player = player;
    }
    @Override
    public void paint(Graphics g) {
        g.drawImage(this.getCurrentFrame(),
                this.getX(), this.getY(), this.getCHARACTER_WIDTH(), this.getCHARACTER_HEIGHT(),null);
        this.loopBetweenFrames();
    }

    public void update(){
        this.checkMovementDirection();
        this.checkIfAttacking();
        this.checkBulletCollision();
        this.checkAlive();
    }
    public void checkMovementDirection(){
        if (this.getX() > this.player.getX()){
            this.setCharacterMovingLeft(true);
            this.setCharacterMovingRight(false);
            this.setCurrentFrame(this.getRunLeftFrames().get(this.getRunFrameIndex()));
        } else {
            this.setCharacterMovingLeft(false);
            this.setCharacterMovingRight(true);
            this.setCurrentFrame(this.getRunRightFrames().get(this.getRunFrameIndex()));
        }
    }
    public void checkIfAttacking(){
        Rectangle attackHitBox = new Rectangle((int) this.getHitBox().getX(),
                (int) this.getHitBox().getY() + 105, (int) this.getHitBox().getWidth(), (int) this.getHitBox().getHeight()/2);
        if(attackHitBox.intersects(player.getHitBox())){
            this.setCharacterAttacking(true);
            if (this.isCharacterMovingLeft()){
                this.setCurrentFrame(this.getAttackLeftFrames().get(this.getAttackFrameIndex()));
            } else {
                this.setCurrentFrame(this.getAttackRightFrames().get(this.getAttackFrameIndex()));
            }
            this.player.setAlive(false);
        } else {
            this.setCharacterAttacking(false);
        }
    }
    public void checkBulletCollision(){
        for (Bullet bullet : this.player.getBullets()){
            if (this.getHitBox().intersects(bullet)){
                if (this.isAlive()){
                    this.player.setPoints(this.player.getPoints() + 10);
                }
                bullet.setBounds(0,0,0,0);
                this.setHitBox(new Rectangle(0,0,0,0));
                this.setAlive(false);
                System.out.println("Dead");
            }
        }
    }
    public void checkAlive(){
        if (this.isAlive()){
            if(!this.isCharacterAttacking()){
                this.moveTowardsPlayer();
            }
        }else {
            if (this.isCharacterMovingLeft()){
                this.setCurrentFrame(this.getDeathLeftFrames().get(this.getDeathFrameIndex()));
            }else {
                this.setCurrentFrame(this.getDeathRightFrames().get(this.getDeathFrameIndex()));
            }
        }
    }


    protected void loopBetweenFrames() {
        this.setRunFrameIndex(this.getRunFrameIndex() + 1);
        if (this.getRunFrameIndex() % this.getRunRightFrames().size() == 0) {
            this.setRunFrameIndex(0);
        }
        this.setAttackFrameIndex(this.getAttackFrameIndex() + 1);
        if (this.getAttackFrameIndex() % this.getAttackRightFrames().size() == 0){
            this.setAttackFrameIndex(0);
        }
        if (this.getDeathFrameIndex() != 1) {
            this.setDeathFrameIndex(this.getDeathFrameIndex() + 1);
        }
    }

    public void moveTowardsPlayer() {
        int distanceX = this.getX() - this.player.getX();
        int distanceY = this.getY() - this.player.getY();
        if(this.canMove()) {
            if (distanceX > 0) {
                this.setDx(-CHARACTER_SPEED); // move left
            } else {
                this.setDx(CHARACTER_SPEED); // move right
            }
            if (distanceY > 0) {
                this.setDy(-CHARACTER_SPEED); // move up
            } else {
                this.setDy(CHARACTER_SPEED); // move down
            }
            this.move();
        }else {
            if (distanceX > 0){ // if he goes left and got stuck
                this.setDx(-CHARACTER_SPEED);
                this.setDy(CHARACTER_SPEED);
            }else if (distanceX < 0){ // if he goes right and got stuck
                this.setDx(CHARACTER_SPEED);
                this.setDy(CHARACTER_SPEED);
            }
            else if (distanceY > 0){ // if he goes up and got stuck
                this.setDy(CHARACTER_SPEED);
                this.setDx(CHARACTER_SPEED);
            }else if (distanceY < 0){ // if he goes down and got stuck
                this.setDy(-CHARACTER_SPEED);
                this.setDx(CHARACTER_SPEED);
            }
            if(this.getY() >= this.getLOWER_BOUNDARY_Y() / 2){
                this.setDy(-CHARACTER_SPEED);
            }
            this.move();
        }
    }

    public boolean canMove(){
        boolean checkCollisionOk = true;
        int yPosition = this.getY() + this.getDy();
        Rectangle CollisionHitBox = new Rectangle((int) this.getHitBox().getX(),
                (int) this.getHitBox().getY() + 105, (int) this.getHitBox().getWidth(), (int) this.getHitBox().getHeight()/2);
        boolean yPositionOk = yPosition <= this.getLOWER_BOUNDARY_Y() && yPosition >= this.getUPPER_BOUNDARY_Y();
        this.getHitBox().setLocation((int) (this.getHitBox().getX() + this.getDx()), (int) (this.getHitBox().getY() + this.getDy()));
        for (Rectangle obstacle : this.getObstacles()){
            if (CollisionHitBox.intersects(obstacle)){
                System.out.println("Hit");
                checkCollisionOk = false;
            }
        }
        return yPositionOk && checkCollisionOk;
    }


    public void move(){
        this.setX(this.getX() + this.getDx());
        this.setY(this.getY() + this.getDy());
    }
}

