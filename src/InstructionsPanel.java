import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InstructionsPanel extends JPanel {
    private final String MENU_BG_FILE_PATH = "src\\Images\\Menu_background.png";
    private final String INSTRUCTION_FILE_PATH = "src\\Images\\Instruction2.png";
    private final String BACK_FILE_PATH = "src\\Images\\BackButton2.png";
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
        this.backgroundImage = new ImageIcon(MENU_BG_FILE_PATH).getImage();
        this.instructions= new ImageIcon(INSTRUCTION_FILE_PATH).getImage();
        this.movmentFrames = new ArrayList<>();
        this.loadMovementFrames();
        frameCount = 0;
        this.backLabel =  WindowFrame.createPhotoLabel("BACK",BACK_FILE_PATH);
        this.backLabel.setBounds(50,850,450,150);
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
        g.drawImage(this.backgroundImage, 0, 0, this.width, this.height, this);
        g.drawImage(this.instructions, this.width/5, this.height/5, getWidth()/2, getHeight()/2, this);
        g.drawImage(this.movmentFrames.get(frameCount), this.width/4 + 10,this.height/5 - 150, getWidth()/7, getHeight()/7, this);
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
