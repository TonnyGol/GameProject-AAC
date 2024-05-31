import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {
    private final String MENU_BG_FILE_NAME = "Menu_background.png";
    private final String BACK_FILE_PATH = "BackButton.png";
    private final String EFFECTS_FILE_NAME = "Effects.png";
    private final String checkedEFFECTS_FILE_NAME = "checkedEFFECTS.png";
    private final String uncheckedEFFECTS_FILE_NAME = "uncheckedEFFECTS.png";
    private final Image backgroundImage;
    private final Image effectsImage;
    private final String VOLUME_UP_FILE_NAME = "Volume_up.png";
    private final String VOLUME_MUTE_FILE_NAME = "Volume_mute.png";
    private JLabel volumeMUTE_Label;
    private JLabel volumeUP_Label;
    private JLabel uncheckedEFFECTS_Label;
    private JLabel checkedEFFECTS_Label;
    private int width;
    private int height;
    private JLabel backLabel;


    public SettingsPanel (int width, int height) {
        this.width = width;
        this.height = height;
        this.setLayout(null);
        this.setBounds(WindowFrame.DEFAULT_POSITION, WindowFrame.DEFAULT_POSITION, this.width, this.height);
        this.backgroundImage = new ImageIcon("src\\Images\\" + MENU_BG_FILE_NAME).getImage();
        this.effectsImage = new ImageIcon("src\\Images\\" + EFFECTS_FILE_NAME).getImage();
        this.backLabel =  WindowFrame.createPhotoLabel("BACK",BACK_FILE_PATH);
        this.backLabel.setBounds(50,850,450,150);
        this.add(backLabel);
        this.volumeMUTE_Label = WindowFrame.createPhotoLabel("volumeMUTE",VOLUME_MUTE_FILE_NAME);
        this.volumeMUTE_Label.setBounds(100,500,300,150);
        this.volumeUP_Label = WindowFrame.createPhotoLabel("Volume_up",VOLUME_UP_FILE_NAME);
        this.mainSettingsPanelLoop();
    }


    public static void changeIcon(){
       boolean flag = false;

    }


        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(this.backgroundImage, 0, 0, this.width, this.height, this);
            g.drawImage(this.effectsImage, 10, 50, 200, 100, this);

    }

    private void mainSettingsPanelLoop(){
        new Thread(() ->{
            while (true){
                if (WindowFrame.panelChoice == 3){
                    repaint();
                }
            }
        }).start();
    }
}
