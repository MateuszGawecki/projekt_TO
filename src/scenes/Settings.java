package scenes;

import Game.Game;

import ui.MyButton;

import java.awt.*;
import java.util.ArrayList;

import static Game.GameDifficultyLvl.*;
import static Game.GameStates.*;

public class Settings extends GameScene implements SceneMethods{

    private MyButton bMenu;
    private ArrayList<MyButton> bLvls = new ArrayList<>();

    public Settings(Game game) {
        super(game);

        initButtons();
    }

    private void initButtons() {
        bMenu = new MyButton("Menu", 2, 2, 100, 30);
        int yPos = 40;

        bLvls.add(new MyButton("EASY",2, yPos += 40, 100,30));
        bLvls.add(new MyButton("NORMAL",2, yPos += 40, 100,30));
        bLvls.add(new MyButton("HARD",2, yPos += 40, 100,30));
        bLvls.add(new MyButton("BRUTAL",2, yPos += 40, 100,30));
    }

    @Override
    public void render(Graphics g) {
        drawButtons(g);
    }

    private void drawButtons(Graphics g) {
        bMenu.draw(g);

        for(MyButton b: bLvls)
            b.draw(g);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y))
            SetGameState(MENU);

        for(MyButton b: bLvls){
            if(b.getBounds().contains(x,y)) {
                SetDificultyLvl(GetDifLvl(b.getText()));
                getGame().getPlaying().resetEverything();
            }
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);

        for(MyButton b: bLvls){
            b.setMouseOver(false);
        }

        if (bMenu.getBounds().contains(x, y))
            bMenu.setMouseOver(true);

        for(MyButton b: bLvls){
            if(b.getBounds().contains(x,y))
                b.setMouseOver(true);
        }

    }

    @Override
    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y))
            bMenu.setMousePressed(true);

        for(MyButton b: bLvls){
            if(b.getBounds().contains(x,y))
                b.setMousePressed(true);
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
        bMenu.resetBooleans();

        for(MyButton b: bLvls){
            b.resetBooleans();
        }
    }
}
