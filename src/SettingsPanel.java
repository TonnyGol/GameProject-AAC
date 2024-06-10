import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {
    private final String MENU_BG_FILE_PATH = "resources\\Images\\Menu_background.png";
    private final String BACK_FILE_PATH = "resources\\Images\\BackButton2.png";
    private final String SOUND_FX_FILE_PATH = "resources\\Images\\SoundFXButton2.png";
    private final String MUSIC_FILE_PATH = "resources\\Images\\MusicButton2.png";
    private final String VOLUME_ON_FILE_PATH = "resources\\Images\\Volume_ON.png";
    private final String VOLUME_OFF_FILE_PATH = "resources\\Images\\Volume_OFF.png";
    private final String VOLUME_SLIDER_PATH = "resources\\Images\\SliderTest.png";

    private final Image backgroundImage;
    private final Image effectsImage;
    private final Image musicImage;
    private final JLabel volumeSliderLabel;
    private JLabel backLabel;
    private JLabel musicOffLabel;
    private JLabel musicOnLabel;
    private JLabel effectsOffLabel;
    private JLabel effectsOnLabel;
    public static boolean switchMusicVolume;
    public static boolean switchEffectsVolume;
    private int volumeState = 1;
    private int effectsState = 1;


    public SettingsPanel (int width, int height) {
        SettingsPanel.switchMusicVolume = false;
        SettingsPanel.switchEffectsVolume = false;
        this.setLayout(null);
        this.setBounds(WindowFrame.DEFAULT_POSITION, WindowFrame.DEFAULT_POSITION, width, height);
        this.backgroundImage = new ImageIcon(MENU_BG_FILE_PATH).getImage();
        this.effectsImage = new ImageIcon(SOUND_FX_FILE_PATH).getImage();
        this.musicImage = new ImageIcon(MUSIC_FILE_PATH).getImage();
        this.backLabel = WindowFrame.createPhotoLabel("BACK",BACK_FILE_PATH);
        this.backLabel.setBounds(50,850,450,150);
        this.add(backLabel);
        this.effectsOffLabel = WindowFrame.createPhotoLabel("SoundFX_OFF", VOLUME_OFF_FILE_PATH);
        this.effectsOffLabel.setBounds(180,40,175,125);
        this.add(effectsOffLabel);
        this.effectsOffLabel.setVisible(false);
        this.effectsOnLabel = WindowFrame.createPhotoLabel("SoundFX_ON", VOLUME_ON_FILE_PATH);
        this.effectsOnLabel.setBounds(200,35,200,125);
        this.add(effectsOnLabel);

        this.musicOffLabel = WindowFrame.createPhotoLabel("Volume_OFF", VOLUME_OFF_FILE_PATH);
        this.musicOffLabel.setBounds(180,180,175,125);
        this.add(musicOffLabel);
        musicOffLabel.setVisible(false);
        this.musicOnLabel = WindowFrame.createPhotoLabel("Volume_ON", VOLUME_ON_FILE_PATH);
        this.musicOnLabel.setBounds(200,180,200,125);
        this.add(musicOnLabel);

        this.volumeSliderLabel = new JLabel(new ImageIcon(VOLUME_SLIDER_PATH));
        this.volumeSliderLabel.setBounds(450, 200, 350, 100);
        this.add(this.volumeSliderLabel);
        this.mainSettingsPanelLoop();
    }
    private void changeMusicOnOrOff(float adjustment){
        WindowFrame.musicPlayer.setVolumeBackgroundMusic(adjustment);
    }
    private void changeSoundFxOnOrOff(float adjustment){
        WindowFrame.musicPlayer.setVolumeSoundFx(adjustment);
    }


    public void changeVolumeIcon(){
        if (volumeState == 1){
            this.musicOnLabel.setVisible(false);
            this.musicOffLabel.setVisible(true);
            this.changeMusicOnOrOff(0);
            this.volumeState = 0;
        } else{
            this.musicOffLabel.setVisible(false);
            this.musicOnLabel.setVisible(true);
            this.changeMusicOnOrOff(0.7f);
            this.volumeState = 1;
        }
        SettingsPanel.switchMusicVolume = false;
    }
    public void changeEffectsIcon(){
        if (effectsState == 1){
            this.effectsOnLabel.setVisible(false);
            this.effectsOffLabel.setVisible(true);
            this.changeSoundFxOnOrOff(0);
            this.effectsState = 0;
        } else{
            this.effectsOffLabel.setVisible(false);
            this.effectsOnLabel.setVisible(true);
            this.changeSoundFxOnOrOff(0.7f);
            this.effectsState = 1;
        }
        SettingsPanel.switchEffectsVolume = false;
    }

    private void update(){
        if(switchMusicVolume){
            this.changeVolumeIcon();
        }
        if(switchEffectsVolume){
            this.changeEffectsIcon();
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        g.drawImage(this.effectsImage, 10, 50, 200, 100, this);
        g.drawImage(this.musicImage, 10, 200, 200, 100, this);
    }

    private void mainSettingsPanelLoop(){
        new Thread(() ->{
            while (true){
                update();
                repaint();
            }
        }).start();
    }
}
