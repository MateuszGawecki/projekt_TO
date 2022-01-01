package enemies;

import java.awt.*;

import static helpz.Constants.Direction.*;

public class Enemy {

    private float x, y;
    private Rectangle bounds;
    private int health;
    private int Id;
    private int enemyType;
    private int lastDir;

    public Enemy(float x, float y,int Id, int enemyType) {
        this.x = x;
        this.y = y;
        this.Id = Id;
        this.enemyType = enemyType;
        this.bounds = new Rectangle((int) x, (int) y, 32, 32);
        lastDir = RIGHT;
    }

    public void setPos(int x, int y){
        //for pos fix
        this.x = x;
        this.y = y;
    }

    public void move(float speed, int dir){
        lastDir = dir;
        switch (dir){
            case LEFT:
                this.x -=speed;
                break;
            case UP:
                this.y -=speed;
                break;
            case DOWN:
                this.y+=speed;
                break;
            case RIGHT:
                this.x +=speed;
                break;
            default:
                break;
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getHealth() {
        return health;
    }

    public int getId() {
        return Id;
    }

    public int getEnemyType() {
        return enemyType;
    }

    public int getLastDir() {
        return lastDir;
    }
}
