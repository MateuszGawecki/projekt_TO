package ui;

import inputs.MyKeyboardListener;
import objects.Tile;
import scenes.Editing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static Game.GameStates.MENU;
import static Game.GameStates.setGameState;

public class ToolBar extends Bar{
    private MyButton bMenu, bSave;

//    private ArrayList<MyButton> tileButtons = new ArrayList<>();

    private Map<MyButton, ArrayList<Tile>> map = new HashMap<>();

    private MyButton bGrass, bWater, bRoadS, bRoadC, bWaterC, bWaterB, bWaterI;
    private MyButton currentButton;

    private int currentIndex;

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

    public void rotateSprite(){
        currentIndex++;

        if(currentIndex>=map.get(currentButton).size()){
            currentIndex=0;
        }

        selectedTile = map.get(currentButton).get(currentIndex);
        editing.setSelectedTile(selectedTile);
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

        bGrass = new MyButton("Grass", xStart, yStart, w,h,i++);
        bWater = new MyButton("Water", xStart + xOffset, yStart, w,h,i++);

        initMapButton(bRoadS, editing.getGame().getTileManager().getRoadsS(), xStart,yStart,xOffset,w,h,i++);
        initMapButton(bRoadC, editing.getGame().getTileManager().getRoadsC(), xStart,yStart,xOffset,w,h,i++);
        initMapButton(bWaterC, editing.getGame().getTileManager().getCorners(), xStart,yStart,xOffset,w,h,i++);
        initMapButton(bWaterB, editing.getGame().getTileManager().getBeaches(), xStart,yStart,xOffset,w,h,i++);
        initMapButton(bWaterI, editing.getGame().getTileManager().getIslands(), xStart,yStart,xOffset,w,h,i++);

    }

    private void initMapButton(MyButton b, ArrayList<Tile> list, int x, int y, int xOff, int w, int h, int id){
        b = new MyButton("", x+xOff*id, y,w,h,id);
        map.put(b,list);
    }

    private void drawButtons(Graphics g) {
        bMenu.draw(g);
        bSave.draw(g);

//        drawTileButtons(g);

        drawNormalButton(g,bGrass);
        drawNormalButton(g,bWater);
        drawSelectedTile(g);
        drawMapButtons(g);
    }

    private void drawNormalButton(Graphics g, MyButton b) {
        g.drawImage(getButtImg(b.getId()),b.getX(),b.getY(),b.getWidth(),b.getHeight(),null);

        drawButtonFeedBack(g,b);
    }

    private void drawMapButtons(Graphics g) {

        for(Map.Entry<MyButton, ArrayList<Tile>> entry : map.entrySet()){
            MyButton b = entry.getKey();
            BufferedImage img = entry.getValue().get(0).getSprite();

            g.drawImage(img, b.getX(), b.getY(),b.getWidth(),b.getHeight(),null);

            drawButtonFeedBack(g,b);
        }
    }

    private void drawButtonFeedBack(Graphics g, MyButton b){
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

    private void drawSelectedTile(Graphics g) {
        if(selectedTile != null){
            g.drawImage(selectedTile.getSprite(),550, 650, 50,50,null);
            g.setColor(Color.BLACK);
            g.drawRect(550, 650,50,50);
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
        else if(bWater.getBounds().contains(x,y)){
            selectedTile = editing.getGame().getTileManager().getTile(bWater.getId());
            editing.setSelectedTile(selectedTile);
        }else if(bGrass.getBounds().contains(x,y)){
            selectedTile = editing.getGame().getTileManager().getTile(bGrass.getId());
            editing.setSelectedTile(selectedTile);
        }
        else{
            for(MyButton b : map.keySet()){
                if(b.getBounds().contains(x,y)){
                    selectedTile = map.get(b).get(0);
                    editing.setSelectedTile(selectedTile);
                    currentButton = b;
                    currentIndex = 0;
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
        bWater.setMouseOver(false);
        bGrass.setMouseOver(false);

        for(MyButton b : map.keySet()) {
            b.setMouseOver(false);
        }

        if (bMenu.getBounds().contains(x, y))
            bMenu.setMouseOver(true);
        else if(bSave.getBounds().contains(x,y))
            bSave.setMouseOver(true);
        else if(bWater.getBounds().contains(x,y))
            bWater.setMouseOver(true);
        else if(bGrass.getBounds().contains(x,y))
            bGrass.setMouseOver(true);
        else{
            for(MyButton b : map.keySet()){
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
        else if(bWater.getBounds().contains(x,y))
            bWater.setMousePressed(true);
        else if(bGrass.getBounds().contains(x,y))
            bGrass.setMousePressed(true);
        else{
            for(MyButton b : map.keySet()){
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
        bWater.resetBooleans();
        bGrass.resetBooleans();

        for(MyButton b: map.keySet()){
            b.resetBooleans();
        }
    }
}
