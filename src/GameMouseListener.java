import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameMouseListener implements MouseListener {
    GamePanel gamePanel;
    Player player;

    public GameMouseListener(GamePanel gamePanel, Player soldier){
        this.gamePanel = gamePanel;
        this.player = soldier;
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
            this.player.setCharacterMovingRight(false);
            this.player.setCharacterStanding(false);
            this.player.setCharacterShooting(true);

            if (xMouseClick > this.player.getX() + this.player.getCHARACTER_WIDTH() / 2){
                this.player.setCharacterMovingLeft(false);
            }else {
                this.player.setCharacterMovingLeft(true);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        MusicPlayer.gunFireClip.stop();
        this.player.setCharacterShooting(false);
        this.player.setCharacterStanding(true);
        this.player.setShootFrameIndex(0);
        Main.sleep(10);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
