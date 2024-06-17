import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

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

    private final int FPS = 25;
    private int countTimer; // (counter/25) = counter of seconds while the game is running
    private long lastSpawnTime;
    private final int SPAWN_INTERVAL = 1000; // 1000 = 1 sec between enemy spawn

    private boolean canSave;
    private File savedScores;
    private List<String> savedScoreLines;

    private Player player;
    private List<Enemy> enemies;
    private HashSet<Rectangle> obstacles;
    private Image gameBackgroundImage;
    private MusicPlayer musicPlayer;
    private WindowFrame windowFrame;

    private JLabel giveUpLabel;
    private JLabel replayLabel;

    public GamePanel(int width, int height, MusicPlayer musicPlayer, WindowFrame windowFrame) {
        this.windowFrame = windowFrame;
        this.setLayout(null);
        this.setBounds(this.windowFrame.getDEFAULT_POSITION(), this.windowFrame.getDEFAULT_POSITION(), width, height);
        this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

        this.musicPlayer = musicPlayer;

        this.canSave = true;
        this.savedScores = new File(SAVED_SCORES_FILE_PATH);
        this.savedScoreLines = loadSavedScore();

        this.lastSpawnTime = 0;
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
        this.mainGamePanelLoop();
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

    private void update(){
        this.player.update();
        for (Enemy enemy : this.enemies){
            enemy.update();
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
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.gameBackgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);

        this.player.paint(g);

        for (Enemy enemy : this.enemies){
            enemy.paint(g);
        }

        g.setFont(new Font(null, Font.PLAIN, 30));
        g.drawString("Time: " + this.countTimer / 25 + " sec", TIMER_X_POSITION, TIMER_Y_POSITION);
        g.drawString("Points: " + this.player.getPoints(), POINTS_X_POSITION, POINTS_Y_POSITION);
    }

    private void spawnEnemy(long deltaTime){
        int xStart = new Random().nextInt(this.windowFrame.getDEFAULT_POSITION() - 200, this.windowFrame.getDEFAULT_POSITION());
        int xStart2 = new Random().nextInt(this.getWidth() + 10, this.getWidth() + 200);
        int yStart = new Random().nextInt(this.player.getUPPER_BOUNDARY_Y(), this.player.getLOWER_BOUNDARY_Y());
        int xChosenStart = new Random().nextBoolean() ? xStart : xStart2;
        this.lastSpawnTime += deltaTime;

        if (this.lastSpawnTime >= SPAWN_INTERVAL) {
            Enemy newEnemy = new Enemy(xChosenStart, yStart, this.player, this.obstacles);
            this.enemies.removeIf(enemy -> !enemy.isAlive());
            this.enemies.add(newEnemy);
            this.lastSpawnTime = 0;
        }
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
            String newScore = "Time: "+this.countTimer/25+" | "+"Points: "+this.player.getPoints();
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

    private void mainGamePanelLoop(){
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
                if (this.windowFrame.getPanelChoice() == 1 && this.player.isAlive()){
                    this.spawnEnemy(deltaTime); // spawns enemies only if you in the game window
                }
                currentTimeNano = System.nanoTime();
                delta += (currentTimeNano - lastTime) / drawInterval;
                lastTime = currentTimeNano;
                if (delta >= 1) {
                    if (this.windowFrame.getPanelChoice() == 1){ // timer goes up, update happens and repaint, only when you in the game window
                        if (this.player.isAlive()){
                            this.countTimer++;
                        }
                        update();
                        repaint();
                    }
                    delta--;
                }
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
