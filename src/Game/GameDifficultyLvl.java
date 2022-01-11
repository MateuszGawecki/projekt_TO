package Game;

public enum GameDifficultyLvl {
    EASY,
    NORMAL,
    HARD,
    BRUTAL;

    public static GameDifficultyLvl gameDificultyLvl = EASY;

    public static void SetDificultyLvl(GameDifficultyLvl lvl){
        gameDificultyLvl = lvl;
    }

    public static GameDifficultyLvl GetDifLvl(String name){
        switch (name){
            case "EASY":
                return EASY;
            case "NORMAL":
                return NORMAL;
            case "HARD":
                return HARD;
            case "BRUTAL":
                return BRUTAL;
        }

        return null;
    }

    public static int GetDifLvl(){
        switch (gameDificultyLvl){
            case EASY:
                return 0;
            case NORMAL:
                return 1;
            case HARD:
                return 2;
            case BRUTAL:
                return 3;
        }

        return 0;
    }
}
