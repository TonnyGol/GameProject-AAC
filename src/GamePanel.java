import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {
    private final String GAME_BG_FILE_PATH = "resources\\Images\\gameBackground.png";

    private int paintType;
    private int paintTypeEnemy;
    private int width;
    private int height;
    private final Image gameBackgroundImage;

    private Character character;
    private boolean isCharacterMoving;
    private boolean isEnemyMoving;
    private final int MOVE_CODE = 1;
    private boolean isCharacterMovingBack;
    private final int MOVE_BACK_CODE = 2;
    private boolean isCharacterShooting;
    private final int SHOOT_CODE = 3;
    private List<Enemy> enemies;

    public GamePanel(int width, int height) {
        this.paintType = 0;
        this.width = width;
        this.height = height;
        this.setLayout(null);
        this.setBounds(WindowFrame.DEFAULT_POSITION, WindowFrame.DEFAULT_POSITION, this.width, this.height);
        this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        this.character = new Character(10, 700);
        Enemy enemy = new Enemy(800, 700, character);
        this.gameBackgroundImage = new ImageIcon(GAME_BG_FILE_PATH).getImage();
        this.addKeyListener(new GameKeyListener(this, this.character));
        this.addMouseListener(new GameMouseListener(this, this.character));
        this.mainGamePanelLoop();
        this.enemies = new ArrayList<>();
        this.enemies.add(enemy);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.gameBackgroundImage, 0, 0, this.width, this.height, this);
        int paintType = 0;
        int paintTypeEnemy = 0;
        g.fillRect(245, 580, 85, 95);

        this.character.paint(g, paintType);
        this.paintType = 0;
        this.enemies.get(0).paint(g, paintTypeEnemy);
        this.enemies.get(0).moveTowardsPlayer();
        this.paintTypeEnemy = 0;
        Main.sleep(50);
        loopBetweenFrames();

//        for (int i = 0; i < this.enemies.size(); i++){
//            this.enemies.get(i).paint(g, enemyPaintType);
//            this.enemies.get(i).moveTowardsPlayer();
//            Main.sleep(100);
//            loopBetweenFrames();
//        }
    }

        private void update() {
//            if (this.isCharacterMoving){
//                if(this.character.canMove()){
//                    this.character.move();
//                    if (this.isCharacterMovingBack){
//                        this.paintType = MOVE_BACK_CODE;
//                    }else{
//                        this.paintType = MOVE_CODE;
//                    }
//                    Main.sleep(40);
//                    this.loopBetweenFrames();
//                }
//            }else if (this.isCharacterShooting) {
//                if (this.isCharacterMovingBack){
//                    this.paintType = SHOOT_BACK_CODE;
//                }else {
//                    this.paintType = SHOOT_CODE;
//                }
//                Main.sleep(40);
//                this.loopBetweenFrames();
//            } else {
//                if (this.isCharacterMovingBack){
//                    this.paintType = STAND_BACK_CODE;
//                }
        }
    
    private void loopBetweenFrames(){
        if (this.isEnemyMoving){
            this.enemies.get(0).setRunFrameIndex(this.enemies.get(0).getRunFrameIndex() + 1);
            if (this.enemies.get(0).getRunFrameIndex() % 8 == 0){
                this.enemies.get(0).setRunFrameIndex(0);
            }
        }
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

    public boolean isEnemyMoving(){
        return isEnemyMoving;
    }

    public boolean isCharacterMoving() {
        return isCharacterMoving;
    }

    public void setEnemyMoving(boolean enemyMoving){
        isEnemyMoving = enemyMoving;
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
