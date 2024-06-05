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
            this.gamePanel.setCharacterMoving(true);
            this.character.setDy(-this.character.getCHARACTER_SPEED());
        } else if (keyCode == KeyEvent.VK_S) {
            this.gamePanel.setCharacterMoving(true);
            this.character.setDy(this.character.getCHARACTER_SPEED());
        } else if (keyCode == KeyEvent.VK_D){
            this.gamePanel.setCharacterMoving(true);
            this.gamePanel.setCharacterMovingBack(false);
            this.character.setDx(this.character.getCHARACTER_SPEED());
        } else if (keyCode == KeyEvent.VK_A){
            this.gamePanel.setCharacterMoving(true);
            this.gamePanel.setCharacterMovingBack(true);
            this.character.setDx(-this.character.getCHARACTER_SPEED());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.gamePanel.setCharacterMoving(false);
        this.character.setDy(0);
        this.character.setDx(0);
        this.character.setRunFrameIndex(0);
    }
}
