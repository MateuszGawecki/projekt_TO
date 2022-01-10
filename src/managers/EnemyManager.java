package managers;

import enemies.*;
import helpz.LoadSave;
import helpz.Utilz;
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
    private PathPoint start, end;
    private int HpBarWidth = 20;
    private BufferedImage slowEffect;
    private int[][] roadDirArr;

    public EnemyManager(Playing playing, PathPoint start, PathPoint end){
        this.playing= playing;
        this.enemyImgs = new BufferedImage[4];
        this.start=start;
        this.end=end;
        loadEnemyImgs();
        loadEfectImg();
        loadRoadDirArr();

        //tempMethod();
    }

    public void reloadRoadDirArr(int [][] newArr){
        this.roadDirArr = newArr;
    }

    private void loadRoadDirArr() {
        roadDirArr = Utilz.GetRoadDirArr(playing.getGame().getTileManager().getTypeArr(), start,end);
    }

    private void tempMethod() {
        int[][] arr = Utilz.GetRoadDirArr(playing.getGame().getTileManager().getTypeArr(), start,end);

        for(int j = 0; j< arr.length ; j++){
            for(int i = 0 ; i< arr[0].length ; i++)
                System.out.print(arr[j][i] + "|");
            System.out.println();
        }
    }

    private void loadEfectImg() {
        slowEffect = LoadSave.GetSpriteAtlas().getSubimage(32*9,32*2,32,32);
    }

    private void loadEnemyImgs() {
        BufferedImage atlas = LoadSave.GetSpriteAtlas();

        for(int i =0; i<4 ; i++){
            enemyImgs[i] = atlas.getSubimage(i*32,32,32,32);
        }
    }

    public void update(){
        for(Enemy e : enemies){
            if(e.isAlive()){
                //updateEnemyMove(e);
                updateEnemyMoveNew(e);
            }
        }
    }

    private void updateEnemyMoveNew(Enemy e) {
        PathPoint currTile = getEnemyTile(e);
        int dir = roadDirArr[currTile.getyCord()][currTile.getxCord()];

        e.move(GetSpeed(e.getEnemyType()), dir);

        PathPoint newTile = getEnemyTile(e);
        if(isTileTheSame(newTile, end)){
            e.kill();
            playing.removeOneLife();
        }

        if(!isTileTheSame(currTile,newTile)){
            int newDir = roadDirArr[newTile.getyCord()][newTile.getxCord()];
            if(newDir != dir){
                e.setPos(newTile.getxCord() * 32, newTile.getyCord() * 32);
                e.setLastDir(newDir);
            }
        }
    }

    private PathPoint getEnemyTile(Enemy e) {
        switch (e.getLastDir()){
            case LEFT:
                return new PathPoint((int)((e.getX() + 31) / 32), (int) (e.getY() / 32));
            case UP:
                return new PathPoint((int)(e.getX() / 32), (int) ((e.getY() + 31) / 32));
            case RIGHT:
            case DOWN:
                return new PathPoint((int)(e.getX() / 32), (int) (e.getY() / 32));
        }

        return new PathPoint((int)(e.getX() / 32), (int) (e.getY() / 32));
    }

    private boolean isTileTheSame(PathPoint currTile, PathPoint newTile) {
        return currTile.getxCord() == newTile.getxCord() && currTile.getyCord() == newTile.getyCord();
    }

    private void updateEnemyMove(Enemy e) {
        if(e.getLastDir() == -1){
            setNewDirectionAndMove(e);
        }

        int newX = (int) (e.getX() + getSpeedAndWidth(e.getLastDir(), e.getEnemyType()));
        int newY = (int) (e.getY() + getSpeedAndHeight(e.getLastDir(), e.getEnemyType()));

        if(getTileType(newX,newY) == ROAD_TIlE){
            e.move(GetSpeed(e.getEnemyType()),e.getLastDir());
        }else if(isAtEnd(e)){
            e.kill();
            playing.removeOneLife();
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

            int newY = (int) (e.getY() + getSpeedAndHeight(UP,e.getEnemyType()));
            if(getTileType((int) e.getX(), newY) == ROAD_TIlE)
                e.move(GetSpeed(e.getEnemyType()),UP);
            else
                e.move(GetSpeed(e.getEnemyType()),DOWN);

        }else {

            int newX = (int) (e.getX() + getSpeedAndHeight(RIGHT,e.getEnemyType()));
            if(getTileType(newX, (int) e.getY()) == ROAD_TIlE)
                e.move(GetSpeed(e.getEnemyType()),RIGHT);
            else
                e.move(GetSpeed(e.getEnemyType()),LEFT);
        }
    }

    private void fixEnemyOffsetTile(Enemy e, int dir, int xCord, int yCord) {
        switch (dir){
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

    private float getSpeedAndHeight(int lastDir, int enemyType) {
        if(lastDir == UP)
            return -GetSpeed(enemyType);
        else if(lastDir == DOWN)
            return GetSpeed(enemyType) + 32;

        return 0;
    }

    private float getSpeedAndWidth(int lastDir, int enemyType) {
        if(lastDir == LEFT)
            return -GetSpeed(enemyType);
        else if(lastDir == RIGHT)
            return GetSpeed(enemyType) + 32;

        return 0;
    }

    public void addEnemy(int enemyType){

        int x = start.getxCord() *32;
        int y = start.getyCord() *32;

        switch (enemyType){
            case ORC:
                enemies.add(new Orc(x, y,0, this));
                break;
            case BAT:
                enemies.add(new Bat(x,y,1,this));
                break;
            case KNIGHT:
                enemies.add(new Knight(x, y,2,this));
                break;
            case WOLF:
                enemies.add(new Wolf(x, y,3,this));
                break;
            default:
                break;

        }
    }

    public void draw(Graphics g){
        for(Enemy e: enemies){
            if(e.isAlive()){
                drawEnemy(g,e);
                drawHealthBar(g,e);
                drawEffects(g,e);
            }
        }
    }

    private void drawEffects(Graphics g, Enemy e) {
        if(e.isSlowed()){
            g.drawImage(slowEffect, (int) e.getX(),(int) e.getY(),null);
        }
    }

    private void drawHealthBar(Graphics g, Enemy e) {
        g.setColor(Color.RED);
        g.fillRect((int)e.getX() + 16 - (getNewBarWidth(e)/2),(int)e.getY() - 10,getNewBarWidth(e),3);
    }

    private int getNewBarWidth(Enemy e){
        return  (int) (HpBarWidth * e.getHealthBarFloat());
    }

    private void drawEnemy(Graphics g, Enemy enemy) {
        g.drawImage(enemyImgs[enemy.getEnemyType()], (int) enemy.getX(), (int) enemy.getY(),null );
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void spawnEnemy(int nextEnemy) {
        addEnemy(nextEnemy);
    }

    public int getAmountOfAliveEnemies() {
        int size = 0;

        for(Enemy e: enemies){
            if(e.isAlive()){
                size++;
            }
        }

        return size;
    }

    public void rewardPlayer(int enemyType) {
        playing.rewardPlayer(enemyType);
    }

    public void reset() {
        enemies.clear();
    }
}
