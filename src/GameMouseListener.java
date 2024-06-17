import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameMouseListener implements MouseListener {
    Player player;
    GamePanel gamePanel;

    public GameMouseListener(GamePanel gamePanel, Player player){
        this.gamePanel = gamePanel;
        this.player = player;
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)){
            if (this.player.isAlive()){
                if (!this.player.isCharacterReloading()){
                    this.makeShootSound();
                }else {
                    this.gamePanel.getMusicPlayer().getGunFireClip().stop();
                }
                int xMouseClick = e.getX();
                this.player.setCharacterMovingRight(false);
                this.player.setCharacterStanding(false);
                this.player.setCharacterShooting(true);
                this.player.setCharacterMovingLeft(
                        xMouseClick <= this.player.getX() + this.player.getCHARACTER_WIDTH() / 2);
            }
        }
    }

    private void makeShootSound(){
        this.gamePanel.getMusicPlayer().getGunFireClip().setMicrosecondPosition(0);
        this.gamePanel.getMusicPlayer().getGunFireClip().start();
        this.gamePanel.getMusicPlayer().getGunFireClip().loop(Clip.LOOP_CONTINUOUSLY);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.gamePanel.getMusicPlayer().getGunFireClip().stop();
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
