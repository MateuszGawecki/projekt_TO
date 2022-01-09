package scenes;

import Game.Game;
import ui.MyButton;

import java.awt.*;

import static Game.GameStates.*;

public class Menu extends GameScene implements SceneMethods{

    private MyButton bPlaying, bSettings, bQuit, bEditing;

    public Menu(Game game) {
        super(game);
        initButtons();
    }

    private void initButtons(){
        int w = 150;
        int h = w/3;
        int x = 640 / 2 - w / 2;
        int y = 150;
        int yOffset = 100;

        bPlaying = new MyButton("Play", x,y,w,h);
        bEditing = new MyButton("Editing", x,y + yOffset,w,h);
        bSettings = new MyButton("Settings", x,y + yOffset *2,w,h);
        bQuit = new MyButton("Quit", x,y + yOffset * 3,w,h);
    }

    @Override
    public void render(Graphics g) {
        drawButtons(g);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(bPlaying.getBounds().contains(x,y)) {
            SetGameState(PLAYING);
        } else if(bEditing.getBounds().contains(x,y)){
            SetGameState(EDITING);
        } else if(bSettings.getBounds().contains(x,y)){
            SetGameState(SETTINGS);
        } else if(bQuit.getBounds().contains(x, y)){
            System.exit(0);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        bPlaying.setMouseOver(false);
        bEditing.setMouseOver(false);
        bSettings.setMouseOver(false);
        bQuit.setMouseOver(false);

        if(bPlaying.getBounds().contains(x,y)) {
            bPlaying.setMouseOver(true);
        } else if(bEditing.getBounds().contains(x,y)){
            bEditing.setMouseOver(true);
        } else if(bSettings.getBounds().contains(x, y)){
            bSettings.setMouseOver(true);
        } else if(bQuit.getBounds().contains(x, y)){
            bQuit.setMouseOver(true);
        }
    }

    @Override
    public void mousePressed(int x, int y) {

        if(bPlaying.getBounds().contains(x,y)) {
            bPlaying.setMousePressed(true);
        } else if(bEditing.getBounds().contains(x,y)){
            bEditing.setMousePressed(true);
        } else if(bSettings.getBounds().contains(x, y)){
            bSettings.setMousePressed(true);
        } else if(bQuit.getBounds().contains(x, y)){
            bQuit.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        resetButtons();
    }

    @Override
    public void mouseDragged(int x, int y) {

    }

    private void resetButtons() {
        bPlaying.resetBooleans();
        bEditing.resetBooleans();
        bSettings.resetBooleans();
        bQuit.resetBooleans();
    }

    private void drawButtons(Graphics g){
        bPlaying.draw(g);
        bEditing.draw(g);
        bSettings.draw(g);
        bQuit.draw(g);
    }
}
