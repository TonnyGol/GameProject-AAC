import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GamePanel extends JPanel {

    private int width;
    private int height;

    public GamePanel (int width, int height) {
        this.width = width;
        this.height = height;
        this.setBounds(WindowFrame.DEFAULT_POSITION, WindowFrame.DEFAULT_POSITION, this.width, this.height);
        this.setBackground(Color.DARK_GRAY);

    }

}
