package inputs;

import Game.Game;
import Game.GameStates;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyKeyboardListener implements KeyListener {

    private Game game;

    public MyKeyboardListener(Game game){
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(GameStates.gameState ==GameStates.EDITING){
            game.getEditing().keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
