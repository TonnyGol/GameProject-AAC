import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuPanel extends JPanel {
    private final int DEFAULT_POSITION = 0;
    private final int BUTTON_MARGIN = 50;
    private final int BUTTON_FONT_SIZE = 30;
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
        this.playLabel = createMenuLabel("Play");
        this.instructionsLabel = createMenuLabel("Instructions");
        this.settingsLabel = createMenuLabel("Settings");
        this.quitLabel = createMenuLabel("Quit");

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

    private JLabel createMenuLabel(String text) {
        JLabel label = new JLabel(text);
        label.setIcon(new ImageIcon("src\\Images\\"+ BUTTON_BG_FILE_NAME)); // Set the icon for the label
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.CENTER);
        label.setFont(new Font(label.getFont().getName(), Font.PLAIN, BUTTON_FONT_SIZE));
        label.setForeground(Color.ORANGE);
        label.setName(text);

        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new ButtonListener());
        return label;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
