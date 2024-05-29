import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        new WindowFrame();
    }

    public static void sleep(long milisec){
        try {
            Thread.sleep(milisec);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
