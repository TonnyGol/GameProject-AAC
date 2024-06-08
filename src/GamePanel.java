import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

public class GamePanel extends JPanel {
    private final String GAME_BG_FILE_PATH = "resources\\Images\\gameBackground.png";

    private final int FPS = 24;
    private HashSet<Rectangle> obstacles;
    private final Image gameBackgroundImage;
    private Player player;
    private Enemy enemy;


    public GamePanel(int width, int height) {
        this.obstacles = createObstacles(3);
        this.setLayout(null);
        this.setBounds(WindowFrame.DEFAULT_POSITION, WindowFrame.DEFAULT_POSITION, width, height);
        this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        this.player = new Player(10, 620, this.obstacles);
        this.enemy = new Enemy(1000, 620, this.player, this.obstacles);
        this.gameBackgroundImage = new ImageIcon(GAME_BG_FILE_PATH).getImage();
        this.addKeyListener(new GameKeyListener(this, this.player));
        this.addMouseListener(new GameMouseListener(this, this.player));
        this.mainGamePanelLoop();
        //this.EnemiesMovementMainLoop();
    }
    private HashSet<Rectangle> createObstacles(int count){
        HashSet<Rectangle> obstacles = new HashSet<>(count);
        obstacles.add(new Rectangle(500, 453, 60, 5));
        obstacles.add(new Rectangle(945, 558, 140, 5));
        obstacles.add(new Rectangle(1510, 400, 55, 5));
        return obstacles;
    }

    private void update(){
        this.player.update();
        this.enemy.update();
        //System.out.println("Character x: " + this.character.getX());
        //System.out.println("Character y: " + this.character.getY());
    }

//    private void enemyUpdate(){
//        this.enemy.update();
//    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.gameBackgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        g.fillRect(500, 453, 60, 5);
        g.fillRect(945, 558, 140, 5);
        g.fillRect(1510, 400, 55, 5);
        this.player.paint(g);
        this.enemy.paint(g);
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
//    private void EnemiesMovementMainLoop(){
//        new Thread(()->{
//            enemyUpdate();
//        }).start();
//
//    }

}
