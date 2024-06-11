import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GamePanel extends JPanel {
    private final String GAME_BG_FILE_PATH = "resources\\Images\\gameBackground.png";

    private final int FPS = 25;
    private long lastSpawnTime = 0;
    private static final int SPAWN_INTERVAL = 3000;

    private final Player player;
    private List<Enemy> enemies;
    private final HashSet<Rectangle> obstacles;
    private final Image gameBackgroundImage;
    private final MusicPlayer musicPlayer;

    public GamePanel(int width, int height, MusicPlayer musicPlayer){
        this.setLayout(null);
        this.setBounds(WindowFrame.DEFAULT_POSITION, WindowFrame.DEFAULT_POSITION, width, height);
        this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        this.musicPlayer = musicPlayer;
        this.obstacles = createObstacles(3);
        this.enemies = new LinkedList<>();
        this.player = new Player(10, 620, this.obstacles);
        this.gameBackgroundImage = new ImageIcon(GAME_BG_FILE_PATH).getImage();
        this.addKeyListener(new GameKeyListener(this, this.player));
        this.addMouseListener(new GameMouseListener(this, this.player));
        this.mainGamePanelLoop();
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
        for (Enemy enemy : this.enemies){
            enemy.update();
        }
        //System.out.println("Character x: " + this.character.getX());
        //System.out.println("Character y: " + this.character.getY());
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.gameBackgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        this.player.paint(g);
        for (Enemy enemy : this.enemies){
            enemy.paint(g);
        }
    }
    private void spawnEnemy(long deltaTime){
        int xStart = new Random().nextInt(-200, 1);
        int xStart2 = new Random().nextInt(1930, 2131);
        int yStart = new Random().nextInt(this.player.getUPPER_BOUNDARY_Y(), this.player.getLOWER_BOUNDARY_Y());
        int xChosenStart = new Random().nextBoolean() ? xStart : xStart2;
        this.lastSpawnTime += deltaTime;

        if (this.lastSpawnTime >= SPAWN_INTERVAL) {
            Enemy newEnemy = new Enemy(xChosenStart, yStart, this.player, this.obstacles);
            this.enemies.add(newEnemy);
            this.enemies.removeIf(enemy -> !enemy.isAlive());
            this.lastSpawnTime = 0;
        }
    }



    private void mainGamePanelLoop() {
        new Thread(() -> {
            double drawInterval = (double) 1000000000 / FPS;
            double delta = 0;
            long lastTime = System.nanoTime();
            long currentTimeNano;

            long previousTime = System.currentTimeMillis();
            while (true) {
                long currentTimeMillis = System.currentTimeMillis();
                long deltaTime = currentTimeMillis - previousTime;
                previousTime = currentTimeMillis;
                if (WindowFrame.panelChoice == 1){
                    this.spawnEnemy(deltaTime);
                }

                currentTimeNano = System.nanoTime();
                delta += (currentTimeNano - lastTime) / drawInterval;
                lastTime = currentTimeNano;
                if (delta >= 1) {
                    update();
                    repaint();
                    delta--;
                }
            }
        }).start();
    }

    public MusicPlayer getMusicPlayer() {
        return musicPlayer;
    }
    
}
