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

        if (keyCode == KeyEvent.VK_W){
            this.character.setCharacterMoving(true);
            MusicPlayer.runOnSandClip.start();
            MusicPlayer.runOnSandClip.loop(Clip.LOOP_CONTINUOUSLY);
            this.character.setDy(-this.character.getCHARACTER_SPEED());
        } else if (keyCode == KeyEvent.VK_S) {
            this.character.setCharacterMoving(true);
            MusicPlayer.runOnSandClip.start();
            MusicPlayer.runOnSandClip.loop(Clip.LOOP_CONTINUOUSLY);
            this.character.setDy(this.character.getCHARACTER_SPEED());
        } else if (keyCode == KeyEvent.VK_D){
            this.character.setCharacterMoving(true);
            this.character.setCharacterMovingBack(false);
            MusicPlayer.runOnSandClip.start();
            MusicPlayer.runOnSandClip.loop(Clip.LOOP_CONTINUOUSLY);
            this.character.setDx(this.character.getCHARACTER_SPEED());
        } else if (keyCode == KeyEvent.VK_A){
            this.character.setCharacterMoving(true);
            this.character.setCharacterMovingBack(true);
            MusicPlayer.runOnSandClip.start();
            MusicPlayer.runOnSandClip.loop(Clip.LOOP_CONTINUOUSLY);
            this.character.setDx(-this.character.getCHARACTER_SPEED());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.character.setCharacterMoving(false);
        MusicPlayer.runOnSandClip.setMicrosecondPosition(0);
        MusicPlayer.runOnSandClip.stop();
        if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D){
            this.character.setDx(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S){
            this.character.setDy(0);
        }
        this.character.setRunFrameIndex(0);
    }
}
