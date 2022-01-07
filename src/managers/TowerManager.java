package managers;

import helpz.LoadSave;
import objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helpz.Constants.Towers.*;

public class TowerManager {

    private Playing playing;
    private BufferedImage[] towerImgs;
    private ArrayList<Tower> towers = new ArrayList<>();
    private int towerAmount =0;

    public TowerManager(Playing playing) {
        this.playing = playing;

        loadTowerImgs();
    }

    private void loadTowerImgs() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        towerImgs = new BufferedImage[3];

        for(int i=0; i<3;i++)
            towerImgs[i] = atlas.getSubimage((4 + i) * 32, 32,32,32);
    }

    public void draw(Graphics g){
       // g.drawImage(towerImgs[ARCHER],tower.getX(), tower.getY(),null);
        for(Tower t: towers){
            g.drawImage(towerImgs[t.getTowerType()],t.getX(), t.getY(),null);
        }
    }

    public void update() {
    }

    public BufferedImage[] getTowerImgs(){
        return towerImgs;
    }

    public void addTower(Tower selectedTower, int xPos, int yPos) {
        towers.add(new Tower(xPos,yPos, towerAmount++,selectedTower.getTowerType()));
    }

    public Tower getTowerAt(int x, int y) {
        for (Tower t : towers){
            if(t.getX() == x && t.getY() == y)
                return t;
        }

        return null;
    }
}
