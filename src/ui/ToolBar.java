package ui;

import objects.Tile;
import scenes.Editing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static Game.GameStates.MENU;
import static Game.GameStates.setGameState;

public class ToolBar extends Bar{
    private MyButton bMenu, bSave;

    private ArrayList<MyButton> tileButtons = new ArrayList<>();

    private Tile selectedTile;
    private Editing editing;

    public ToolBar(int x, int y, int width, int height, Editing editing) {
        super(x, y, width, height);
        this.editing = editing;

        initButtons();
    }

    public void draw(Graphics g){
        g.setColor(new Color(222,123,15));
        g.fillRect(x,y,width,height);

        drawButtons(g);
    }

    private void initButtons() {
        bMenu = new MyButton("Menu", 2, 642, 100, 30);
        bSave = new MyButton("Save", 2, 674, 100, 30);

        int w=50;
        int h=50;
        int xStart = 110;
        int yStart = 650;
        int xOffset = (int) (w*1.1f);

        int i=0;
        for(Tile tile : editing.getGame().getTileManager().tiles){
            tileButtons.add(new MyButton(tile.getName(),xStart + xOffset * i,yStart,w,h, i));
            i++;
        }
    }

    private void drawButtons(Graphics g) {
        bMenu.draw(g);
        bSave.draw(g);

        drawTileButtons(g);

        drawSelectedTile(g);
    }

    private void drawSelectedTile(Graphics g) {
        if(selectedTile != null){
            g.drawImage(selectedTile.getSprite(),550, 650, 50,50,null);
            g.setColor(Color.BLACK);
            g.drawRect(550, 650,50,50);
        }
    }

    private void drawTileButtons(Graphics g) {
        for(MyButton b: tileButtons){
            g.drawImage(getButtImg(b.getId()),b.getX(),b.getY(),b.getWidth(),b.getHeight(),null);

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

    private BufferedImage getButtImg(int id) {
        return editing.getGame().getTileManager().getSprite(id);
    }

    public void mouseClicked(int x, int y) {
        if (bMenu.getBounds().contains(x, y))
            setGameState(MENU);
        else if(bSave.getBounds().contains(x,y))
            saveLevel();
        else{
            for(MyButton b : tileButtons){
                if(b.getBounds().contains(x,y)){
                    selectedTile = editing.getGame().getTileManager().getTile(b.getId());
                    editing.setSelectedTile(selectedTile);
                    return;
                }
            }
        }
    }

    private void saveLevel() {
        editing.saveLevel();
    }

    public void mouseMoved(int x, int y) {
        bMenu.setMouseOver(false);
        bSave.setMouseOver(false);
        for(MyButton b : tileButtons) {
            b.setMouseOver(false);
        }

        if (bMenu.getBounds().contains(x, y))
            bMenu.setMouseOver(true);
        else if(bSave.getBounds().contains(x,y))
            bSave.setMouseOver(true);
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
        else if(bSave.getBounds().contains(x,y))
            bSave.setMousePressed(true);
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
        bSave.resetBooleans();

        for(MyButton b: tileButtons){
            b.resetBooleans();
        }
    }
}
