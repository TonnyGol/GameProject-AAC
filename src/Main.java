import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        new WindowFrame();
    }

    public static List<Image> loadFrames(int frames, String fileName){
        List<Image> frameList = new ArrayList<>(frames);
        for (int i = 1; i <= frames; i++){
            Image frame = new ImageIcon(fileName+i+".png").getImage();
            frameList.add(frame);
        }
        return frameList;
    }

    public static JLabel createButtonLabel(String text, String filePath, ButtonListener buttonListener) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(text,Font.PLAIN,0));
        label.setIcon(new ImageIcon(filePath)); // Set the icon for the label
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.CENTER);
        label.setForeground(Color.ORANGE);
        label.setName(text);

        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label.addMouseListener(buttonListener);
        return label;
    }

    public static void sleep(long millisecond){
        try {
            Thread.sleep(millisecond);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}