import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WindowFrame extends JFrame {
    public static final int DEFAULT_POSITION = 0;

    private final int WIDTH = 1920;
    private final int HEIGHT = 1080;

    public static int panelShowNum;

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

        this.mainGameLoop();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    public static JLabel createPhotoLabel(String text, String fileName, int fontSize) {
        JLabel label = new JLabel(text);
        label.setIcon(new ImageIcon("src\\Images\\"+ fileName)); // Set the icon for the label
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.CENTER);
        label.setFont(new Font(label.getFont().getName(), Font.PLAIN, fontSize));
        label.setForeground(Color.ORANGE);
        label.setName(text);

        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new ButtonListener());
        return label;
    }

    private void showOnlyOnePanel(int panelShowNum){
        for (JPanel panel : this.panels){
            if (panelShowNum == this.panels.indexOf(panel)){
                panel.setVisible(true);
                panel.setFocusable(true);
            }else {
                panel.setVisible(false);
                panel.setFocusable(false);
            }
        }
    }

    private void mainGameLoop (){
        new Thread(() -> {
            while(true){
                this.showOnlyOnePanel(WindowFrame.panelShowNum);
                if (WindowFrame.panelShowNum == 2){
                    InstructionsPanel.frameCount++;
                    if (InstructionsPanel.frameCount % 8 == 0){
                        InstructionsPanel.frameCount = 0;
                    }
                    this.repaint();
                    Main.sleep(80);
                }
            }
        }).start();
    }
}


