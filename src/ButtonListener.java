import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ButtonListener implements MouseListener {
    private final MusicPlayer musicPlayer;
    private GamePanel gamePanel;
    private SettingsPanel settingsPanel;

    public ButtonListener(MusicPlayer musicPlayer){
        this.musicPlayer = musicPlayer;
    }
    public ButtonListener(MusicPlayer musicPlayer, GamePanel gamePanel){
        this.gamePanel = gamePanel;
        this.musicPlayer = musicPlayer;
    }
    public ButtonListener(MusicPlayer musicPlayer, SettingsPanel settingsPanel){
        this.musicPlayer = musicPlayer;
        this.settingsPanel = settingsPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Component label = e.getComponent();
        String command = label.getName();
        switch (command){
            case "Play":
                WindowFrame.panelChoice = 1;
                WindowFrame.switchPanels = true;
                break;
            case "Instructions":
                WindowFrame.panelChoice = 2;
                WindowFrame.switchPanels = true;
                break;
            case "Settings":
                WindowFrame.panelChoice = 3;
                WindowFrame.switchPanels = true;
                break;
            case "Quit":
                System.exit(0);
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
            case "Back":
                WindowFrame.panelChoice = 0;
                WindowFrame.switchPanels = true;
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
                WindowFrame.panelChoice = 0;
                WindowFrame.switchPanels = true;
                this.gamePanel.getEnemies().clear();
                this.gamePanel.setCountTimer(0);
                this.gamePanel.getPlayer().setPoints(0);
                this.gamePanel.replay();
                break;
            case "Replay":

                break;
            case "GiveUp":
                WindowFrame.panelChoice = 0;
                WindowFrame.switchPanels = true;
                label.setFocusable(false);
                label.setVisible(false);
//                this.replayLabel.setFocusable(true);
//                this.replayLabel.setVisible(true);
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
