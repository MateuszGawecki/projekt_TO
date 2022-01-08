package managers;

import enemies.Enemy;
import helpz.Constants;
import helpz.LoadSave;
import objects.Projectile;
import objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helpz.Constants.Projectiles.*;
import static helpz.Constants.Towers.*;

public class ProjectileManager {
    private Playing playing;
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private BufferedImage[] proj_img;
    private int  projectileId = 0;

    public ProjectileManager(Playing playing) {
        this.playing = playing;
        importImgs();
    }
    private void importImgs(){
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        proj_img = new BufferedImage[3];

        for(int i=0; i<3;i++){
            proj_img[i] = atlas.getSubimage((7+i) * 32, 32,32,32);
        }
    }

    public void newProjectile(Tower t, Enemy e){
        int type = getProjTile(t);

        int xDist = (int) (t.getX() - e.getX());
        int yDist = (int) (t.getY() - e.getY());
        int totDist = Math.abs(xDist) + Math.abs(yDist);

        float xPer = (float) Math.abs(xDist) / totDist;

        float xSpeed = xPer * Constants.Projectiles.GetSpeed(type);
        float ySpeed = Constants.Projectiles.GetSpeed(type) - xSpeed;

        if(t.getX() > e.getX())
            xSpeed *= -1;
        if(t.getY() > e.getY())
            ySpeed *= -1;

        float arcValue = (float) Math.atan(yDist /(float) xDist);
        float rotate = (float) Math.toDegrees(arcValue);

        if(xDist < 0)
            rotate +=180;

        projectiles.add(new Projectile(t.getX() + 16 , t.getY() + 16, xSpeed, ySpeed,t.getDmg(),rotate, projectileId++, type));
    }

    private int getProjTile(Tower t) {
        switch (t.getTowerType()){
            case ARCHER:
                return ARROW;
            case CANNON:
                return BOMB;
            case WIZARD:
                return CHAINS;
            default:
                return -1;
        }
    }

    public void update(){
        for(Projectile p : projectiles){
            if(p.isActive()){
                p.move();
                if(isProjHittingEnemy(p)){
                    p.setActive(false);
                }
            }
        }
    }

    private boolean isProjHittingEnemy(Projectile p) {
        for(Enemy e : playing.getEnemyManager().getEnemies()){
            if(e.getBounds().contains(p.getPos())){
                e.hurt(p.getDmg());
                return true;
            }
        }
        return false;
    }

    public void draw(Graphics g){

        Graphics2D g2d = (Graphics2D) g;

        for(Projectile p : projectiles){
            if(p.isActive()) {
                g2d.translate(p.getPos().x, p.getPos().y);
                g2d.rotate(Math.toRadians(90));
                g2d.drawImage(proj_img[p.getProjectileType()],-16, -16, null);
                g2d.rotate(-Math.toRadians(90));
                g2d.translate(-p.getPos().x, -p.getPos().y);
            }
        }
    }
}
