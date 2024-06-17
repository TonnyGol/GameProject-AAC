import javax.sound.sampled.Clip;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyListener implements KeyListener {
    private GamePanel gamePanel;
    private Player player;
    private WindowFrame windowFrame;
    private boolean wPressed, sPressed, aPressed, dPressed, spacePressed;

    public GameKeyListener(GamePanel gamePanel, Player player, WindowFrame windowFrame){
        this.gamePanel = gamePanel;
        this.player = player;
        this.windowFrame = windowFrame;
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

        if (keyCode == KeyEvent.VK_ESCAPE) {
            this.windowFrame.setPanelChoice(0);
            this.windowFrame.setSwitchPanels(true);
        }
        if(!this.player.isCharacterReloading() && this.player.isAlive()){
            switch (keyCode){
                case KeyEvent.VK_W ->{
                    this.wPressed = true;
                    this.player.setCharacterMovingRight(true);
                    this.makeRunOnSandSound();
                    this.player.setDy(-this.player.getPLAYER_SPEED());
                }
                case KeyEvent.VK_S ->{
                    this.sPressed = true;
                    this.player.setCharacterMovingRight(true);
                    this.makeRunOnSandSound();
                    this.player.setDy(this.player.getPLAYER_SPEED());
                }
                case KeyEvent.VK_D ->{
                    this.dPressed = true;
                    this.player.setCharacterMovingRight(true);
                    this.player.setCharacterMovingLeft(false);
                    this.makeRunOnSandSound();
                    this.player.setDx(this.player.getPLAYER_SPEED());
                }
                case KeyEvent.VK_A ->{
                    this.aPressed = true;
                    this.player.setCharacterMovingRight(true);
                    this.player.setCharacterMovingLeft(true);
                    this.makeRunOnSandSound();
                    this.player.setDx(-this.player.getPLAYER_SPEED());
                }
                case KeyEvent.VK_SPACE -> {
                    this.spacePressed = true;
                    this.player.setCharacterAttacking(true);
                    if(player.isCharacterMovingLeft()){
                        this.player.setDx(-this.player.getPLAYER_SPEED());
                    }else{
                        this.player.setDx(this.player.getPLAYER_SPEED());
                    }
                }
                default -> System.out.println("A Key pressed " + e.getKeyChar());
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
            default -> System.out.println("A key released " + e.getKeyChar());
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
