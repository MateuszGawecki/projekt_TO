package scenes;

import Game.Game;

import java.awt.image.BufferedImage;

public class GameScene {
    private Game game;
    protected int animationId;
    protected int ANIMATION_SPREED = 20;
    protected int tick;

    public GameScene(Game game){
        this.game=game;
    }

    public Game getGame(){
        return game;
    }


    protected void updateTick() {
        tick++;
        if(tick>=ANIMATION_SPREED){
            tick = 0;
            animationId++;
            if(animationId>=4){
                animationId=0;
            }
        }
    }

    protected boolean isAnimation(int spriteID) {
        return getGame().getTileManager().isSpriteAnimation(spriteID);
    }

    protected BufferedImage getSprite(int spriteId){
        return getGame().getTileManager().getSprite(spriteId);
    }

    protected BufferedImage getSprite(int spriteId, int animationIndex){
        return getGame().getTileManager().getAniSprite(spriteId,animationIndex);
    }
}
