package managers;

import enemies.*;
import helpz.LoadSave;
import objects.PathPoint;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helpz.Constants.Direction.*;
import static helpz.Constants.Enemies.*;
import static helpz.Constants.Tiles.*;

public class EnemyManager {

    private Playing playing;
    private BufferedImage[] enemyImgs;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private float speed = 0.5f;
    private PathPoint start, end;

    public EnemyManager(Playing playing, PathPoint start, PathPoint end){
        this.playing= playing;
        this.enemyImgs = new BufferedImage[4];
        this.start=start;
        this.end=end;
        addEnemy(ORC);
        addEnemy(BAT);
        addEnemy(KNIGHT);
        addEnemy(WOLF);
        loadEnemyImgs();
    }

    private void loadEnemyImgs() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();

        for(int i =0; i<4 ; i++){
            enemyImgs[i] = atlas.getSubimage(i*32,32,32,32);
        }
    }

    public void update(){
        for(Enemy e : enemies){
            //is next tile road(pos,dir)
            updateEnemyMove(e);
        }
    }

    private void updateEnemyMove(Enemy e) {
        if(e.getLastDir() == -1){
            setNewDirectionAndMove(e);
        }

        int newX = (int) (e.getX() + getSpeedAndWidth(e.getLastDir()));
        int newY = (int) (e.getY() + getSpeedAndHeight(e.getLastDir()));

        if(getTileType(newX,newY) == ROAD_TIlE){
            e.move(speed,e.getLastDir());
        }else if(isAtEnd(e)){
            System.out.println("Lives lost");
        } else {
            setNewDirectionAndMove(e);
        }
    }

    private void setNewDirectionAndMove(Enemy e) {
        int dir = e.getLastDir();

        //move into the current tile 100%
        int xCord = (int) (e.getX()/32);
        int yCord = (int) (e.getY()/32);

        fixEnemyOffsetTile(e,dir, xCord,yCord);

        if(isAtEnd(e))
            return;

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
        if(e.getX() == end.getxCord() *32)
            if(e.getY() == end.getyCord()*32)
                return true;

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

    public void addEnemy(int enemyType){

        int x = start.getxCord() *32;
        int y = start.getxCord() *32;

        switch (enemyType){
            case ORC:
                enemies.add(new Orc(x, y,0));
                break;
            case BAT:
                enemies.add(new Bat(x,y,1));
                break;
            case KNIGHT:
                enemies.add(new Knight(x, y,2));
                break;
            case WOLF:
                enemies.add(new Wolf(x, y,3));
                break;
            default:
                break;

        }
    }

    public void draw(Graphics g){
        for(Enemy e: enemies){
            drawEnemy(g,e);
        }
    }

    private void drawEnemy(Graphics g, Enemy enemy) {
        g.drawImage(enemyImgs[enemy.getEnemyType()], (int) enemy.getX(), (int) enemy.getY(),null );
    }
}
