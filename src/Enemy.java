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

    private final int MOVE_RIGHT_CODE = 1;
    private final int MOVE_LEFT_CODE = 2;
    private final int ATTACK_RIGHT_CODE = 3;
    private final int ATTACK_LEFT_CODE = 4;
    private final int DIE_RIGHT_CODE = 5;
    private final int DIE_LEFT_CODE = 6;

    private Player player;
    private int deathFrameIndex;

    private List<Image> deathRightFrames;
    private List<Image> deathLeftFrames;

    public Enemy(int startX, int startY, Player player, HashSet<Rectangle> obstacles) {
        super(startX, startY, obstacles);
        this.setHitBox(new Rectangle(this.getX() + this.getCHARACTER_WIDTH() / 4 - 10,
                this.getY() + 100, this.getCHARACTER_WIDTH() / 2 + 15, (this.getCHARACTER_HEIGHT() + 50)/2));

        this.setRunRightFrames(this.loadFrames(11, RUN_RIGHT_IMAGES_PATH));
        this.setRunLeftFrames(this.loadFrames(11, RUN_LEFT_IMAGES_PATH));
        this.setAttackRightFrames(this.loadFrames(4, ATTACK_RIGHT_IMAGES_PATH));
        this.setAttackLeftFrames(this.loadFrames(4, ATTACK_LEFT_IMAGES_PATH));

        this.player = player;
        this.deathFrameIndex = 0;
        this.deathRightFrames = loadFrames(2, DIE_RIGHT_IMAGES_PATH);
        this.deathLeftFrames = loadFrames(2, DIE_LEFT_IMAGES_PATH);
    }
    @Override
    public void paint(Graphics g) {
//        g.fillRect(this.x + CHARACTER_WIDTH / 4 - 10,
//                this.y + 100, CHARACTER_WIDTH / 2 + 15, (CHARACTER_HEIGHT + 50)/2);
        this.loopBetweenFrames();
        switch (this.getPaintType()) {
            case 1:
                g.drawImage(this.getRunRightFrames().get(this.getRunFrameIndex()),
                        this.getX(), this.getY(), this.getCHARACTER_WIDTH(), this.getCHARACTER_HEIGHT(), null);
                break;
            case 2:
                g.drawImage(this.getRunLeftFrames().get(this.getRunFrameIndex()),
                        this.getX(), this.getY(), this.getCHARACTER_WIDTH(), this.getCHARACTER_HEIGHT(), null);
                break;
            case 3:
                g.drawImage(this.getAttackRightFrames().get(this.getAttackFrameIndex()),
                        this.getX(), this.getY(), this.getCHARACTER_WIDTH(), this.getCHARACTER_HEIGHT(), null);
                break;
            case 4:
                g.drawImage(this.getAttackLeftFrames().get(this.getAttackFrameIndex()),
                        this.getX(), this.getY(), this.getCHARACTER_WIDTH(), this.getCHARACTER_HEIGHT(), null);
                break;
            case 5:
                for (int i = 0; i < this.deathRightFrames.size(); i++){
                    g.drawImage(this.deathRightFrames.get(i),
                            this.getX(), this.getY(), this.getCHARACTER_WIDTH(), this.getCHARACTER_HEIGHT(), null);
                }
                break;
            case 6:
                for (int i = 0; i < this.deathLeftFrames.size(); i++){
                    g.drawImage(this.deathLeftFrames.get(i),
                            this.getX(), this.getY(), this.getCHARACTER_WIDTH(), this.getCHARACTER_HEIGHT(), null);
                }
                break;
        }
    }

    public void update(){
        if (this.getX() > this.player.getX()){
            this.setCharacterMovingLeft(true);
            this.setCharacterMovingRight(false);
            this.setPaintType(MOVE_LEFT_CODE);
        } else {
            this.setCharacterMovingLeft(false);
            this.setCharacterMovingRight(true);
            this.setPaintType(MOVE_RIGHT_CODE);
        }
        if(this.getHitBox().intersects(player.getHitBox())){
            this.setCharacterAttacking(true);
            if (this.isCharacterMovingLeft()){
                this.setPaintType(ATTACK_LEFT_CODE);
            } else {
                this.setPaintType(ATTACK_RIGHT_CODE);
            }
        } else {
            this.setCharacterAttacking(false);
        }
        for (Bullet bullet : this.player.getBullets()){
            if (this.getHitBox().intersects(bullet)){
                this.setAlive(false);
                System.out.println("Dead");
                //this.player.addPoint();
            }
        }
        if (this.isAlive()){
            this.moveTowardsPlayer();
        }else {
            if (this.isCharacterMovingLeft()){
                this.setPaintType(DIE_LEFT_CODE);
            }else {
                this.setPaintType(DIE_RIGHT_CODE);
            }
        }
    }

    protected void loopBetweenFrames() {
        this.setRunFrameIndex(this.getRunFrameIndex() + 1);
        if (this.getRunFrameIndex() % 11 == 0) {
            this.setRunFrameIndex(0);
        }
        this.setAttackFrameIndex(this.getAttackFrameIndex() + 1);
        if (this.getAttackFrameIndex() % this.getAttackRightFrames().size() == 0){
            this.setAttackFrameIndex(0);
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
            if(this.getY() <= this.getLOWER_BOUNDARY_Y()){
                this.setDy(-CHARACTER_SPEED);
            }
//            if(this.y >= UPPER_BOUNDARY_Y){
//                this.dy = CHARACTER_SPEED;
//            }
            this.move();
        }

    }

    public boolean canMove(){
        int yPosition = this.getY() + this.getDy();
        boolean yPositionOk = yPosition <= this.getLOWER_BOUNDARY_Y() && yPosition >= this.getUPPER_BOUNDARY_Y();
        this.getHitBox().setLocation((int) (this.getHitBox().getX() + this.getDx()), (int) (this.getHitBox().getY() + this.getDy()));
//        this.hitBox.setBounds((int) (this.hitBox.getX() + dx),
//                (int) (this.hitBox.getY() + dy), (int) this.hitBox.getWidth(), (int) this.hitBox.getHeight());
        for (Rectangle obstacle : this.getObstacles()){
            if (this.getHitBox().intersects(obstacle)){
                System.out.println("Hit");
                return false;
            }
        }
        return yPositionOk;
    }


    public void move(){
        this.setX(this.getX() + this.getDx());
        this.setY(this.getY() + this.getDy());
    }

    public int getCHARACTER_SPEED() {
        return CHARACTER_SPEED;
    }

    public int getDeathFrameIndex() {
        return deathFrameIndex;
    }

    public void setDeathFrameIndex(int deathFrameIndex) {
        this.deathFrameIndex = deathFrameIndex;
    }
}

