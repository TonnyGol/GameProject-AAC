import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuPanel extends JPanel {

    private final int DEFAULT_POSITION = 0;
    private final int BUTTON_MARGIN = 80;
    private final int BUTTON_FONT_SIZE = 40;
    private final String BUTTON_BG_FILE_NAME = "Button_BG.png";
    private final String MENU_BG_FILE_NAME = "Menu_background.png";

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

        this.backgroundImage = new ImageIcon("src\\Images\\"+MENU_BG_FILE_NAME).getImage();

        // Create labels for each menu option with icons
        this.playLabel = WindowFrame.createPhotoLabel("Play", BUTTON_BG_FILE_NAME, BUTTON_FONT_SIZE);
        this.instructionsLabel = WindowFrame.createPhotoLabel("Instructions", BUTTON_BG_FILE_NAME, BUTTON_FONT_SIZE);
        this.settingsLabel = WindowFrame.createPhotoLabel("Settings", BUTTON_BG_FILE_NAME, BUTTON_FONT_SIZE);
        this.quitLabel = WindowFrame.createPhotoLabel("Quit", BUTTON_BG_FILE_NAME, BUTTON_FONT_SIZE);

        // Add labels to the panel with some spacing
        int buttonWidthMargin = (width / 4) + 30;
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
        g.drawImage(this.backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
