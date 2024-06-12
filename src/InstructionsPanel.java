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
    private final int RUN_FRAME_COUNT = 8;
    private final int SHOOT_FRAME_COUNT = 4;

    private final ButtonListener buttonListener;

    private final List<Image> fireFrames;
    private final List<Image> movementFrames;
    private final JLabel backLabel;

    public InstructionsPanel (int width, int height, ButtonListener buttonListener) {
        this.setLayout(null);
        this.setBounds(WindowFrame.DEFAULT_POSITION, WindowFrame.DEFAULT_POSITION, width, height);
        this.buttonListener = buttonListener;
        this.movementFrameCount = 0;
        this.backgroundImage = new ImageIcon(MENU_BG_FILE_PATH).getImage();
        this.instructions= new ImageIcon(INSTRUCTION_FILE_PATH).getImage();
        this.movementFrames = Main.loadFrames(RUN_FRAME_COUNT, MOVEMENT_FILE_PATH);
        this.fireFrames = Main.loadFrames(SHOOT_FRAME_COUNT, FIRE_MOVEMENT_PATH);
        this.backLabel = Main.createButtonLabel("Back", BACK_FILE_PATH, this.buttonListener);
        this.backLabel.setBounds((int)(this.getWidth()/80),
                (int)(this.getHeight()/1.25), (int)(this.getWidth()/4), (int)(this.getHeight()/6));
        this.add(backLabel);

        this.mainInstructionPanelLoop();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(this.backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        g.drawImage(this.instructions, this.getWidth()/15-170, this.getHeight()/10+100,
                this.getWidth()/2+15, this.getHeight()/2, this);

        g.drawImage(this.movementFrames.get(movementFrameCount), this.getWidth()/8 -170 ,this.getHeight()/5 - 220,
                200, 250, this);
        g.drawImage(this.fireFrames.get(fireFrameCount), this.getWidth()/8 + 410 ,this.getHeight()/5 - 220,
                200, 250, this);
    }
    private void update(){
        this.movementFrameCount++;
        this.fireFrameCount++;
        if (this.movementFrameCount % this.movementFrames.size() == 0){
            this.movementFrameCount = 0;
        }
        if (this.fireFrameCount % this.fireFrames.size() == 0){
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
