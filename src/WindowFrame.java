import javax.swing.*;

public class WindowFrame extends JFrame {
    public static final int DEFAULT_POSITION = 0;

    private final int WIDTH = 1920;
    private final int HEIGHT = 1080;
    public static boolean gameRuns;

    private GamePanel gamePanel;
    private MenuPanel menu;
    private InstructionsPanel instructionsPanel;
    private SettingsPanel settingsPanel;
    private PausePanel pausePanel;

    public WindowFrame(){
        this.setTitle("Soldier Survival Game");
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);


        this.menu = new MenuPanel(WIDTH, HEIGHT);
        this.add(this.menu);

        this.instructionsPanel = new InstructionsPanel(WIDTH, HEIGHT);
        this.add(this.instructionsPanel);

        this.gamePanel = new GamePanel(WIDTH, HEIGHT);
        this.add(gamePanel);

        this.mainGameLoop();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void mainGameLoop (){
        new Thread(() -> {
            if (this.gameRuns){
                this.gamePanel.setFocusable(true);
                this.gamePanel.setVisible(true);
            }
        }).start();
    }

}
