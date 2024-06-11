import javax.sound.sampled.Clip;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyListener implements KeyListener {
    GamePanel gamePanel;
    Player player;
    boolean wPressed, sPressed, aPressed, dPressed, spacePressed;

    public GameKeyListener(GamePanel gamePanel, Player soldier){
        this.gamePanel = gamePanel;
        this.player = soldier;
        this.wPressed = false;
        this.sPressed = false;
        this.aPressed = false;
        this.dPressed = false;
        this.spacePressed = false;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        this.player.setCharacterStanding(false);
        if(!this.player.isCharacterRecharging()){
            if (keyCode == KeyEvent.VK_W){
                this.wPressed = true;
                this.player.setCharacterMovingRight(true);
                MusicPlayer.runOnSandClip.start();
                MusicPlayer.runOnSandClip.loop(Clip.LOOP_CONTINUOUSLY);
                this.player.setDy(-this.player.getCHARACTER_SPEED());
            }

            if (keyCode == KeyEvent.VK_S){
                this.sPressed = true;
                this.player.setCharacterMovingRight(true);
                MusicPlayer.runOnSandClip.start();
                MusicPlayer.runOnSandClip.loop(Clip.LOOP_CONTINUOUSLY);
                this.player.setDy(this.player.getCHARACTER_SPEED());
            }

            if (keyCode == KeyEvent.VK_D){
                this.dPressed = true;
                this.player.setCharacterMovingRight(true);
                this.player.setCharacterMovingLeft(false);
                MusicPlayer.runOnSandClip.start();
                MusicPlayer.runOnSandClip.loop(Clip.LOOP_CONTINUOUSLY);
                this.player.setDx(this.player.getCHARACTER_SPEED());
            }

            if (keyCode == KeyEvent.VK_A){
                this.aPressed = true;
                this.player.setCharacterMovingRight(true);
                this.player.setCharacterMovingLeft(true);
                MusicPlayer.runOnSandClip.start();
                MusicPlayer.runOnSandClip.loop(Clip.LOOP_CONTINUOUSLY);
                this.player.setDx(-this.player.getCHARACTER_SPEED());
            }

            if (keyCode == KeyEvent.VK_SPACE){
                this.spacePressed = true;
                this.player.setCharacterMovingLeft(false);
                this.player.setCharacterMovingRight(false);
                this.player.setCharacterStanding(false);
                this.player.setCharacterSliding(true);
                if(player.isCharacterMovingLeft()){
                    this.player.setDx(-this.player.getCHARACTER_SPEED());
                }else{
                    this.player.setDx(this.player.getCHARACTER_SPEED());
                }

            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e){
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_S) {
            if (keyCode == KeyEvent.VK_W){
                this.wPressed = false;
            }else {
                this.sPressed = false;
            }
        }
        else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_A) {
            if (keyCode == KeyEvent.VK_D){
                this.dPressed = false;
            }else {
                this.aPressed = false;
            }
        }
        else if(keyCode == KeyEvent.VK_SPACE){
            this.spacePressed = false;
        }

        if (!this.aPressed && !this.dPressed){
            this.player.setDx(0);
        }
        if(!this.sPressed && !this.wPressed){
            this.player.setDy(0);
        }

        if (!aPressed && !wPressed && !sPressed && !dPressed && !spacePressed){
            this.player.setCharacterAttacking(false);
            this.player.setCharacterMovingRight(false);
            this.player.setCharacterStanding(true);
            this.player.setRunFrameIndex(0);
            this.player.setAttackFrameIndex(0);
            MusicPlayer.runOnSandClip.setMicrosecondPosition(0);
            MusicPlayer.runOnSandClip.stop();
        }
    }
}
