import javax.swing.*;
import java.awt.*;

public class InstructionsPanel extends JPanel {
    private int width;
    private int height;
    private final String MENU_BG_FILE_NAME = "Menu_background.png";
    private final Image backgroundImage;

    public InstructionsPanel (int width, int height) {
        this.width = width;
        this.height = height;
        this.setBounds(WindowFrame.DEFAULT_POSITION, WindowFrame.DEFAULT_POSITION, this.width, this.height);
        this.backgroundImage = new ImageIcon("src\\Images\\"+MENU_BG_FILE_NAME).getImage();
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
