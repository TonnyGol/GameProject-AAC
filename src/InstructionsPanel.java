import javax.swing.*;
import java.awt.*;

public class InstructionsPanel extends JPanel {
    private int width;
    private int height;
    private final String MENU_BG_FILE_NAME = "Menu_background.png";
    private final String INSTRUCTION_FILE_NAME = "instructions.png";
    private final String BACK_ARROW_FILE_NAME = "backarrow.jpg";
    private final Image backgroundImage;
    private final Image instructions;
    private JLabel backLabel;

    public InstructionsPanel (int width, int height) {
        this.width = width;
        this.height = height;
        this.setLayout(null);
        this.setBounds(WindowFrame.DEFAULT_POSITION, WindowFrame.DEFAULT_POSITION, this.width, this.height);
        this.backgroundImage = new ImageIcon("src\\Images\\"+MENU_BG_FILE_NAME).getImage();
        this.instructions= new ImageIcon("src\\Images\\"+INSTRUCTION_FILE_NAME).getImage();
        this.backLabel =  WindowFrame.createPhotoLabel("BackLabel",BACK_ARROW_FILE_NAME,0);
        this.backLabel.setBounds(0,0,100,100);
        this.add(backLabel);

    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.backgroundImage, 0, 0, getWidth(), getHeight(), this);
        g.drawImage(this.instructions, this.width/5, this.height/5, getWidth()/2, getHeight()/2, this);

    }
}
