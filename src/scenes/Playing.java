package scenes;

import Game.Game;
import helpz.LoadSave;
import managers.EnemyManager;
import managers.TowerManager;
import objects.PathPoint;
import objects.Tower;
import ui.ActionBar;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static helpz.Constants.Tiles.*;

public class Playing extends GameScene implements SceneMethods{

    private int[][] lvl;
    private ActionBar actionBar;
    private PathPoint start,end;

    private int mouseX, mouseY;

    private EnemyManager enemyManager;
    private TowerManager towerManager;

    private Tower selectedTower;

    public Playing(Game game) {
        super(game);

        loadDefaultLevel();
        actionBar = new ActionBar(0,640, 640,160, this);

        enemyManager = new EnemyManager(this,start,end);
        towerManager = new TowerManager(this);
    }

    private void loadDefaultLevel() {
        lvl = LoadSave.getLevelData("new level");
        ArrayList<PathPoint> points = LoadSave.getLevelPathPoints("new level");
        start = points.get(0);
        end = points.get(1);
    }

    @Override
    public void render(Graphics g) {

        drawLevel(g);
        actionBar.draw(g);
        enemyManager.draw(g);
        towerManager.draw(g);
        drawSelectedTower(g);
        drawHighLight(g);
    }

    private void drawHighLight(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawRect(mouseX,mouseY,32,32);
    }

    private void drawSelectedTower(Graphics g) {
        if(selectedTower != null)
            g.drawImage(towerManager.getTowerImgs()[selectedTower.getTowerType()], mouseX,mouseY ,null);
    }

    public void update(){
        updateTick();
        enemyManager.update();
        towerManager.update();
    }

    private void drawLevel(Graphics g){
        for(int y=0 ; y<lvl.length; y++){
            for(int x=0; x<lvl[y].length; x++){
                int id = lvl[y][x];
                if(isAnimation(id)){
                    g.drawImage(getSprite(id, animationId), x *32,y *32,null);
                }else
                    g.drawImage(getSprite(id), x *32,y *32,null);
            }
        }
    }

    public void setLevel(int[][] lvl){
        this.lvl = lvl;
    }

    public int getTileType(int x, int y){
        int xCord = x/32;
        int yCord = y/32;

        if(xCord < 0 || xCord > 19)
            return 0;

        if(yCord < 0 || yCord > 19)
            return 0;

        int id = lvl[yCord][xCord];
        return getGame().getTileManager().getTile(id).getTileType();
    }

    public TowerManager getTowerManager(){
        return towerManager;
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(y>=640) {
            actionBar.mouseClicked(x, y);
        }else {
            if(selectedTower !=null){
                if(isTileGrass(mouseX,mouseY)){
                    if(getTowerAt(mouseX,mouseY) == null){
                        towerManager.addTower(selectedTower, mouseX, mouseY);
                        selectedTower = null;
                    }
                }
            }else {
                Tower t = getTowerAt(mouseX,mouseY);
                actionBar.displayTower(t);
            }
        }

    }

    private Tower getTowerAt(int mouseX, int mouseY) {
        return towerManager.getTowerAt(mouseX,mouseY);
    }

    private boolean isTileGrass(int x, int y) {
        int id = lvl[y/32][x/32];

        int tileType = getGame().getTileManager().getTile(id).getTileType();

        return tileType == GRASS_TILE;
    }

    @Override
    public void mouseMoved(int x, int y) {
        if(y>=640){
            actionBar.mouseMoved(x,y);
        }else {
            mouseX = (x / 32) * 32;
            mouseY = (y / 32) * 32;
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if(y>=640){
            actionBar.mousePressed(x,y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        actionBar.mouseReleased(x,y);
    }

    @Override
    public void mouseDragged(int x, int y) {

    }

    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            selectedTower = null;
        }
    }

    public void setSelectedTower(Tower selectedTower) {
        this.selectedTower = selectedTower;
    }
}
