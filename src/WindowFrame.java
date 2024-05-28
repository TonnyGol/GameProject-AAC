import javax.swing.*;

public class WindowFrame extends JFrame {
    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;

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


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
