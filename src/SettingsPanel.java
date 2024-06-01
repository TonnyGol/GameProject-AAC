import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SettingsPanel extends JPanel {
    private final String MENU_BG_FILE_PATH = "resources\\Images\\Menu_background.png";
    private final String BACK_FILE_PATH = "resources\\Images\\BackButton2.png";
    private final String SOUND_FX_FILE_PATH = "resources\\Images\\SoundFXButton2.png";
    private final String VOLUME_FILE_PATH = "resources\\Images\\MusicButton2.png";
    private final Image backgroundImage;
    private final Image effectsImage;
    private final Image volumeImage;
    private final String VOLUME_ON_FILE_PATH = "resources\\Images\\Volume_ON.png";
    private final String VOLUME_OFF_FILE_PATH = "resources\\Images\\Volume_OFF.png";


    private int width;
    private int height;
    private JLabel backLabel;
    private JLabel volumeOffLabel;
    private JLabel volumeOnLabel;
    private JLabel effectsOffLabel;
    private JLabel effectsOnLabel;
    public static boolean switchMusicVolume;
    public static boolean switchEffectsVolume;
    private int volumeState = 1;
    private int effectsState = 1;
    private JLabel volumeLabel;


    public SettingsPanel (int width, int height) {
        this.width = width;
        this.height = height;
        SettingsPanel.switchMusicVolume = false;
        SettingsPanel.switchEffectsVolume = false;
        this.setLayout(null);
        this.setBounds(WindowFrame.DEFAULT_POSITION, WindowFrame.DEFAULT_POSITION, this.width, this.height);
        this.backgroundImage = new ImageIcon(MENU_BG_FILE_PATH).getImage();
        this.effectsImage = new ImageIcon(SOUND_FX_FILE_PATH).getImage();
        this.volumeImage = new ImageIcon(VOLUME_FILE_PATH).getImage();
        this.backLabel =  WindowFrame.createPhotoLabel("BACK",BACK_FILE_PATH);
        this.backLabel.setBounds(50,850,450,150);
        this.add(backLabel);
        this.effectsOffLabel = WindowFrame.createPhotoLabel("SoundFX_OFF", VOLUME_OFF_FILE_PATH);
        this.effectsOffLabel.setBounds(180,40,175,125);
        this.add(effectsOffLabel);
        this.effectsOffLabel.setVisible(false);
        this.effectsOnLabel = WindowFrame.createPhotoLabel("SoundFX_ON", VOLUME_ON_FILE_PATH);
        this.effectsOnLabel.setBounds(200,35,200,125);
        this.add(effectsOnLabel);

        this.volumeOffLabel = WindowFrame.createPhotoLabel("Volume_OFF", VOLUME_OFF_FILE_PATH);
        this.volumeOffLabel.setBounds(180,180,175,125);
        this.add(volumeOffLabel);
        volumeOffLabel.setVisible(false);
        this.volumeOnLabel = WindowFrame.createPhotoLabel("Volume_ON", VOLUME_ON_FILE_PATH);
        this.volumeOnLabel.setBounds(200,180,200,125);
        this.add(volumeOnLabel);
        this.mainSettingsPanelLoop();




    }
    private static void adjustVolume(float adjustment) {
        WindowFrame.musicPlayer.setVolume(adjustment);
    }


    public void changeVolumeIcon(){
        if (volumeState == 1){
            this.volumeOnLabel.setVisible(false);
            this.volumeOffLabel.setVisible(true);
            this.adjustVolume(0);
            volumeState = 0;
        } else{
            this.volumeOffLabel.setVisible(false);
            this.volumeOnLabel.setVisible(true);
            this.adjustVolume(0.5f);
            volumeState = 1;
        }
        SettingsPanel.switchMusicVolume = false;
    }
    public void changeEffectsIcon(){
        if (effectsState == 1){
            this.effectsOnLabel.setVisible(false);
            this.effectsOffLabel.setVisible(true);
            effectsState = 0;
        } else{
            this.effectsOffLabel.setVisible(false);
            this.effectsOnLabel.setVisible(true);
            effectsState = 1;
        }
        SettingsPanel.switchEffectsVolume = false;
    }


        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(this.backgroundImage, 0, 0, this.width, this.height, this);
            g.drawImage(this.effectsImage, 10, 50, 200, 100, this);
            g.drawImage(this.volumeImage, 10, 200, 200, 100, this);

    }

    private void mainSettingsPanelLoop(){
        new Thread(() ->{
            while (true){
                if (WindowFrame.panelChoice == 3){
                    if(switchMusicVolume){
                        changeVolumeIcon();
                    }
                    if(switchEffectsVolume){
                        changeEffectsIcon();
                    }
                    repaint();
                }
            }
        }).start();
    }
}
