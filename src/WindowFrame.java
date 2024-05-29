import javax.swing.*;
import java.awt.*;

public class WindowFrame extends JFrame {
    public static final int DEFAULT_POSITION = 0;

    private final int WIDTH = 1920;
    private final int HEIGHT = 1080;
    public static boolean gameRuns;
    public static boolean instructionsRun;

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



        this.gamePanel = new GamePanel(WIDTH, HEIGHT);
        this.add(gamePanel);

        this.instructionsPanel = new InstructionsPanel(WIDTH, HEIGHT);
        this.add(this.instructionsPanel);

        this.mainGameLoop();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    public static JLabel createPhotoLabel(String text,String fileName,int fontSize) {
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

    private void mainGameLoop (){
        new Thread(() -> {
            while(true){
                if (WindowFrame.gameRuns){
                    this.gamePanel.setFocusable(true);
                    this.gamePanel.setVisible(true);
                }
                if(WindowFrame.instructionsRun){
                    this.instructionsPanel.setFocusable(true);
                    this.instructionsPanel.setVisible(true);
                }
            }

        }).start();
    }



    }


