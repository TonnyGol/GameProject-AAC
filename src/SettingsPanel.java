import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {
    private final String MENU_BG_FILE_PATH = "resources\\Images\\Menu_background.png";
    private final String BACK_FILE_PATH = "resources\\Images\\BackButton2.png";
    private final String SOUND_FX_FILE_PATH = "resources\\Images\\SoundFXButton2.png";
    private final String MUSIC_FILE_PATH = "resources\\Images\\MusicButton2.png";
    private final String VOLUME_ON_FILE_PATH = "resources\\Images\\Volume_ON.png";
    private final String VOLUME_OFF_FILE_PATH = "resources\\Images\\Volume_OFF.png";
    private final String RAISE_VOLUME_FILE_PATH = "resources\\Images\\RaiseVolume.png";
    private final String LOWER_VOLUME_FILE_PATH = "resources\\Images\\LowerVolume.png";

    private final Image backgroundImage;
    private final Image effectsImage;
    private final Image musicImage;
    private final JLabel backLabel;
    private final JLabel musicOffLabel;
    private final JLabel musicOnLabel;
    private final JLabel soundEffectsOffLabel;
    private final JLabel soundEffectsOnLabel;
    private final JLabel raiseMusicVolumeLabel;
    private final JLabel raiseSfxVolumeLabel;
    private final JLabel lowerMusicVolumeLabel;
    private final JLabel lowerSfxVolumeLabel;

    private String musicVolume;
    private String soundFxVolume;

    public static boolean switchMusicVolume;
    public static boolean switchEffectsVolume;
    public static boolean musicVolumeChanged;
    public static boolean sfxVolumeChanged;

    private int volumeState = 1;
    private int effectsState = 1;
    private final MusicPlayer musicPlayer;
    private final ButtonListener buttonListener;

    private final int IMAGE_DEFAULT_X = 10;
    private final int IMAGE_DEFAULT_Y = 50;
    private final int IMAGE_DEFAULT_WIDTH = 200;
    private final int IMAGE_DEFAULT_HEIGHT = 100;


    public SettingsPanel (int width, int height, MusicPlayer musicPlayer, ButtonListener buttonListener) {
        SettingsPanel.switchMusicVolume = false;
        SettingsPanel.switchEffectsVolume = false;
        this.setLayout(null);
        this.setBounds(WindowFrame.DEFAULT_POSITION, WindowFrame.DEFAULT_POSITION, width, height);
        this.musicPlayer = musicPlayer;
        this.buttonListener = buttonListener;
        this.backgroundImage = new ImageIcon(MENU_BG_FILE_PATH).getImage();
        this.effectsImage = new ImageIcon(SOUND_FX_FILE_PATH).getImage();
        this.musicImage = new ImageIcon(MUSIC_FILE_PATH).getImage();

        this.soundEffectsOffLabel = Main.createButtonLabel("SoundFX_OFF", VOLUME_OFF_FILE_PATH, this.buttonListener);
        this.soundEffectsOffLabel.setBounds(IMAGE_DEFAULT_X+180,
                IMAGE_DEFAULT_Y-10, IMAGE_DEFAULT_WIDTH+75, IMAGE_DEFAULT_HEIGHT+25);

        this.soundEffectsOnLabel = Main.createButtonLabel("SoundFX_ON", VOLUME_ON_FILE_PATH, this.buttonListener);
        this.soundEffectsOnLabel.setBounds(IMAGE_DEFAULT_X+190,
                this.soundEffectsOffLabel.getY(), IMAGE_DEFAULT_WIDTH, IMAGE_DEFAULT_HEIGHT+25);

        this.lowerSfxVolumeLabel = Main.createButtonLabel("LowerSfx", LOWER_VOLUME_FILE_PATH, this.buttonListener);
        this.lowerSfxVolumeLabel.setBounds(this.soundEffectsOnLabel.getX() + 220,
                this.soundEffectsOffLabel.getY()+10, IMAGE_DEFAULT_WIDTH-80, IMAGE_DEFAULT_HEIGHT+20);
        this.add(this.soundEffectsOffLabel);
        this.add(this.lowerSfxVolumeLabel);
        this.soundEffectsOffLabel.setVisible(false);

        this.raiseSfxVolumeLabel = Main.createButtonLabel("RaiseSfx", RAISE_VOLUME_FILE_PATH, this.buttonListener);
        this.raiseSfxVolumeLabel.setBounds(this.soundEffectsOnLabel.getX() + 450,
                this.soundEffectsOnLabel.getY()+10, this.lowerSfxVolumeLabel.getWidth(), this.lowerSfxVolumeLabel.getHeight());
        this.add(soundEffectsOnLabel);
        this.add(this.raiseSfxVolumeLabel);

        this.musicOffLabel = Main.createButtonLabel("Volume_OFF", VOLUME_OFF_FILE_PATH, this.buttonListener);
        this.musicOffLabel.setBounds(this.soundEffectsOffLabel.getX(),
                IMAGE_DEFAULT_Y+130, IMAGE_DEFAULT_WIDTH-25, IMAGE_DEFAULT_HEIGHT+25);

        this.musicOnLabel = Main.createButtonLabel("Volume_ON", VOLUME_ON_FILE_PATH, this.buttonListener);
        this.musicOnLabel.setBounds(this.musicOffLabel.getX()+10,
                this.musicOffLabel.getY()+10, this.musicOffLabel.getWidth()+25, this.musicOffLabel.getHeight());

        this.lowerMusicVolumeLabel = Main.createButtonLabel("LowerMusic", LOWER_VOLUME_FILE_PATH, this.buttonListener);
        this.lowerMusicVolumeLabel.setBounds(this.musicOnLabel.getX() + 220,
                this.musicOffLabel.getY()+10, IMAGE_DEFAULT_WIDTH-80, IMAGE_DEFAULT_HEIGHT+20);
        this.add(this.lowerMusicVolumeLabel);
        this.add(this.musicOffLabel);
        this.musicOffLabel.setVisible(false);

        this.raiseMusicVolumeLabel = Main.createButtonLabel("RaiseMusic", RAISE_VOLUME_FILE_PATH, this.buttonListener);
        this.raiseMusicVolumeLabel.setBounds(this.musicOnLabel.getX() + 450,
                this.musicOffLabel.getY()+10, this.lowerMusicVolumeLabel.getWidth(), this.lowerMusicVolumeLabel.getHeight());
        this.add(this.musicOnLabel);
        this.add(this.raiseMusicVolumeLabel);

        this.backLabel = Main.createButtonLabel("Back", BACK_FILE_PATH, this.buttonListener);
        this.backLabel.setBounds((int)(this.getWidth()/80),
                (int)(this.getHeight()/1.25), (int)(this.getWidth()/4), (int)(this.getHeight()/6));
        this.add(backLabel);

        this.soundFxVolume = (int) (this.musicPlayer.getVolumeSoundFx() * 100) + "%";
        this.musicVolume = (int) (this.musicPlayer.getVolumeBackgroundMusic() * 100) + "%";

        this.mainSettingsPanelLoop();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        g.drawImage(this.effectsImage, IMAGE_DEFAULT_X,
                IMAGE_DEFAULT_Y, IMAGE_DEFAULT_WIDTH, IMAGE_DEFAULT_HEIGHT, this);
        g.drawImage(this.musicImage, IMAGE_DEFAULT_X,
                (IMAGE_DEFAULT_Y+150), IMAGE_DEFAULT_WIDTH, IMAGE_DEFAULT_HEIGHT, this);
        g.setFont(new Font(null, Font.PLAIN, 30));
        g.setColor(Color.ORANGE);
        g.drawString(this.musicVolume, this.musicOnLabel.getX() + 363, this.musicOffLabel.getY()+75);
        g.drawString(this.soundFxVolume, this.soundEffectsOnLabel.getX() + 363, this.soundEffectsOffLabel.getY()+75);
    }

    private void update(){
        this.toggleVolumeOnOff();
        if (SettingsPanel.musicVolumeChanged){
            this.musicVolume = (int) (this.musicPlayer.getVolumeBackgroundMusic() * 100) + "%";
            SettingsPanel.musicVolumeChanged = false;
        }
        if (SettingsPanel.sfxVolumeChanged){
            this.soundFxVolume = (int) (this.musicPlayer.getVolumeSoundFx() * 100) + "%";
            SettingsPanel.sfxVolumeChanged = false;
        }
    }

    public void toggleVolumeOnOff(){
        if (switchMusicVolume){
            if (volumeState == 1){
                this.musicOnLabel.setVisible(false);
                this.musicOffLabel.setVisible(true);
                this.volumeState = 0;
            } else{
                this.musicOffLabel.setVisible(false);
                this.musicOnLabel.setVisible(true);
                this.volumeState = 1;
            }
            SettingsPanel.switchMusicVolume = false;
        }
        if(switchEffectsVolume){
            if (effectsState == 1){
                this.soundEffectsOnLabel.setVisible(false);
                this.soundEffectsOffLabel.setVisible(true);
                this.effectsState = 0;
            } else{
                this.soundEffectsOffLabel.setVisible(false);
                this.soundEffectsOnLabel.setVisible(true);
                this.effectsState = 1;
            }
            SettingsPanel.switchEffectsVolume = false;
        }
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
