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

    private File savedScores;
    private String savedScoreString;
    private boolean canSave;
    private final int FPS = 25;
    private int countTimer; // counter of seconds while the game is running
    private long lastSpawnTime;
    private static final int SPAWN_INTERVAL = 1000; // 1 sec between enemy spawn

    private final int DEFAULT_PLAYER_SPAWN_X = 500;
    private final int DEFAULT_PLAYER_SPAWN_Y = 620;
    private final int DEFAULT_OBSTACLE_HEIGHT = 5;
    private final int DEFAULT_OBSTACLE_WIDTH = 60;
    private final int TIMER_X_POSITION = WindowFrame.DEFAULT_POSITION + 20;
    private final int TIMER_Y_POSITION = WindowFrame.DEFAULT_POSITION + 40;
    private final int POINTS_X_POSITION = WindowFrame.DEFAULT_POSITION + 250;
    private final int POINTS_Y_POSITION = WindowFrame.DEFAULT_POSITION + 40;

    private final Player player;
    private List<Enemy> enemies;
    private final HashSet<Rectangle> obstacles;
    private final Image gameBackgroundImage;
    private final MusicPlayer musicPlayer;

    private final JLabel giveUpLabel;
    private final JLabel replayLabel;

    public GamePanel(int width, int height, MusicPlayer musicPlayer) {
        this.setLayout(null);
        this.setBounds(WindowFrame.DEFAULT_POSITION, WindowFrame.DEFAULT_POSITION, width, height);
        this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        this.savedScores = new File(SAVED_SCORES_FILE_PATH);
        this.savedScoreString = loadSavedScore();
        this.canSave = true;
        this.musicPlayer = musicPlayer;
        this.obstacles = createObstacles(3);
        this.lastSpawnTime = 0;
        this.countTimer = 0;
        this.enemies = new LinkedList<>();
        this.player = new Player(DEFAULT_PLAYER_SPAWN_X, DEFAULT_PLAYER_SPAWN_Y, this.obstacles);
        this.gameBackgroundImage = new ImageIcon(GAME_BG_FILE_PATH).getImage();

        this.giveUpLabel = Main.createButtonLabel("GiveUp", GIVEUP_BUTTON_FILE_PATH, new ButtonListener(this.musicPlayer, this));
        this.giveUpLabel.setBounds(this.getWidth()/3+50,
                this.getHeight()-600, this.getWidth()/4, this.getHeight()/6);
        this.add(giveUpLabel);
        this.giveUpLabel.setFocusable(false);
        this.giveUpLabel.setVisible(false);
        this.replayLabel = Main.createButtonLabel("Replay", REPLAY_BUTTON_FILE_PATH, new ButtonListener(this.musicPlayer, this));
        this.replayLabel.setBounds(this.getWidth()/3+50,
                this.getHeight()-800, this.getWidth()/4, this.getHeight()/6);
        this.add(replayLabel);
        this.replayLabel.setFocusable(false);
        this.replayLabel.setVisible(false);

        this.addKeyListener(new GameKeyListener(this, this.player));
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
        //System.out.println("Character x: " + this.character.getX());
        //System.out.println("Character y: " + this.character.getY());
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.gameBackgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        g.setFont(new Font(null, Font.PLAIN, 30));
        g.drawString("Time: " + this.countTimer / 25 + " sec", TIMER_X_POSITION, TIMER_Y_POSITION);
        g.drawString("Points: " + this.player.getPoints(), POINTS_X_POSITION, POINTS_Y_POSITION);
        this.player.paint(g);
        for (Enemy enemy : this.enemies){
            enemy.paint(g);
        }
    }
    private void spawnEnemy(long deltaTime){
        int xStart = new Random().nextInt(WindowFrame.DEFAULT_POSITION - 200, WindowFrame.DEFAULT_POSITION);
        int xStart2 = new Random().nextInt(this.getWidth() + 10, this.getWidth() + 200);
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

    private String loadSavedScore(){
        String scores = "";
        try {
            FileReader fileReader = new FileReader(this.savedScores);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while (bufferedReader.readLine() != null){
                scores += bufferedReader.readLine();
            }
            return scores;
        } catch (IOException e) {
        }
        return scores;
    }

    private void saveScore(){
        try {
            FileWriter fileWriter = new FileWriter(this.savedScores);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            this.savedScoreString += "Time: "+this.countTimer/25+" | "+"Points: "+this.player.getPoints()+"\n";
            bufferedWriter.write(this.savedScoreString);
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

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public Player getPlayer() {
        return player;
    }

    public void setCountTimer(int countTimer) {
        this.countTimer = countTimer;
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
                if (WindowFrame.panelChoice == 1 && this.player.isAlive()){
                    this.spawnEnemy(deltaTime); // spawns enemies only if you in the game window
                }
                currentTimeNano = System.nanoTime();
                delta += (currentTimeNano - lastTime) / drawInterval;
                lastTime = currentTimeNano;
                if (delta >= 1) {
                    if (WindowFrame.panelChoice == 1){ // timer goes up, update happens and repaint, only when you in the game window
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

    public MusicPlayer getMusicPlayer() {
        return musicPlayer;
    }
    
}
