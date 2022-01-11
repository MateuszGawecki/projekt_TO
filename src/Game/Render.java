package Game;

import java.awt.*;

public class Render {

    private Game game;

    public Render(Game game){
        this.game = game;
    }

    public void render(Graphics g){
        switch (GameStates.gameState) {
            case MENU:
                game.getMenu().render(g);
                break;

            case PLAYING:
                game.getPlaying().render(g);
                break;

            case SETTINGS:
                game.getSettings().render(g);
                break;

            case EDITING:
                game.getEditing().render(g);
                break;

            case GAME_OVER:
                game.getGameOver().render(g);
                break;

            default:
                break;
        }
    }
}
