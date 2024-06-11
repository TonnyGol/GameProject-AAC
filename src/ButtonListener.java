import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ButtonListener implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {
        Component label = e.getComponent();
        String command = label.getName();
        if(command.equals("BACK")){
            WindowFrame.panelChoice = 0;
            WindowFrame.switchPanels = true;
        }
        if (command.equals("Play")){
            WindowFrame.panelChoice = 1;
            WindowFrame.switchPanels = true;
        }
        if (command.equals("Instructions")){
            WindowFrame.panelChoice = 2;
            WindowFrame.switchPanels = true;
        }
        if (command.equals("Settings")){
            WindowFrame.panelChoice = 3;
            WindowFrame.switchPanels = true;
        }
        if (command.equals("Quit")){
            //WindowFrame.musicPlayer.stopMusic();
            System.exit(0);
        }
        if(command.equals("Volume_ON") || command.equals("Volume_OFF")){
            SettingsPanel.switchMusicVolume = true;
        }
        if(command.equals("SoundFX_ON") || command.equals("SoundFX_OFF")){
            SettingsPanel.switchEffectsVolume = true;
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
