import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ButtonListener implements MouseListener {
    private MusicPlayer musicPlayer;
    private WindowFrame windowFrame;
    private GamePanel gamePanel;
    private SettingsPanel settingsPanel;

    public ButtonListener(WindowFrame windowFrame){
        this.windowFrame = windowFrame;
    }
    public ButtonListener(WindowFrame windowFrame, GamePanel gamePanel){
        this.gamePanel = gamePanel;
        this.windowFrame = windowFrame;
    }
    public ButtonListener(MusicPlayer musicPlayer, WindowFrame windowFrame){
        this.windowFrame = windowFrame;
        this.musicPlayer = musicPlayer;
    }
    public ButtonListener(MusicPlayer musicPlayer, SettingsPanel settingsPanel){
        this.musicPlayer = musicPlayer;
        this.settingsPanel = settingsPanel;
    }
    public ButtonListener(MusicPlayer musicPlayer, SettingsPanel settingsPanel, WindowFrame windowFrame){
        this.musicPlayer = musicPlayer;
        this.windowFrame = windowFrame;
        this.settingsPanel = settingsPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Component label = e.getComponent();
        String command = label.getName();
        switch (command){
            case "Play":
                this.windowFrame.setPanelChoice(1);
                this.windowFrame.setSwitchPanels(true);
                break;
            case "Instructions":
                this.windowFrame.setPanelChoice(2);
                this.windowFrame.setSwitchPanels(true);
                break;
            case "Settings":
                this.windowFrame.setPanelChoice(3);
                this.windowFrame.setSwitchPanels(true);
                break;
            case "Quit":
                System.exit(0);
                break;
            case "Back":
                this.windowFrame.setPanelChoice(0);
                this.windowFrame.setSwitchPanels(true);
                break;
            case "Volume_ON", "Volume_OFF":
                this.settingsPanel.setSwitchMusicVolume(true);
                if (command.equals("Volume_ON")){
                    this.musicPlayer.setVolumeBackgroundMusic(0);
                }else{
                    this.musicPlayer.setVolumeBackgroundMusic(0.6f);
                    this.settingsPanel.setMusicVolumeChanged(true);
                }
                break;
            case "SoundFX_ON", "SoundFX_OFF":
                this.settingsPanel.setSwitchSoundFxVolume(true);
                if (command.equals("SoundFX_ON")){
                    this.musicPlayer.setVolumeSoundFx(0);
                }else{
                    this.musicPlayer.setVolumeSoundFx(0.7f);
                    this.settingsPanel.setSoundFxVolumeChanged(true);
                }
                break;
            case "RaiseMusic":
                this.musicPlayer.setVolumeBackgroundMusic(this.musicPlayer.getVolumeBackgroundMusic() + 0.10f);
                this.settingsPanel.setMusicVolumeChanged(true);
                break;
            case "LowerMusic":
                this.musicPlayer.setVolumeBackgroundMusic(this.musicPlayer.getVolumeBackgroundMusic() - 0.10f);
                this.settingsPanel.setMusicVolumeChanged(true);
                break;
            case "RaiseSfx":
                this.musicPlayer.setVolumeSoundFx(this.musicPlayer.getVolumeSoundFx() + 0.10f);
                this.settingsPanel.setSoundFxVolumeChanged(true);
                break;
            case "LowerSfx":
                this.musicPlayer.setVolumeSoundFx(this.musicPlayer.getVolumeSoundFx() - 0.10f);
                this.settingsPanel.setSoundFxVolumeChanged(true);
                break;
            case "Replay":
                this.gamePanel.getEnemies().clear();
                this.gamePanel.setCountTimer(0);
                this.gamePanel.getPlayer().setPoints(0);
                this.gamePanel.replay();
                break;
            case "GiveUp":
                this.windowFrame.setPanelChoice(0);
                this.windowFrame.setSwitchPanels(true);
                this.gamePanel.getEnemies().clear();
                this.gamePanel.setCountTimer(0);
                this.gamePanel.getPlayer().setPoints(0);
                this.gamePanel.replay();
                break;
            default:
                System.out.println("A button was clicked");
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
