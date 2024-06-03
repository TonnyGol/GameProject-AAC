import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameMouseListener implements MouseListener {
    GamePanel gamePanel;
    Character character;

    public GameMouseListener(GamePanel gamePanel, Character soldier){
        this.gamePanel = gamePanel;
        this.character = soldier;
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)){
            this.gamePanel.setCharacterMoving(false);
            this.gamePanel.setCharacterShooting(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.gamePanel.setCharacterShooting(false);
        this.character.setShootFrameIndex(0);
        Main.sleep(10);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
