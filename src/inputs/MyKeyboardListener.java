package inputs;

import Game.GameStates;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyKeyboardListener implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_A)
            GameStates.gameState = GameStates.MENU;

        else if(e.getKeyCode() == KeyEvent.VK_S)
            GameStates.gameState = GameStates.PLAYING;

        else if(e.getKeyCode() == KeyEvent.VK_D)
            GameStates.gameState = GameStates.SETTINGS;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
