import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ButtonListener implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {
        Component label = e.getComponent();
        if (label.getName().equals("Play")){
            WindowFrame.gameRuns = true;
            label.getParent().setFocusable(false);
            label.getParent().setVisible(false);
        }
        if (label.getName().equals("Instructions")){
            WindowFrame.instructionsRun = true;
            label.getParent().setFocusable(false);
            label.getParent().setVisible(false);
            WindowFrame.gameRuns = false;




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
