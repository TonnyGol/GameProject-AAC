import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GamePanel extends JPanel {
    private final String GAME_BG_FILE_PATH = "resources\\Images\\gameBackground.png";

    private final int FPS = 24;
    private HashSet<Rectangle> obstacles;
    private final Image gameBackgroundImage;
    private Character character;


    public GamePanel(int width, int height) {
        this.obstacles = createObstacles(3);
        this.setLayout(null);
        this.setBounds(WindowFrame.DEFAULT_POSITION, WindowFrame.DEFAULT_POSITION, width, height);
        this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        this.character = new Character(10, 620, this.obstacles);
        this.gameBackgroundImage = new ImageIcon(GAME_BG_FILE_PATH).getImage();
        this.addKeyListener(new GameKeyListener(this, this.character));
        this.addMouseListener(new GameMouseListener(this, this.character));
        this.mainGamePanelLoop();
    }
    private HashSet<Rectangle> createObstacles(int count){
        HashSet<Rectangle> obstacles = new HashSet<>(count);
        obstacles.add(new Rectangle(500, 453, 60, 5));
        obstacles.add(new Rectangle(945, 558, 140, 5));
        obstacles.add(new Rectangle(1510, 400, 55, 5));
        return obstacles;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.gameBackgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        this.character.paint(g);
    }

    private void update(){
        this.character.update();
        System.out.println("Character x: " + this.character.getX());
        System.out.println("Character y: " + this.character.getY());
    }

    private void mainGamePanelLoop() {
        new Thread(() -> {
//            double drawInterval = (double) 1000000000 / FPS;
//            double nextDrawTime = System.nanoTime() + drawInterval;
            while (true) {
                if (WindowFrame.panelChoice == 1) {
                    update();
                    repaint();
//                    double remainingTime = nextDrawTime - System.nanoTime();
//                    remainingTime = remainingTime / 1000000;
//                    if (remainingTime < 0){
//                        remainingTime = 0;
//                    }
//                    Main.sleep((long) remainingTime);
//                    nextDrawTime += drawInterval;
                }
            }
        }).start();
    }


}
