import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private final String GAME_BG_FILE_PATH = "resources\\Images\\gameBackground.png";
    private int paintType;
    private int width;
    private int height;
    private final Image gameBackgroundImage;

    private Character character;
    private final int STAND_BACK_CODE = -1;
    private boolean isCharacterMoving;
    private final int MOVE_CODE = 1;
    private boolean isCharacterMovingBack;
    private final int MOVE_BACK_CODE = 2;
    private boolean isCharacterShooting;
    private final int SHOOT_CODE = 3;
    private final int SHOOT_BACK_CODE = 4;

    public GamePanel(int width, int height) {
        this.paintType = 0;
        this.width = width;
        this.height = height;
        this.setLayout(null);
        this.setBounds(WindowFrame.DEFAULT_POSITION, WindowFrame.DEFAULT_POSITION, this.width, this.height);
        this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        this.character = new Character(10, 620,this.width,this.height);
        this.gameBackgroundImage = new ImageIcon(GAME_BG_FILE_PATH).getImage();
        this.addKeyListener(new GameKeyListener(this, this.character));
        this.addMouseListener(new GameMouseListener(this, this.character));
        this.mainGamePanelLoop();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.gameBackgroundImage, 0, 0, this.width, this.height, this);

        this.character.paint(g, paintType);
        this.paintType = 0;
    }
    
    private void loopBetweenFrames(){
        if (this.isCharacterMoving){
            this.character.setRunFrameIndex(this.character.getRunFrameIndex() + 1);
            if (this.character.getRunFrameIndex() % 8 == 0){
                this.character.setRunFrameIndex(0);
            }
        }
        if (this.isCharacterShooting){
            this.character.setShootFrameIndex(this.character.getShootFrameIndex() + 1);
            if (this.character.getShootFrameIndex() % 4 == 0){
                this.character.setShootFrameIndex(0);
            }
        }
    }

    private void update(){
        if (this.isCharacterMoving){
            if(this.character.canMove()){
                this.character.move();
                if (this.isCharacterMovingBack){
                    this.paintType = MOVE_BACK_CODE;
                }else{
                    this.paintType = MOVE_CODE;
                }
                Main.sleep(40);
                this.loopBetweenFrames();
            }
        }else if (this.isCharacterShooting) {
            if (this.isCharacterMovingBack){
                this.paintType = SHOOT_BACK_CODE;
            }else {
                this.paintType = SHOOT_CODE;
            }
            Main.sleep(40);
            this.loopBetweenFrames();
        } else {
            if (this.isCharacterMovingBack){
                this.paintType = STAND_BACK_CODE;
            }
        }
        System.out.println("Character x: " + this.character.getX());
        System.out.println("Character y: " + this.character.getY());
    }

    private void mainGamePanelLoop() {
        new Thread(() -> {
            while (true) {
                if (WindowFrame.panelChoice == 1) {
                    update();
                    repaint();
                }
            }
        }).start();
    }

    public boolean isCharacterMoving() {
        return isCharacterMoving;
    }

    public void setCharacterMoving(boolean characterMoving) {
        isCharacterMoving = characterMoving;
    }

    public boolean isCharacterMovingBack() {
        return isCharacterMovingBack;
    }

    public void setCharacterMovingBack(boolean characterMovingBack) {
        isCharacterMovingBack = characterMovingBack;
    }

    public void setCharacterShooting(boolean characterShooting) {
        isCharacterShooting = characterShooting;
    }
}
