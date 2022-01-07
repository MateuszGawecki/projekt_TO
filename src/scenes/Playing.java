package scenes;

import Game.Game;
import helpz.LoadSave;
import managers.EnemyManager;
import objects.PathPoint;
import ui.ActionBar;

import java.awt.*;
import java.util.ArrayList;

public class Playing extends GameScene implements SceneMethods{

    private int[][] lvl;
    private ActionBar actionBar;
    private PathPoint start,end;

    private int mouseX, mouseY;

    private EnemyManager enemyManager;

    public Playing(Game game) {
        super(game);

        loadDefaultLevel();
        actionBar = new ActionBar(0,640, 640,160, this);

        enemyManager = new EnemyManager(this,start,end);
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
    }

    public void update(){
        updateTick();
        enemyManager.update();
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

    @Override
    public void mouseClicked(int x, int y) {
        if(y>=640) {
            actionBar.mouseClicked(x, y);
        }
//        else
//            enemyManager.addEnemy(x,y);
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
}
