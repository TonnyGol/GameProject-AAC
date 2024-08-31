import javax.swing.*;
import java.awt.*;
import java.util.List;

public class InstructionsPanel extends JPanel {
    private final String MENU_BG_FILE_PATH = "resources\\Images\\Menu_background.png";
    private final String INSTRUCTION_FILE_PATH = "resources\\Images\\Instruction2.png";
    private final String BACK_FILE_PATH = "resources\\Images\\BackButton2.png";
    private final String MOVEMENT_FILE_PATH = "resources\\Images\\Run";
    private final String FIRE_MOVEMENT_PATH = "resources\\Images\\Shoot";
    private final String ATTACK_MOVEMENT_PATH = "resources\\Images\\Attack";

    private final int PLAYER_ANIMATION_WIDTH = 200;
    private final int PLAYER_ANIMATION_HEIGHT = 250;

    private final int ATTACK_FRAME_COUNT = 3;
    private final int RUN_FRAME_COUNT = 8;
    private final int SHOOT_FRAME_COUNT = 4;

    private int movementFrameCount;
    private int shootFrameCount;
    private int attackFrameCount;

    private Image backgroundImage;
    private Image instructions;
    private JLabel backLabel;
    private MusicPlayer musicPlayer;
    private WindowFrame windowFrame;

    private List<Image> shootFrames;
    private List<Image> attackFrames;
    private List<Image> movementFrames;

    public InstructionsPanel (int width, int height, MusicPlayer musicPlayer, WindowFrame windowFrame) {
        this.windowFrame = windowFrame;

        this.setLayout(null);
        this.setBounds(this.windowFrame.getDEFAULT_POSITION(), this.windowFrame.getDEFAULT_POSITION(), width, height);

        this.musicPlayer = musicPlayer;
        this.shootFrameCount = 0;
        this.attackFrameCount = 0;
        this.movementFrameCount = 0;

        this.backgroundImage = new ImageIcon(MENU_BG_FILE_PATH).getImage();
        this.instructions= new ImageIcon(INSTRUCTION_FILE_PATH).getImage();

        this.movementFrames = Main.loadFrames(RUN_FRAME_COUNT, MOVEMENT_FILE_PATH);
        this.shootFrames = Main.loadFrames(SHOOT_FRAME_COUNT, FIRE_MOVEMENT_PATH);
        this.attackFrames = Main.loadFrames(ATTACK_FRAME_COUNT, ATTACK_MOVEMENT_PATH);

        this.backLabel = Main.createButtonLabel("Back", BACK_FILE_PATH, new ButtonListener(this.musicPlayer, this.windowFrame));
        this.backLabel.setBounds(this.getWidth()/80,
                (int)(this.getHeight()/1.25), this.getWidth()/4, this.getHeight()/6);
        this.add(backLabel);

        this.renderInstructionPanelLoop();
        this.updateMovementAnimation();
        this.updateShootAnimation();
        this.updateAttackAnimation();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(this.backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        g.drawImage(this.instructions, this.getWidth()/15-170, this.getHeight()/10+100,
                this.getWidth()/2+15, this.getHeight()/2, this);

        g.drawImage(this.movementFrames.get(movementFrameCount), this.getWidth()/8 -170 ,this.getHeight()/5 - 220,
                PLAYER_ANIMATION_WIDTH, PLAYER_ANIMATION_HEIGHT, this);

        g.drawImage(this.shootFrames.get(shootFrameCount), this.getWidth()/8 + 430 ,this.getHeight()/5 - 220,
                PLAYER_ANIMATION_WIDTH, PLAYER_ANIMATION_HEIGHT, this);

        g.drawImage(this.attackFrames.get(attackFrameCount), this.getWidth()/8 + 420 ,this.getHeight()/5 + 450,
                PLAYER_ANIMATION_WIDTH, PLAYER_ANIMATION_HEIGHT, this);
    }

    private void updateMovementAnimation(){
        new Thread(()->{
            while (true){
                this.movementFrameCount++;
                if (this.movementFrameCount % this.movementFrames.size() == 0){
                    this.movementFrameCount = 0;
                }
                Main.sleep(100);
            }
        }).start();
    }

    private void updateShootAnimation(){
        new Thread(()->{
            while (true){
                this.shootFrameCount++;
                if (this.shootFrameCount % this.shootFrames.size() == 0){
                    this.shootFrameCount = 0;
                }
                Main.sleep(60);
            }
        }).start();
    }

    private void updateAttackAnimation(){
        new Thread(()->{
            while (true){
                this.attackFrameCount++;
                if (this.attackFrameCount % this.attackFrames.size() == 0) {
                    this.attackFrameCount = 0;
                }
                Main.sleep(100);
            }
        }).start();
    }

    private void renderInstructionPanelLoop(){
        new Thread(() ->{
            while (true){
                repaint();
            }
        }).start();
    }
}
