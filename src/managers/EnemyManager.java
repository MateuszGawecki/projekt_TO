package managers;

import enemies.Enemy;
import helpz.LoadSave;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helpz.Constants.Direction.*;
import static helpz.Constants.Tiles.*;

public class EnemyManager {

    private Playing playing;
    private BufferedImage[] enemyImgs;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private float speed = 0.5f;

    public EnemyManager(Playing playing){
        this.playing= playing;
        this.enemyImgs = new BufferedImage[4];
        addEnemy(3*32,9*32);
        loadEnemyImgs();
    }

    private void loadEnemyImgs() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        enemyImgs[0] = atlas.getSubimage(0,32,32,32);
        enemyImgs[1] = atlas.getSubimage(1,32,32,32);
        enemyImgs[2] = atlas.getSubimage(2 * 32,32,32,32);
        enemyImgs[3] = atlas.getSubimage(3 *32,32,32,32);
    }

    public void update(){
        for(Enemy e : enemies){
            //is next tile road(pos,dir)
            if(isNextTileRoad(e)){
                //moveenemy
            }
        }
    }

    private boolean isNextTileRoad(Enemy e) {
        //e pos
        //e dir
        //tile at new possible pos
        int newX = (int) (e.getX() + getSpeedAndWidth(e.getLastDir()));
        int newY = (int) (e.getY() + getSpeedAndHeight(e.getLastDir()));

        if(getTileType(newX,newY) == ROAD_TIlE){
            e.move(speed,e.getLastDir());
        }else if(isAtEnd(e)){

        } else {
            setNewDirectionAndMove(e);
        }

        return false;
    }

    private void setNewDirectionAndMove(Enemy e) {
        int dir = e.getLastDir();

        //move into the current tile 100%
        int xCord = (int) (e.getX()/32);
        int yCord = (int) (e.getY()/32);

        fixEnemyOffsetTile(e,dir, xCord,yCord);

        if(dir == LEFT || dir == RIGHT){

            int newY = (int) (e.getY() + getSpeedAndHeight(UP));
            if(getTileType((int) e.getX(), newY) == ROAD_TIlE)
                e.move(speed,UP);
            else
                e.move(speed,DOWN);

        }else {

            int newX = (int) (e.getX() + getSpeedAndHeight(RIGHT));
            if(getTileType(newX, (int) e.getY()) == ROAD_TIlE)
                e.move(speed,RIGHT);
            else
                e.move(speed,LEFT);
        }
    }

    private void fixEnemyOffsetTile(Enemy e, int dir, int xCord, int yCord) {
        switch (dir){
//            case LEFT:
//                if(xCord >0)
//                    xCord--;
//                break;
//            case UP:
//                if(yCord>0)
//                    yCord--;
//                break;
            case RIGHT:
                if(xCord<19)
                    xCord++;
                break;
            case DOWN:
                if(yCord<19)
                    yCord++;
                break;
            default:
                break;
        }

        e.setPos(xCord * 32,yCord * 32);
    }

    private boolean isAtEnd(Enemy e) {
        return false;
    }

    private int getTileType(int x, int y) {
        return playing.getTileType(x,y);
    }

    private float getSpeedAndHeight(int lastDir) {
        if(lastDir == UP)
            return -speed;
        else if(lastDir == DOWN)
            return speed + 32;

        return 0;
    }

    private float getSpeedAndWidth(int lastDir) {
        if(lastDir == LEFT)
            return -speed;
        else if(lastDir == RIGHT)
            return speed + 32;

        return 0;
    }

    public void addEnemy(int x, int y){
        enemies.add(new Enemy(x, y,0,0));
    }

    public void draw(Graphics g){
        for(Enemy e: enemies){
            drawEnemy(g,e);
        }
    }

    private void drawEnemy(Graphics g, Enemy testEnemy) {
        g.drawImage(enemyImgs[0], (int) testEnemy.getX(), (int) testEnemy.getY(),null );
    }
}
