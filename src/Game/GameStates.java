package Game;

public enum GameStates {
    PLAYING,
    MENU,
    SETTINGS,
    EDITING,
    GAME_OVER;

    public static GameStates gameState = MENU;

    public static void SetGameState(GameStates state){
        gameState = state;
    }
}
