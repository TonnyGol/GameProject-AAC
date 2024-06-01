import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GamePanel extends JPanel {
    private final String GAME_BG_FILE_PATH = "resources\\Images\\gameBackground.png";
    private final String DEFAULT_FRAME_FILE_PATH = "resources\\Images\\Shoot1.png";
    private int width;
    private int height;
    private final Image gameBackgroundImage;
    private final Image defualtFrame;

    public GamePanel(int width, int height) {
        this.width = width;
        this.height = height;
        this.setLayout(null);
        this.setBounds(WindowFrame.DEFAULT_POSITION, WindowFrame.DEFAULT_POSITION, this.width, this.height);
        this.gameBackgroundImage = new ImageIcon(GAME_BG_FILE_PATH).getImage();
        this.defualtFrame = new ImageIcon(DEFAULT_FRAME_FILE_PATH).getImage();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.gameBackgroundImage, 0, 0, this.width, this.height, this);
        g.drawImage(this.defualtFrame,10,700,200,250,this);
    }

    private void mainGamePanelLoop() {
        new Thread(() -> {
            while (true) {
                if (WindowFrame.panelChoice == 1) {
                    repaint();
                }
            }
        }).start();
    }
}
