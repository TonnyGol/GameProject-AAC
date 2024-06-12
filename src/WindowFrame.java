import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WindowFrame extends JFrame {
    public static final int DEFAULT_POSITION = 0;

    private final int WIDTH = 1920;
    private final int HEIGHT = 1080;

    public static boolean switchPanels;
    public static int panelChoice;

    private GamePanel gamePanel;
    private MenuPanel menu;
    private InstructionsPanel instructionsPanel;
    private SettingsPanel settingsPanel;
    private PausePanel pausePanel;
    private List<JPanel> panels;

    private MusicPlayer musicPlayer;
    private ButtonListener buttonListener;

    public WindowFrame(){
        this.setTitle("Soldier Survival Game");
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.panels = new ArrayList<>();
        this.musicPlayer = new MusicPlayer();
        this.musicPlayer.setVolumeSoundFx(0.3f);
        this.musicPlayer.setVolumeBackgroundMusic(0.5f);
        this.buttonListener = new ButtonListener(this.musicPlayer);

        this.menu = new MenuPanel(WIDTH, HEIGHT, this.buttonListener); // 0 index in the list
        this.add(this.menu);
        this.panels.add(this.menu);

        this.gamePanel = new GamePanel(WIDTH, HEIGHT, this.musicPlayer); // 1 index in the list
        this.add(gamePanel);
        this.panels.add(this.gamePanel);

        this.instructionsPanel = new InstructionsPanel(WIDTH, HEIGHT, this.buttonListener); // 2 index in the list
        this.add(this.instructionsPanel);
        this.panels.add(this.instructionsPanel);

        this.settingsPanel = new SettingsPanel(WIDTH,HEIGHT, this.musicPlayer, this.buttonListener);  // 3 index in the list
        this.add(this.settingsPanel);
        this.panels.add(this.settingsPanel);

        this.showOnlyOnePanel();
        this.mainWindowLoop();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void showOnlyOnePanel(){
        for (JPanel panel : this.panels){
            panel.setVisible(WindowFrame.panelChoice == this.panels.indexOf(panel));
            panel.setFocusable(WindowFrame.panelChoice == this.panels.indexOf(panel));
            panel.requestFocus();
        }
        WindowFrame.switchPanels = false;
    }
    private void mainWindowLoop(){
        new Thread(() -> {
            while(true){
                if (WindowFrame.switchPanels){
                    this.showOnlyOnePanel();
                }
            }
        }).start();
    }
}


