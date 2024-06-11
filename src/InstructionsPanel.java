import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InstructionsPanel extends JPanel {
    private final String MENU_BG_FILE_PATH = "resources\\Images\\Menu_background.png";
    private final String INSTRUCTION_FILE_PATH = "resources\\Images\\Instruction2.png";
    private final String BACK_FILE_PATH = "resources\\Images\\BackButton2.png";
    private final String MOVEMENT_FILE_PATH = "resources\\Images\\Run";
    private final String FIRE_MOVEMENT_PATH = "resources\\Images\\Shoot";
    private final Image backgroundImage;
    private final Image instructions;

    private int movementFrameCount;
    private int fireFrameCount;

    private List<Image> fireFrames;
    private List<Image> movmentFrames;
    private JLabel backLabel;

    public InstructionsPanel (int width, int height) {
        this.setLayout(null);
        this.setBounds(WindowFrame.DEFAULT_POSITION, WindowFrame.DEFAULT_POSITION, width, height);
        this.movementFrameCount = 0;
        this.backgroundImage = new ImageIcon(MENU_BG_FILE_PATH).getImage();
        this.instructions= new ImageIcon(INSTRUCTION_FILE_PATH).getImage();
        this.movmentFrames = loadFrames(8, MOVEMENT_FILE_PATH);
        this.fireFrames = loadFrames(4, FIRE_MOVEMENT_PATH);
        this.backLabel = WindowFrame.createPhotoLabel("BACK",BACK_FILE_PATH);
        this.backLabel.setBounds(50,850,450,150);
        this.add(backLabel);

        this.mainInstructionPanelLoop();

    }
    private List<Image> loadFrames(int frames, String fileName){
        List<Image> frameList = new ArrayList<>(frames);
        for (int i = 1; i <= frames; i++){
            Image frame = new ImageIcon(fileName+i+".png").getImage();
            frameList.add(frame);
        }
        return frameList;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(this.backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        g.drawImage(this.instructions, this.getWidth()/15-170, this.getHeight()/10+100,
                this.getWidth()/2+15, this.getHeight()/2, this);

        g.drawImage(this.movmentFrames.get(movementFrameCount), this.getWidth()/8 -170 ,this.getHeight()/5 - 220,
                200, 250, this);
        g.drawImage(this.fireFrames.get(fireFrameCount), this.getWidth()/8 + 410 ,this.getHeight()/5 - 220,
                200, 250, this);
    }
    private void update(){
        this.movementFrameCount++;
        this.fireFrameCount++;
        if (this.movementFrameCount % 8 == 0){
            this.movementFrameCount = 0;
        }
        if (this.fireFrameCount % 4 == 0){
            this.fireFrameCount = 0;
        }
    }

    private void mainInstructionPanelLoop(){
        new Thread(() ->{
            while (true){
                update();
                repaint();
                Main.sleep(60);
            }
        }).start();
    }
}
