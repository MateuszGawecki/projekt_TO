package ui;

import objects.Tile;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static Game.GameStates.MENU;
import static Game.GameStates.setGameState;

public class BottomBar {

    private int x, y, width, height;
    private MyButton bMenu;
    private Playing playing;

    private ArrayList<MyButton> tileButtons = new ArrayList<>();

    public BottomBar(int x, int y, int width, int height, Playing playing) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.playing=playing;

        initButtons();
    }

    private void initButtons() {
        bMenu = new MyButton("Menu", 2, 642, 100, 30);

        int w=50;
        int h=50;
        int xStart = 110;
        int yStart = 650;
        int xOffset = (int) (w*1.1f);

        int i=0;
        for(Tile tile : playing.getTileManager().tiles){
            tileButtons.add(new MyButton(tile.getName(),xStart + xOffset * i,yStart,w,h, i));
            i++;
        }
    }

    public void draw(Graphics g){
        g.setColor(new Color(222,123,15));
        g.fillRect(x,y,width,height);

        drawButtons(g);
    }

    private void drawButtons(Graphics g) {
        bMenu.draw(g);

        drawTileButtons(g);
    }

    private void drawTileButtons(Graphics g) {
        for(MyButton b: tileButtons){
            g.drawImage(getButtImgb(b.getId()),b.getX(),b.getY(),b.getWidth(),b.getHeight(),null);

            //Mouseover
            if(b.isMouseOver()){
                g.setColor(Color.WHITE);
            }else{
                g.setColor(Color.BLACK);
            }

            //Border
            g.drawRect(b.getX(),b.getY(), b.getWidth(), b.getHeight());

            if(b.isMousePressed()){
                //MousePressed
                g.drawRect(b.getX()+1,b.getY()+1, b.getWidth()-2, b.getHeight()-2);
                g.drawRect(b.getX()+2,b.getY()+2, b.getWidth()-4, b.getHeight()-4);
            }
        }
    }

    private BufferedImage getButtImgb(int id) {
        return playing.getTileManager().getSprite(id);
    }


    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y))
            setGameState(MENU);
    }


    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        for(MyButton b : tileButtons) {
            b.setMouseOver(false);
        }

        if (bMenu.getBounds().contains(x, y))
            bMenu.setMouseOver(true);
        else{
            for(MyButton b : tileButtons){
                if(b.getBounds().contains(x,y)){
                    b.setMouseOver(true);
                    return;
                }
            }
        }
    }

    public void mousePressed(int x, int y) {
        if (bMenu.getBounds().contains(x, y))
            bMenu.setMousePressed(true);
        else{
            for(MyButton b : tileButtons){
                if(b.getBounds().contains(x,y)){
                    b.setMousePressed(true);
                    return;
                }
            }
        }
    }


    public void mouseReleased(int x, int y) {
        bMenu.resetBooleans();

        for(MyButton b: tileButtons){
            b.resetBooleans();
        }
    }
}
