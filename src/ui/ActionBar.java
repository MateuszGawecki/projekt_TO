package ui;

import objects.Tile;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static Game.GameStates.MENU;
import static Game.GameStates.setGameState;

public class ActionBar extends Bar{

    private MyButton bMenu;
    private Playing playing;

    public ActionBar(int x, int y, int width, int height, Playing playing) {
        super(x,y,width,height);
        this.playing=playing;

        initButtons();
    }

    private void initButtons() {
        bMenu = new MyButton("Menu", 2, 642, 100, 30);
    }

    public void draw(Graphics g){
        g.setColor(new Color(222,123,15));
        g.fillRect(x,y,width,height);

        drawButtons(g);
    }

    private void drawButtons(Graphics g) {
        bMenu.draw(g);
    }

    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y))
            setGameState(MENU);

    }

    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);

        if (bMenu.getBounds().contains(x, y))
            bMenu.setMouseOver(true);
    }

    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y))
            bMenu.setMousePressed(true);
    }


    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();
    }
}
