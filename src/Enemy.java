import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Enemy {
    private final int CHARACTER_WIDTH = 200;
    private final int CHARACTER_HEIGHT = 250;
    private final int CHARACTER_SPEED = 15;

    private final String DEFAULT_FRAME_FILE_PATH = "resources\\Images\\Wolfwalk1.png";
    private final String RUN_IMAGES_PATH = "resources\\Images\\WolfWalk";
    private final String RUN_BACK_IMAGES_PATH = "resources\\Images\\RunBack";
    private final String ATTACK_IMAGES_PATH = "resources\\Images\\Attack";

    private int x;
    private int y;
    private int dx;
    private int dy;
    private int runFrameIndex;
    private int attackFrameIndex;
    private Character character;

    private Image defaultFrame;
    private java.util.List<Image> runFrames;
    private java.util.List<Image> runBackFrames;
    private List<Image> attackFrames;

    public Enemy (int startX, int startY, Character character) {
        this.x = startX;
        this.y = startY;
        this.dx = 0;
        this.dy = 0;
        this.runFrameIndex = 0;
        this.defaultFrame =  new ImageIcon(DEFAULT_FRAME_FILE_PATH).getImage();
        this.runFrames = loadFrames(11, RUN_IMAGES_PATH);
        this.runBackFrames = loadFrames(11, RUN_BACK_IMAGES_PATH);
        this.attackFrames = loadFrames(4, ATTACK_IMAGES_PATH);
        this.character = character;
    }

    private List<Image> loadFrames(int frames, String fileName){
        List<Image> frameList = new ArrayList<>(frames);
        for (int i = 1; i <= frames; i++){
            Image frame = new ImageIcon(fileName+i+".png").getImage();
            frameList.add(frame);
        }
        return frameList;
    }

    public void paint(Graphics g, int paintType) {
        switch (paintType) {
            case 1:
                g.drawImage(this.runFrames.get(this.runFrameIndex), this.x, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT, null);
                break;
            case 2:
                g.drawImage(this.runBackFrames.get(this.runFrameIndex), this.x, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT, null);
                break;
            case 3:
                g.drawImage(this.attackFrames.get(this.attackFrameIndex), this.x, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT, null);
                break;
            case 0:
                g.drawImage(this.defaultFrame,this.x,this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT,null);
                break;
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

    public void moveTowardsPlayer() {
        double dx = this.character.getDx() - x;
        double dy = this.character.getDy() - y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        dx /= distance;
        dy /= distance;
        x += dx * CHARACTER_SPEED;
    }




}
