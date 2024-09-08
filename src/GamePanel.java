import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GamePanel extends JPanel {
    private final String GAME_BG_FILE_PATH = "resources\\Images\\gameBackground.png";
    private final String REPLAY_BUTTON_FILE_PATH = "resources\\Images\\replayButton.png";
    private final String GIVEUP_BUTTON_FILE_PATH = "resources\\Images\\giveUpButton.png";
    private final String SAVED_SCORES_FILE_PATH = "resources\\Scores\\savedScore.txt";

    private final int DEFAULT_PLAYER_SPAWN_X = 500;
    private final int DEFAULT_PLAYER_SPAWN_Y = 620;
    private final int DEFAULT_OBSTACLE_HEIGHT = 5;
    private final int DEFAULT_OBSTACLE_WIDTH = 60;
    private final int TIMER_X_POSITION = 20;
    private final int TIMER_Y_POSITION = 40;
    private final int POINTS_X_POSITION = 250;
    private final int POINTS_Y_POSITION = 40;

    private int countTimer; // (counter/25) = counter of seconds while the game is running
    private final int ENEMIES_SPAWN_INTERVAL = 2000; // 2000 = 2 sec between enemy spawn

    private boolean canSave;
    private File savedScores;
    private List<String> savedScoreLines;

    private Player player;
    private List<Enemy> enemies;
    private HashSet<Rectangle> obstacles;
    private Image gameBackgroundImage;
    private MusicPlayer musicPlayer;
    private WindowFrame windowFrame;

    private Lock enemyResourceLock;
    public static Lock playerResourceLock;
    public static Lock bulletsResourceLock;

    private JLabel giveUpLabel;
    private JLabel replayLabel;

    public GamePanel(int width, int height, MusicPlayer musicPlayer, WindowFrame windowFrame) {
        this.windowFrame = windowFrame;
        this.setLayout(null);
        this.setBounds(this.windowFrame.getDEFAULT_POSITION(), this.windowFrame.getDEFAULT_POSITION(), width, height);
        this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

        this.musicPlayer = musicPlayer;

        this.enemyResourceLock = new ReentrantLock();
        playerResourceLock = new ReentrantLock();
        bulletsResourceLock = new ReentrantLock();

        this.canSave = true;
        this.savedScores = new File(SAVED_SCORES_FILE_PATH);
        this.savedScoreLines = loadSavedScore();

        this.countTimer = 0;

        this.obstacles = createObstacles(3);

        this.enemies = new LinkedList<>();
        this.player = new Player(DEFAULT_PLAYER_SPAWN_X, DEFAULT_PLAYER_SPAWN_Y, this.obstacles);

        this.gameBackgroundImage = new ImageIcon(GAME_BG_FILE_PATH).getImage();

        this.giveUpLabel = Main.createButtonLabel("GiveUp", GIVEUP_BUTTON_FILE_PATH, new ButtonListener(this.windowFrame, this));
        this.giveUpLabel.setBounds(this.getWidth()/3+50,
                this.getHeight()-600, this.getWidth()/4, this.getHeight()/6);
        this.add(giveUpLabel);
        this.giveUpLabel.setFocusable(false);
        this.giveUpLabel.setVisible(false);

        this.replayLabel = Main.createButtonLabel("Replay", REPLAY_BUTTON_FILE_PATH, new ButtonListener(this.windowFrame, this));
        this.replayLabel.setBounds(this.getWidth()/3+50,
                this.getHeight()-800, this.getWidth()/4, this.getHeight()/6);
        this.add(replayLabel);
        this.replayLabel.setFocusable(false);
        this.replayLabel.setVisible(false);

        this.addKeyListener(new GameKeyListener(this, this.player, this.windowFrame));
        this.addMouseListener(new GameMouseListener(this, this.player));

        //Threads of the game
        this.renderGamePanelLoop();
        this.spawnEnemies();
        this.updatePlayer();
        this.updateEnemies();
    }
    private HashSet<Rectangle> createObstacles(int count){
        HashSet<Rectangle> obstacles = new HashSet<>(count);
        obstacles.add(new Rectangle((this.getWidth()/4) + 20,
                (this.getHeight()/3) + 93, DEFAULT_OBSTACLE_WIDTH, DEFAULT_OBSTACLE_HEIGHT));
        obstacles.add(new Rectangle((this.getWidth()/2) - 15,
                (this.getHeight()/2) + 18, (DEFAULT_OBSTACLE_WIDTH*2) + 20, DEFAULT_OBSTACLE_HEIGHT));
        obstacles.add(new Rectangle((int) ((this.getWidth()/1.25) - 26),
                (this.getHeight()/3) + 40, DEFAULT_OBSTACLE_WIDTH - 5, DEFAULT_OBSTACLE_HEIGHT));
        return obstacles;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.gameBackgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);

        if (playerResourceLock.tryLock()) {
            try {
                g.drawImage(this.player.getCurrentFrame(),
                        this.player.getX(), this.player.getY(),
                        this.player.getCHARACTER_WIDTH(), this.player.getCHARACTER_HEIGHT(),null);
            } finally {
                playerResourceLock.unlock();
            }
        }

        if (bulletsResourceLock.tryLock()) {
            try {
                for (Bullet bullet : this.player.getBullets()){
                    g.fillRect((int) bullet.getX(), (int) bullet.getY(), (int) bullet.getWidth(), (int) bullet.getHeight());
                }
            } finally {
                bulletsResourceLock.unlock();
            }
        }

        if (enemyResourceLock.tryLock()) {
            try {
                for (Enemy enemy : this.enemies){
                    g.drawImage(enemy.getCurrentFrame(),
                            enemy.getX(), enemy.getY(),
                            enemy.getCHARACTER_WIDTH(), enemy.getCHARACTER_HEIGHT(),null);
                }
            } finally {
                enemyResourceLock.unlock();
            }
        }

        g.setFont(new Font(null, Font.PLAIN, 30));
        g.drawString("Time: " + this.countTimer / 1500000 + " sec", TIMER_X_POSITION, TIMER_Y_POSITION);
        g.drawString("Points: " + this.player.getPoints(), POINTS_X_POSITION, POINTS_Y_POSITION);

        Main.sleep(30);

    }

    private void updatePlayer(){
        new Thread(()->{
            while(true){
                if (this.windowFrame.getPanelChoice() == 1){
                    this.player.update();
//                    if (playerResourceLock.tryLock()) {
//                        try {
//
//                        } finally {
//                            playerResourceLock.unlock();
//                        }
//                    }
                }
            }
        }).start();
    }


    private void updateEnemies(){
        new Thread(()->{
            while (true){
                if (this.windowFrame.getPanelChoice() == 1){
                    if (enemyResourceLock.tryLock()) {
                        try {
                            for (Enemy enemy : this.enemies){
                                enemy.update();
                            }
                        } finally {
                            enemyResourceLock.unlock();
                        }
                    }
                    Main.sleep(30);
                }

            }
        }).start();

    }

    private void spawnEnemy(){
        int xStart = new Random().nextInt(this.windowFrame.getDEFAULT_POSITION() - 200, this.windowFrame.getDEFAULT_POSITION());
        int xStart2 = new Random().nextInt(this.getWidth() + 10, this.getWidth() + 200);
        int yStart = new Random().nextInt(this.player.getUPPER_BOUNDARY_Y(), this.player.getLOWER_BOUNDARY_Y());
        int xChosenStart = new Random().nextBoolean() ? xStart : xStart2;
        Enemy newEnemy = new Enemy(xChosenStart, yStart, this.player, this.obstacles);
        if (enemyResourceLock.tryLock()) {
            try {
                this.enemies.removeIf(enemy -> !enemy.isAlive());
                this.enemies.add(newEnemy);
            } finally {
                enemyResourceLock.unlock();
            }
        }

    }

    private void spawnEnemies(){
        new Thread(()->{
            while (true){
                if (this.windowFrame.getPanelChoice() == 1){
                    this.spawnEnemy();
                    Main.sleep(ENEMIES_SPAWN_INTERVAL);
                }

            }
        }).start();
    }

    private List<String> loadSavedScore(){
        List<String> savedScoresLines = new ArrayList<>();
        String line;
        try {
            FileReader fileReader = new FileReader(this.savedScores);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            do {
                line = bufferedReader.readLine();
                if (line != null){
                    savedScoresLines.add(line);
                }
            }while (line != null);
        } catch (IOException e) {
        }
        return savedScoresLines;
    }

    private void saveScore(){
        try {
            FileWriter fileWriter = new FileWriter(this.savedScores);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            String newScore = "Time: "+this.countTimer/1500000+" sec | "+"Points: "+this.player.getPoints();
            this.savedScoreLines.add(newScore);
            String allScores = String.join("\n", this.savedScoreLines);
            bufferedWriter.write(allScores);
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
        }
    }

    public void replay(){
        this.player.setX(DEFAULT_PLAYER_SPAWN_X);
        this.player.setY(DEFAULT_PLAYER_SPAWN_Y);
        this.player.setAlive(true);
        this.canSave = true;
        this.giveUpLabel.setFocusable(false);
        this.giveUpLabel.setVisible(false);
        this.replayLabel.setFocusable(false);
        this.replayLabel.setVisible(false);
    }

    private void renderGamePanelLoop(){
        new Thread(() -> {
            while (true) {
                if (this.windowFrame.getPanelChoice() == 1){
                    if (player.isAlive()){
                        this.countTimer++;
                    }
                }

                if(!this.player.isAlive()) {
                    this.giveUpLabel.setFocusable(true);
                    this.giveUpLabel.setVisible(true);
                    this.replayLabel.setFocusable(true);
                    this.replayLabel.setVisible(true);
                    if (this.canSave){
                        this.saveScore();
                        this.canSave = false;
                    }
                }
                repaint();
            }
        }).start();
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public Player getPlayer() {
        return player;
    }

    public void setCountTimer(int countTimer) {
        this.countTimer = countTimer;
    }

    public MusicPlayer getMusicPlayer() {
        return musicPlayer;
    }
}
