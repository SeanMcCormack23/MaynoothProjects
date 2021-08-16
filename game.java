import java.util.*;
import textio.TextIO;
//
public class game implements Runnable {

        GUI gui = new GUI();
    public static void main(String[] args) {
        new Thread(new game()).start();
    }

    @Override
    public void run() {
        while(true){
            gui.repaint();
        }
    }
}//end game.java file 