import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InstructionsPanel extends JPanel {
    private final String MENU_BG_FILE_NAME = "Menu_background.png";
    private final String INSTRUCTION_FILE_NAME = "Instructions.png";
    private final String BACK_ARROW_FILE_NAME = "backarrow.png";
    private final Image backgroundImage;
    private final Image instructions;

    public static int frameCount;

    private int width;
    private int height;
    private List<Image> movmentFrames;
    private JLabel backLabel;

    public InstructionsPanel (int width, int height) {
        this.width = width;
        this.height = height;
        this.setLayout(null);
        this.setBounds(WindowFrame.DEFAULT_POSITION, WindowFrame.DEFAULT_POSITION, this.width, this.height);
        this.backgroundImage = new ImageIcon("src\\Images\\"+MENU_BG_FILE_NAME).getImage();
        this.instructions= new ImageIcon("src\\Images\\"+INSTRUCTION_FILE_NAME).getImage();
        this.movmentFrames = new ArrayList<>();
        this.loadMovementFrames();
        frameCount = 0;
        this.backLabel =  WindowFrame.createPhotoLabel("BackLabel",BACK_ARROW_FILE_NAME,0);
        this.backLabel.setBounds(5,850,150,150);
        this.add(backLabel);

        this.mainInstructionPanelLoop();

    }

    private void loadMovementFrames(){
        for (int i = 1; i <= 8; i++){
            Image frame = new ImageIcon("src\\Images\\Run"+i+".png").getImage();
            this.movmentFrames.add(frame);
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        InstructionsPanel.frameCount++;
        if (InstructionsPanel.frameCount % 8 == 0){
            InstructionsPanel.frameCount = 0;
        }
        Main.sleep(80);
        g.drawImage(this.backgroundImage, 0, 0, getWidth(), getHeight(), this);
        g.drawImage(this.instructions, this.width/4, this.height/5, getWidth()/2, getHeight()/2, this);
        g.drawImage(this.movmentFrames.get(frameCount), this.width/4 + 70,this.height/5 - 110, getWidth()/8, getHeight()/8, this);
    }

    private void mainInstructionPanelLoop(){
        new Thread(() ->{
            while (true){
                if (WindowFrame.panelChoice == 2){
                    repaint();
                }
            }
        }).start();
    }
}
