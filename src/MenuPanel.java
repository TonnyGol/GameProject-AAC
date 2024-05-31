import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuPanel extends JPanel {

    private final int DEFAULT_POSITION = 0;
    private final int BUTTON_MARGIN = 40;
    private final int BUTTON_FONT_SIZE = 40;
    private final String PLAY_BUTTON_FILE_PATH = "src\\Images\\PlayButton.png";
    private final String INSTRUCTIONS_BUTTON_FILE_PATH = "src\\Images\\InstructionButton.png";
    private final String SETTINGS_BUTTON_FILE_PATH = "src\\Images\\SettingsButton.png";
    private final String QUIT_BUTTON_FILE_PATH = "src\\Images\\QuitButton.png";
    private final String MENU_BG_FILE_PATH = "src\\Images\\Menu_background.png";

    private int width;
    private int height;
    private JLabel playLabel;
    private JLabel instructionsLabel;
    private JLabel settingsLabel;
    private JLabel quitLabel;

    private final Image backgroundImage;

    public MenuPanel(int width, int height){
        this.width = width;
        this.height = height;
        this.setBounds(DEFAULT_POSITION, DEFAULT_POSITION, this.width, this.height);
        this.setFocusable(true);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        this.backgroundImage = new ImageIcon(MENU_BG_FILE_PATH).getImage();

        // Create labels for each menu option with icons
        this.playLabel = WindowFrame.createPhotoLabel("Play", PLAY_BUTTON_FILE_PATH);
        this.instructionsLabel = WindowFrame.createPhotoLabel("Instructions", INSTRUCTIONS_BUTTON_FILE_PATH);
        this.settingsLabel = WindowFrame.createPhotoLabel("Settings", SETTINGS_BUTTON_FILE_PATH);
        this.quitLabel = WindowFrame.createPhotoLabel("Quit", QUIT_BUTTON_FILE_PATH);

        // Add labels to the panel with some spacing
        int buttonWidthMargin = (width / 50) ;
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
        g.drawImage(this.backgroundImage, 0, 0, this.width, this.height, this);
    }
}
