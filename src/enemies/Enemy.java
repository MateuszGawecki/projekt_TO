package enemies;

import helpz.Constants;
import managers.EnemyManager;
import java.awt.*;

import static helpz.Constants.Direction.*;

public abstract class Enemy {
    protected float x, y;
    protected int slowTickLimit = 120;
    protected int slowTick = slowTickLimit;
    private Rectangle bounds;
    private int health, maxHealth, Id, enemyType, lastDir;
    private boolean alive = true;
    private EnemyManager enemyManager;

    public Enemy(float x, float y, int Id, int enemyType, EnemyManager enemyManager) {
        this.x = x;
        this.y = y;
        this.Id = Id;
        this.enemyType = enemyType;
        this.bounds = new Rectangle((int) x, (int) y, 32, 32);
        this.enemyManager = enemyManager;
        lastDir = -1;
        setStartHealth();
    }

    public void setPos(int x, int y){
        //for pos fix
        this.x = x;
        this.y = y;
    }

    public float getHealthBarFloat(){
        return health /  (float) maxHealth;
    }

    public void move(float speed, int dir){
        lastDir = dir;

        if(slowTick <  slowTickLimit){
            slowTick++;
            speed *= 0.5f;
        }

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

        updateHitBox();
    }

    public boolean isSlowed(){
        return slowTick < slowTickLimit;
    }

    public void hurt(int dmg) {
        this.health-=dmg;
        if(health <= 0) {
            alive = false;
            enemyManager.rewardPlayer(enemyType);
        }
    }

    public void slow() {
        slowTick = 0;
    }

    public void kill() {
        //for killing enemy at end of road
        alive = false;
        health = 0;
    }

    private void setStartHealth(){
        health = Constants.Enemies.GetStartHealth(enemyType);
        maxHealth = health;
    }


    private void updateHitBox() {
        bounds.x = (int) x;
        bounds.y = (int) y;
    }


    public boolean isAlive(){
        return alive;
    }

    public void setLastDir(int newDir) {
        this.lastDir = newDir;
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

    public void setHealth(int health) {
        this.health = health;
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
