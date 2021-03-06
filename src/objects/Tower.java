package objects;

import helpz.Constants;

import static helpz.Constants.Towers.*;

public class Tower {

    private int x, y, id, towerType, cdTick,dmg, tier;
    private float range, cooldown;

    public Tower(int x, int y, int id, int towerType) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.towerType = towerType;
        tier = 1;
        setDefaultDmg();
        setDefalutRange();
        setDefaultCooldown();
    }

    public void upgradeTower(){
        tier++;

        switch (towerType){
            case ARCHER:
                dmg+=2;
                range+=20;
                cooldown-=5;
                break;
            case CANNON:
                dmg+=5;
                range+=20;
                cooldown-=15;
                break;
            case WIZARD:
                range+=20;
                cooldown-=10;
                break;
        }
    }

    public int getTier(){
        return tier;
    }

    private void setDefaultCooldown() {
        cooldown = Constants.Towers.GetDefaultCooldown(towerType);
    }

    private void setDefalutRange() {
        range = Constants.Towers.GetDefaultRange(towerType);
    }

    private void setDefaultDmg() {
        dmg = Constants.Towers.GetStartDmg(towerType);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTowerType() {
        return towerType;
    }

    public int getDmg() {
        return dmg;
    }

    public float getRange() {
        return range;
    }

    public boolean isCooldownOver() {
        return cdTick >=cooldown;
    }

    public void resetCooldown() {
        cdTick = 0;
    }

    public void update(){
        cdTick++;
    }
}
