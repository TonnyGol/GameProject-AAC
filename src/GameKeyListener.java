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
        if(!this.player.isCharacterReloading()){
            if (keyCode == KeyEvent.VK_W){
                this.wPressed = true;
                this.player.setCharacterMovingRight(true);
                this.makeRunOnSandSound();
                this.player.setDy(-this.player.getCHARACTER_SPEED());
            }
            if (keyCode == KeyEvent.VK_S){
                this.sPressed = true;
                this.player.setCharacterMovingRight(true);
                this.makeRunOnSandSound();
                this.player.setDy(this.player.getCHARACTER_SPEED());
            }
            if (keyCode == KeyEvent.VK_D){
                this.dPressed = true;
                this.player.setCharacterMovingRight(true);
                this.player.setCharacterMovingLeft(false);
                this.makeRunOnSandSound();
                this.player.setDx(this.player.getCHARACTER_SPEED());
            }
            if (keyCode == KeyEvent.VK_A){
                this.aPressed = true;
                this.player.setCharacterMovingRight(true);
                this.player.setCharacterMovingLeft(true);
                this.makeRunOnSandSound();
                this.player.setDx(-this.player.getCHARACTER_SPEED());
            }
            if (keyCode == KeyEvent.VK_SPACE){
                this.spacePressed = true;
                this.player.setCharacterAttacking(true);
                if(player.isCharacterMovingLeft()){
                    this.player.setDx(-this.player.getCHARACTER_SPEED());
                }else{
                    this.player.setDx(this.player.getCHARACTER_SPEED());
                }
            }
        }

    }

    private void makeRunOnSandSound(){
        this.gamePanel.getMusicPlayer().getRunOnSandClip().start();
        this.gamePanel.getMusicPlayer().getRunOnSandClip().loop(Clip.LOOP_CONTINUOUSLY);
    }

    @Override
    public void keyReleased(KeyEvent e){
        int keyCode = e.getKeyCode();
        switch (keyCode){
            case KeyEvent.VK_W -> this.wPressed = false;
            case KeyEvent.VK_S -> this.sPressed = false;
            case KeyEvent.VK_D -> this.dPressed = false;
            case KeyEvent.VK_A -> this.aPressed = false;
            case KeyEvent.VK_SPACE -> this.spacePressed = false;
            default -> System.out.println("A key released");
        }

        if (!this.aPressed && !this.dPressed){
            this.player.setDx(0);
        }
        if(!this.sPressed && !this.wPressed){
            this.player.setDy(0);
        }
        if (!this.spacePressed){
            this.player.setCharacterAttacking(false);
        }

        if (!aPressed && !wPressed && !sPressed && !dPressed && !spacePressed){
            this.player.setCharacterAttacking(false);
            this.player.setCharacterMovingRight(false);
            this.player.setCharacterStanding(true);
            this.player.setRunFrameIndex(0);
            this.player.setAttackFrameIndex(0);
            this.gamePanel.getMusicPlayer().getRunOnSandClip().setMicrosecondPosition(0);
            this.gamePanel.getMusicPlayer().getRunOnSandClip().stop();
        }
    }
}
