import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InstructionsPanel extends JPanel {
    private final String MENU_BG_FILE_PATH = "resources\\Images\\Menu_background.png";
    private final String INSTRUCTION_FILE_PATH = "resources\\Images\\Instruction2.png";
    private final String BACK_FILE_PATH = "resources\\Images\\BackButton2.png";
    private final String MOVEMENT_FILE_PATH = "resources\\Images\\Run";
    private final Image backgroundImage;
    private final Image instructions;

    private int frameCount;


    private List<Image> movmentFrames;
    private JLabel backLabel;

    public InstructionsPanel (int width, int height) {
        this.setLayout(null);
        this.setBounds(WindowFrame.DEFAULT_POSITION, WindowFrame.DEFAULT_POSITION, width, height);
        this.frameCount = 0;
        this.backgroundImage = new ImageIcon(MENU_BG_FILE_PATH).getImage();
        this.instructions= new ImageIcon(INSTRUCTION_FILE_PATH).getImage();
        this.movmentFrames = new ArrayList<>();
        this.loadMovementFrames(MOVEMENT_FILE_PATH);
        this.backLabel = WindowFrame.createPhotoLabel("BACK",BACK_FILE_PATH);
        this.backLabel.setBounds(50,850,450,150);
        this.add(backLabel);

        this.mainInstructionPanelLoop();

    }

    private void loadMovementFrames(String fileName){
        for (int i = 1; i <= 8; i++){
            Image frame = new ImageIcon(fileName+i+".png").getImage();
            this.movmentFrames.add(frame);
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(this.backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        g.drawImage(this.instructions, this.getWidth()/15-170, this.getHeight()/10-10,
                this.getWidth()/2+15, this.getHeight()/2, this);

        g.drawImage(this.movmentFrames.get(frameCount), this.getWidth()/8 -170 ,this.getHeight()/5 - 220,
                this.getWidth()/7-20, this.getHeight()/7-20, this);
    }
    private void update(){
        this.frameCount++;
        if (this.frameCount % 8 == 0){
            this.frameCount = 0;
        }
        Main.sleep(80);
    }

    private void mainInstructionPanelLoop(){
        new Thread(() ->{
            while (true){
                if (WindowFrame.panelChoice == 2){
                    update();
                    repaint();
                }
            }
        }).start();
    }
}
