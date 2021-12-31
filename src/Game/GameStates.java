package Game;

public enum GameStates {
    PLAYING,
    MENU,
    SETTINGS,
    EDITING;

    public static GameStates gameState = MENU;

    public static void setGameState(GameStates state){
        gameState = state;
    }
}
