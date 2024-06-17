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

    private final int IMAGE_DEFAULT_X = 10;
    private final int IMAGE_DEFAULT_Y = 50;
    private final int IMAGE_DEFAULT_WIDTH = 200;
    private final int IMAGE_DEFAULT_HEIGHT = 100;

    private final int VOLUME_STATE_ON = 1;
    private final int VOLUME_STATE_OFF = 0;

    private String musicVolume;
    private String soundFxVolume;

    private boolean switchMusicVolume;
    private boolean switchSoundFxVolume;
    private boolean musicVolumeChanged;
    private boolean soundFxVolumeChanged;

    private int musicState;
    private int soundFxState;

    private Image backgroundImage;
    private Image soundFxImage;
    private Image musicImage;
    private JLabel backLabel;
    private JLabel musicOffLabel;
    private JLabel musicOnLabel;
    private JLabel soundFxOffLabel;
    private JLabel soundFxOnLabel;
    private JLabel raiseMusicVolumeLabel;
    private JLabel raiseSfxVolumeLabel;
    private JLabel lowerMusicVolumeLabel;
    private JLabel lowerSfxVolumeLabel;

    private MusicPlayer musicPlayer;
    private WindowFrame windowFrame;

    public SettingsPanel (int width, int height, MusicPlayer musicPlayer, WindowFrame windowFrame) {
        this.windowFrame = windowFrame;

        this.setLayout(null);
        this.setBounds(this.windowFrame.getDEFAULT_POSITION(), this.windowFrame.getDEFAULT_POSITION(), width, height);

        this.musicPlayer = musicPlayer;

        this.switchMusicVolume = false;
        this.switchSoundFxVolume = false;
        this.musicVolumeChanged = false;
        this.soundFxVolumeChanged = false;

        this.musicState = VOLUME_STATE_ON;
        this.soundFxState = VOLUME_STATE_ON;

        this.backgroundImage = new ImageIcon(MENU_BG_FILE_PATH).getImage();
        this.soundFxImage = new ImageIcon(SOUND_FX_FILE_PATH).getImage();
        this.musicImage = new ImageIcon(MUSIC_FILE_PATH).getImage();

        this.soundFxOffLabel = Main.createButtonLabel("SoundFX_OFF", VOLUME_OFF_FILE_PATH, new ButtonListener(this.musicPlayer, this));
        this.soundFxOffLabel.setBounds(IMAGE_DEFAULT_X+180,
                IMAGE_DEFAULT_Y-10, IMAGE_DEFAULT_WIDTH+75, IMAGE_DEFAULT_HEIGHT+25);
        this.add(this.soundFxOffLabel);
        this.soundFxOffLabel.setVisible(false);

        this.soundFxOnLabel = Main.createButtonLabel("SoundFX_ON", VOLUME_ON_FILE_PATH, new ButtonListener(this.musicPlayer, this));
        this.soundFxOnLabel.setBounds(IMAGE_DEFAULT_X+190,
                this.soundFxOffLabel.getY(), IMAGE_DEFAULT_WIDTH, IMAGE_DEFAULT_HEIGHT+25);
        this.add(soundFxOnLabel);

        this.lowerSfxVolumeLabel = Main.createButtonLabel("LowerSfx", LOWER_VOLUME_FILE_PATH, new ButtonListener(this.musicPlayer, this));
        this.lowerSfxVolumeLabel.setBounds(this.soundFxOnLabel.getX() + 220,
                this.soundFxOffLabel.getY()+10, IMAGE_DEFAULT_WIDTH-80, IMAGE_DEFAULT_HEIGHT+20);
        this.add(this.lowerSfxVolumeLabel);

        this.raiseSfxVolumeLabel = Main.createButtonLabel("RaiseSfx", RAISE_VOLUME_FILE_PATH, new ButtonListener(this.musicPlayer, this));
        this.raiseSfxVolumeLabel.setBounds(this.soundFxOnLabel.getX() + 450,
                this.soundFxOnLabel.getY()+10, this.lowerSfxVolumeLabel.getWidth(), this.lowerSfxVolumeLabel.getHeight());
        this.add(this.raiseSfxVolumeLabel);

        this.musicOffLabel = Main.createButtonLabel("Volume_OFF", VOLUME_OFF_FILE_PATH, new ButtonListener(this.musicPlayer, this));
        this.musicOffLabel.setBounds(this.soundFxOffLabel.getX(),
                IMAGE_DEFAULT_Y+130, IMAGE_DEFAULT_WIDTH-25, IMAGE_DEFAULT_HEIGHT+25);
        this.add(this.musicOffLabel);
        this.musicOffLabel.setVisible(false);

        this.musicOnLabel = Main.createButtonLabel("Volume_ON", VOLUME_ON_FILE_PATH, new ButtonListener(this.musicPlayer, this));
        this.musicOnLabel.setBounds(this.musicOffLabel.getX()+10,
                this.musicOffLabel.getY()+10, this.musicOffLabel.getWidth()+25, this.musicOffLabel.getHeight());
        this.add(this.musicOnLabel);

        this.lowerMusicVolumeLabel = Main.createButtonLabel("LowerMusic", LOWER_VOLUME_FILE_PATH, new ButtonListener(this.musicPlayer, this));
        this.lowerMusicVolumeLabel.setBounds(this.musicOnLabel.getX() + 220,
                this.musicOffLabel.getY()+10, IMAGE_DEFAULT_WIDTH-80, IMAGE_DEFAULT_HEIGHT+20);
        this.add(this.lowerMusicVolumeLabel);

        this.raiseMusicVolumeLabel = Main.createButtonLabel("RaiseMusic", RAISE_VOLUME_FILE_PATH, new ButtonListener(this.musicPlayer, this));
        this.raiseMusicVolumeLabel.setBounds(this.musicOnLabel.getX() + 450,
                this.musicOffLabel.getY()+10, this.lowerMusicVolumeLabel.getWidth(), this.lowerMusicVolumeLabel.getHeight());
        this.add(this.raiseMusicVolumeLabel);

        this.backLabel = Main.createButtonLabel("Back", BACK_FILE_PATH, new ButtonListener(this.musicPlayer, this, this.windowFrame));
        this.backLabel.setBounds(this.getWidth()/80,
                (int)(this.getHeight()/1.25), this.getWidth()/4, this.getHeight()/6);
        this.add(backLabel);

        this.soundFxVolume = (int) (this.musicPlayer.getVolumeSoundFx() * 100) + "%";
        this.musicVolume = (int) (this.musicPlayer.getVolumeBackgroundMusic() * 100) + "%";

        this.mainSettingsPanelLoop();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        g.drawImage(this.soundFxImage, IMAGE_DEFAULT_X,
                IMAGE_DEFAULT_Y, IMAGE_DEFAULT_WIDTH, IMAGE_DEFAULT_HEIGHT, this);
        g.drawImage(this.musicImage, IMAGE_DEFAULT_X,
                (IMAGE_DEFAULT_Y+150), IMAGE_DEFAULT_WIDTH, IMAGE_DEFAULT_HEIGHT, this);

        g.setFont(new Font(null, Font.PLAIN, 30));
        g.setColor(Color.ORANGE);
        g.drawString(this.musicVolume, this.musicOnLabel.getX() + 363, this.musicOffLabel.getY()+75);
        g.drawString(this.soundFxVolume, this.soundFxOnLabel.getX() + 363, this.soundFxOffLabel.getY()+75);
    }

    private void update(){
        if (this.switchMusicVolume || this.switchSoundFxVolume){
            this.toggleVolumeOnOff();
        }
        if (this.musicVolumeChanged){
            if(this.musicState == VOLUME_STATE_ON) {
                int musicVolume = (int) (this.musicPlayer.getVolumeBackgroundMusic() * 100);
                if (musicVolume % 10 == 9) {
                    musicVolume++;
                }
                this.musicVolume = musicVolume + "%";
                this.musicVolumeChanged = false;
            }
        }
        if (this.soundFxVolumeChanged){
            if(this.soundFxState == VOLUME_STATE_ON) {
                int sfxVolume = (int) (this.musicPlayer.getVolumeSoundFx() * 100);
                if (sfxVolume % 10 == 9) {
                    sfxVolume++;
                }
                this.soundFxVolume = sfxVolume + "%";
                this.soundFxVolumeChanged = false;
            }
        }
    }

    private void toggleVolumeOnOff(){
        if (this.switchMusicVolume){
            if (musicState == VOLUME_STATE_ON){
                this.musicOnLabel.setVisible(false);
                this.musicOffLabel.setVisible(true);
                this.musicState = VOLUME_STATE_OFF;
            } else{
                this.musicOffLabel.setVisible(false);
                this.musicOnLabel.setVisible(true);
                this.musicState = VOLUME_STATE_ON;
            }
            this.switchMusicVolume = false;
        }
        if(this.switchSoundFxVolume){
            if (soundFxState == VOLUME_STATE_ON){
                this.soundFxOnLabel.setVisible(false);
                this.soundFxOffLabel.setVisible(true);
                this.soundFxState = VOLUME_STATE_OFF;
            } else{
                this.soundFxOffLabel.setVisible(false);
                this.soundFxOnLabel.setVisible(true);
                this.soundFxState = VOLUME_STATE_ON;
            }
            this.switchSoundFxVolume = false;
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

    public void setSwitchMusicVolume(boolean switchMusicVolume) {
        this.switchMusicVolume = switchMusicVolume;
    }

    public void setSwitchSoundFxVolume(boolean switchSoundFxVolume) {
        this.switchSoundFxVolume = switchSoundFxVolume;
    }

    public void setMusicVolumeChanged(boolean musicVolumeChanged) {
        this.musicVolumeChanged = musicVolumeChanged;
    }

    public void setSoundFxVolumeChanged(boolean soundFxVolumeChanged) {
        this.soundFxVolumeChanged = soundFxVolumeChanged;
    }
}
