import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Character {
    private final String DEFAULT_FRAME_FILE_PATH = "resources\\Images\\Stand.png";
    private final String DEFAULT_BACK_FRAME_PATH = "resources\\Images\\StandBack.png";
    private final String RUN_IMAGES_PATH = "resources\\Images\\Run";
    private final String RUN_BACK_IMAGES_PATH = "resources\\Images\\RunBack";
    private final String SHOOT_IMAGES_PATH = "resources\\Images\\Shoot";
    private final String SHOO_BACK_IMAGES_PATH = "resources\\Images\\ShootBack";
    private final int CHARACTER_WIDTH = 200;
    private final int CHARACTER_HEIGHT = 250;
    private final int CHARACTER_SPEED = 15;

    private int x;
    private int y;
    private int dx;
    private int dy;
    private int runFrameIndex;
    private int shootFrameIndex;
    private int rightBoundaryX ;
    private int leftBoundaryX;
    private int upperBoundaryY;
    private int lowerBoundaryY;

    private Image defaultFrame;
    private Image defaultFrameBack;
    private List<Image> runFrames;
    private List<Image> runBackFrames;
    private List<Image> shootFrames;
    private List<Image> shootBackFrames;

    public Character(int startX, int startY, int panelWidth, int panelHeight){
        this.x = startX;
        this.y = startY;
        this.dx = 0;
        this.dy = 0;
        this.rightBoundaryX = 1740;
        this.leftBoundaryX = -45;
        this.upperBoundaryY = 165;
        this.lowerBoundaryY = 790;
        this.runFrameIndex = 0;
        this.defaultFrame = new ImageIcon(DEFAULT_FRAME_FILE_PATH).getImage();
        this.defaultFrameBack = new ImageIcon(DEFAULT_BACK_FRAME_PATH).getImage();
        this.runFrames = loadFrames(8, RUN_IMAGES_PATH);
        this.runBackFrames = loadFrames(8, RUN_BACK_IMAGES_PATH);
        this.shootFrames = loadFrames(4, SHOOT_IMAGES_PATH);
        this.shootBackFrames = loadFrames(4, SHOO_BACK_IMAGES_PATH);
    }
    public void paint(Graphics g, int paintType){
        g.fillRect(this.x + CHARACTER_WIDTH / 4 - 10, this.y + 50, CHARACTER_WIDTH / 2 + 15, CHARACTER_HEIGHT - 50);
        switch (paintType){
            case 1:
                g.drawImage(this.runFrames.get(this.runFrameIndex), this.x, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT,null);
                break;
            case 2:
                g.drawImage(this.runBackFrames.get(this.runFrameIndex), this.x, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT,null);
                break;
            case 3:
                g.drawImage(this.shootFrames.get(this.shootFrameIndex), this.x + 30, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT,null);
                break;
            case 4:
                g.drawImage(this.shootBackFrames.get(this.shootFrameIndex), this.x - 30, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT,null);
                break;
            case 0:
                g.drawImage(this.defaultFrame,this.x,this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT,null);
                break;
            case -1:
                g.drawImage(this.defaultFrameBack, this.x, this.y, CHARACTER_WIDTH, CHARACTER_HEIGHT,null);
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

    public boolean canMove(){
        int xPosition = this.x + dx;
        int yPosition = this.y + dy;
        boolean xPositionOk = xPosition >= leftBoundaryX && xPosition <= rightBoundaryX;
        boolean yPositionOk = yPosition <= lowerBoundaryY && yPosition >= upperBoundaryY;
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
}
