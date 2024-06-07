import javax.sound.sampled.Clip;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyListener implements KeyListener {
    GamePanel gamePanel;
    Character character;

    public GameKeyListener(GamePanel gamePanel, Character soldier){
        this.gamePanel = gamePanel;
        this.character = soldier;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        this.character.setCharacterStanding(false);
        if (keyCode == KeyEvent.VK_W) {
            this.character.setCharacterMoving(true);
            MusicPlayer.runOnSandClip.start();
            MusicPlayer.runOnSandClip.loop(Clip.LOOP_CONTINUOUSLY);
            this.character.setDy(-this.character.getCHARACTER_SPEED());
        } else if (keyCode == KeyEvent.VK_S) {
            this.character.setCharacterMoving(true);
            MusicPlayer.runOnSandClip.start();
            MusicPlayer.runOnSandClip.loop(Clip.LOOP_CONTINUOUSLY);
            this.character.setDy(this.character.getCHARACTER_SPEED());
        } else if (keyCode == KeyEvent.VK_D) {
            this.character.setCharacterMoving(true);
            this.character.setCharacterMovingBack(false);
            MusicPlayer.runOnSandClip.start();
            MusicPlayer.runOnSandClip.loop(Clip.LOOP_CONTINUOUSLY);
            this.character.setDx(this.character.getCHARACTER_SPEED());
        } else if (keyCode == KeyEvent.VK_A) {
            this.character.setCharacterMoving(true);
            this.character.setCharacterMovingBack(true);
            MusicPlayer.runOnSandClip.start();
            MusicPlayer.runOnSandClip.loop(Clip.LOOP_CONTINUOUSLY);
            this.character.setDx(-this.character.getCHARACTER_SPEED());
        } else if (keyCode == KeyEvent.VK_SPACE) {
            this.character.setCharacterMoving(false);
            this.character.setCharacterStanding(false);
            this.character.setCharacterAttack(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.character.setCharacterStanding(true);
        this.character.setCharacterMoving(false);
        this.character.setCharacterAttack(false);
        this.character.setDx(0);
        this.character.setDy(0);
        this.character.setRunFrameIndex(0);
        this.character.setAttackFrameIndex(0);
        MusicPlayer.runOnSandClip.setMicrosecondPosition(0);
        MusicPlayer.runOnSandClip.stop();
    }
}
