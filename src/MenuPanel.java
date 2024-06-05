import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuPanel extends JPanel {
    private final String PLAY_BUTTON_FILE_PATH = "resources\\Images\\PlayButton2.png";
    private final String INSTRUCTIONS_BUTTON_FILE_PATH = "resources\\Images\\InstructionButton2.png";
    private final String SETTINGS_BUTTON_FILE_PATH = "resources\\Images\\SettingsButton2.png";
    private final String QUIT_BUTTON_FILE_PATH = "resources\\Images\\QuitButton2.png";
    private final String MENU_BG_FILE_PATH = "resources\\Images\\Menu_background.png";

    private final int BUTTON_MARGIN = 40;
    private JLabel playLabel;
    private JLabel instructionsLabel;
    private JLabel settingsLabel;
    private JLabel quitLabel;
    private final Image backgroundImage;

    public MenuPanel(int width, int height){
        this.setBounds(WindowFrame.DEFAULT_POSITION, WindowFrame.DEFAULT_POSITION, width, height);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.backgroundImage = new ImageIcon(MENU_BG_FILE_PATH).getImage();

        // Create labels for each menu option with icons
        this.playLabel = WindowFrame.createPhotoLabel("Play", PLAY_BUTTON_FILE_PATH);
        this.instructionsLabel = WindowFrame.createPhotoLabel("Instructions", INSTRUCTIONS_BUTTON_FILE_PATH);
        this.settingsLabel = WindowFrame.createPhotoLabel("Settings", SETTINGS_BUTTON_FILE_PATH);
        this.quitLabel = WindowFrame.createPhotoLabel("Quit", QUIT_BUTTON_FILE_PATH);

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
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
