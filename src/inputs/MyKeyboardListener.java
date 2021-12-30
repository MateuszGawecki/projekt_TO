package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyKeyboardListener implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_A)
            System.out.println("A is presed");

        else if(e.getKeyCode() == KeyEvent.VK_B)
            System.out.println("B is presed");
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
