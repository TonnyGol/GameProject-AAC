import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private final String PLAY_BUTTON_FILE_PATH = "resources\\Images\\PlayButton2.png";
    private final String INSTRUCTIONS_BUTTON_FILE_PATH = "resources\\Images\\InstructionButton2.png";
    private final String SETTINGS_BUTTON_FILE_PATH = "resources\\Images\\SettingsButton2.png";
    private final String QUIT_BUTTON_FILE_PATH = "resources\\Images\\QuitButton2.png";
    private final String MENU_BG_FILE_PATH = "resources\\Images\\Menu_background.png";

    private final int BUTTON_MARGIN = 40;
    private final JLabel playLabel;
    private final JLabel instructionsLabel;
    private final JLabel settingsLabel;
    private final JLabel quitLabel;
    private final Image backgroundImage;

    private final ButtonListener buttonListener;

    public MenuPanel(int width, int height, ButtonListener buttonListener){
        this.setBounds(WindowFrame.DEFAULT_POSITION, WindowFrame.DEFAULT_POSITION, width, height);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.backgroundImage = new ImageIcon(MENU_BG_FILE_PATH).getImage();
        this.buttonListener = buttonListener;

        // Create labels for each menu option with icons
        this.playLabel = Main.createButtonLabel("Play", PLAY_BUTTON_FILE_PATH, this.buttonListener);
        this.instructionsLabel = Main.createButtonLabel("Instructions", INSTRUCTIONS_BUTTON_FILE_PATH, this.buttonListener);
        this.settingsLabel = Main.createButtonLabel("Settings", SETTINGS_BUTTON_FILE_PATH, this.buttonListener);
        this.quitLabel = Main.createButtonLabel("Quit", QUIT_BUTTON_FILE_PATH, this.buttonListener);

        // Add labels to the panel with some spacing
        int buttonWidthMargin = (width / 50);
        this.add(Box.createRigidArea(new Dimension(buttonWidthMargin, BUTTON_MARGIN)));
        this.add(playLabel);
        this.add(Box.createRigidArea(new Dimension(buttonWidthMargin, BUTTON_MARGIN)));
        this.add(instructionsLabel);
        this.add(Box.createRigidArea(new Dimension(buttonWidthMargin, BUTTON_MARGIN)));
        this.add(settingsLabel);
        this.add(Box.createRigidArea(new Dimension(buttonWidthMargin, BUTTON_MARGIN)));
        this.add(quitLabel);

        this.menuLoop();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
    }

    private void menuLoop(){
        new Thread(() ->{
            if (WindowFrame.panelChoice == 0){
                repaint();
            }
        }).start();
    }
}
