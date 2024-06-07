import javax.sound.sampled.Clip;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyListener implements KeyListener {
    GamePanel gamePanel;
    Player player;

    public GameKeyListener(GamePanel gamePanel, Player soldier){
        this.gamePanel = gamePanel;
        this.player = soldier;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        this.player.setCharacterStanding(false);
        if (keyCode == KeyEvent.VK_W) {
            this.player.setCharacterMovingRight(true);
            MusicPlayer.runOnSandClip.start();
            MusicPlayer.runOnSandClip.loop(Clip.LOOP_CONTINUOUSLY);
            this.player.setDy(-this.player.getCHARACTER_SPEED());
        } else if (keyCode == KeyEvent.VK_S) {
            this.player.setCharacterMovingRight(true);
            MusicPlayer.runOnSandClip.start();
            MusicPlayer.runOnSandClip.loop(Clip.LOOP_CONTINUOUSLY);
            this.player.setDy(this.player.getCHARACTER_SPEED());
        } else if (keyCode == KeyEvent.VK_D) {
            this.player.setCharacterMovingRight(true);
            this.player.setCharacterMovingLeft(false);
            MusicPlayer.runOnSandClip.start();
            MusicPlayer.runOnSandClip.loop(Clip.LOOP_CONTINUOUSLY);
            this.player.setDx(this.player.getCHARACTER_SPEED());
        } else if (keyCode == KeyEvent.VK_A) {
            this.player.setCharacterMovingRight(true);
            this.player.setCharacterMovingLeft(true);
            MusicPlayer.runOnSandClip.start();
            MusicPlayer.runOnSandClip.loop(Clip.LOOP_CONTINUOUSLY);
            this.player.setDx(-this.player.getCHARACTER_SPEED());
        } else if (keyCode == KeyEvent.VK_SPACE) {
            this.player.setCharacterMovingRight(false);
            this.player.setCharacterStanding(false);
            this.player.setCharacterAttack(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.player.setCharacterStanding(true);
        this.player.setCharacterMovingRight(false);
        this.player.setCharacterAttack(false);
        this.player.setDx(0);
        this.player.setDy(0);
        this.player.setRunFrameIndex(0);
        this.player.setAttackFrameIndex(0);
        MusicPlayer.runOnSandClip.setMicrosecondPosition(0);
        MusicPlayer.runOnSandClip.stop();
    }
}
