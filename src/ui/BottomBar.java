package ui;

import java.awt.*;

import static Game.GameStates.MENU;
import static Game.GameStates.setGameState;

public class BottomBar {

    private int x, y, width, height;
    private MyButton bMenu;

    public BottomBar(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

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
