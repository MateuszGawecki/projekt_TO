package enemies;

import java.awt.*;

public class Enemy {

    private float x, y;
    private Rectangle bounds;
    private int health;
    private int Id;
    private int enemyType;

    public Enemy(float x, float y,int Id, int enemyType) {
        this.x = x;
        this.y = y;
        this.Id = Id;
        this.enemyType = enemyType;
        this.bounds = new Rectangle((int) x, (int) y, 32, 32);
    }

    public void move(float x, float y){
        this.x +=x;
        this.y +=y;
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
}
