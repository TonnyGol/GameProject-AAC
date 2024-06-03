import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Character {
    private final int CHARACTER_WIDTH = 200;
    private final int CHARACTER_HEIGHT = 250;
    private final int CHARACTER_SPEED = 15;
    private final String DEFAULT_FRAME_FILE_PATH = "resources\\Images\\Stand.png";
    private final String RUN_IMAGES_PATH = "resources\\Images\\Run";
    private final String RUN_BACK_IMAGES_PATH = "resources\\Images\\RunBack";
    private final String SHOOT_IMAGES_PATH = "resources\\Images\\Shoot";

    private int x;
    private int y;
    private int dx;
    private int dy;
    private int runFrameIndex;
    private int shootFrameIndex;

    private Image defaultFrame;
    private List<Image> runFrames;
    private List<Image> runBackFrames;
    private List<Image> shootFrames;

    public Character(int startX, int startY){
        this.x = startX;
        this.y = startY;
        this.dx = 0;
        this.dy = 0;
        this.runFrameIndex = 0;
        this.defaultFrame =  new ImageIcon(DEFAULT_FRAME_FILE_PATH).getImage();
        this.runFrames = loadFrames(8, RUN_IMAGES_PATH);
        this.runBackFrames = loadFrames(8, RUN_BACK_IMAGES_PATH);
        this.shootFrames = loadFrames(4, SHOOT_IMAGES_PATH);
    }
    public void paint(Graphics g, int paintType){
        switch (paintType){
            case 1:
                g.drawImage(this.runFrames.get(this.runFrameIndex), this.x, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT,null);
                break;
            case 2:
                g.drawImage(this.runBackFrames.get(this.runFrameIndex), this.x, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT,null);
                break;
            case 3:
                g.drawImage(this.shootFrames.get(this.shootFrameIndex), this.x, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT,null);
                break;
            case 0:
                g.drawImage(this.defaultFrame,this.x,this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT,null);
                break;
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
}
