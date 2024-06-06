import javax.sound.sampled.Clip;
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
            MusicPlayer.gunFireClip.setMicrosecondPosition(0);
            MusicPlayer.gunFireClip.start();
            MusicPlayer.gunFireClip.loop(Clip.LOOP_CONTINUOUSLY);
            int xMouseClick = e.getX();
            this.character.setCharacterMoving(false);
            this.character.setCharacterShooting(true);
            if (xMouseClick > this.character.getX() + this.character.getCHARACTER_WIDTH() / 2){
                this.character.setCharacterMovingBack(false);
            }else {
                this.character.setCharacterMovingBack(true);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        MusicPlayer.gunFireClip.stop();
        this.character.setCharacterShooting(false);
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
