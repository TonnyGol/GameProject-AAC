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

    public WindowFrame(){
        this.setTitle("Soldier Survival Game");
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.panels = new ArrayList<>();

        this.menu = new MenuPanel(WIDTH, HEIGHT); // 0 index in the list
        this.add(this.menu);
        this.panels.add(this.menu);

        this.gamePanel = new GamePanel(WIDTH, HEIGHT); // 1 index in the list
        this.add(gamePanel);
        this.panels.add(this.gamePanel);

        this.instructionsPanel = new InstructionsPanel(WIDTH, HEIGHT); // 2 index in the list
        this.add(this.instructionsPanel);
        this.panels.add(this.instructionsPanel);

        this.settingsPanel = new SettingsPanel(WIDTH,HEIGHT);
        this.add(this.settingsPanel);
        this.panels.add(this.settingsPanel);

        this.mainWindowLoop();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    public static JLabel createPhotoLabel(String text, String filePath) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(text,Font.PLAIN,0));
        label.setIcon(new ImageIcon(filePath)); // Set the icon for the label
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.CENTER);
        label.setForeground(Color.ORANGE);
        label.setName(text);

        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new ButtonListener());
        return label;
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


