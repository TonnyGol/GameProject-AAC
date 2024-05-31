import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ButtonListener implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {
        Component label = e.getComponent();
        String command = label.getName();
        if(command.equals("BackLabel")){
            WindowFrame.switchPanels = true;
            WindowFrame.panelChoice = 0;
        }
        if (command.equals("Play")){
            WindowFrame.switchPanels = true;
            WindowFrame.panelChoice = 1;
        }
        if (command.equals("Instructions")){
            WindowFrame.switchPanels = true;
            WindowFrame.panelChoice = 2;
        }
        if (label.getName().equals("Settings")){

        }
        if (label.getName().equals("Quit")){
            System.exit(0);
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
