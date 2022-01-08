package helpz;

public class Constants {

    public static class Direction{
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class Tiles{
        public static final int WATER_TILE = 0;
        public static final int GRASS_TILE = 1;
        public static final int ROAD_TIlE = 2;
    }

    public static class Enemies{
        public static final int ORC = 0;
        public static final int BAT = 1;
        public static final int KNIGHT = 2;
        public static final int WOLF = 3;

        public static float GetSpeed(int enemyType){
            switch (enemyType){
                case ORC:
                    return 0.5f;
                case BAT:
                    return 0.65f;
                case KNIGHT:
                    return 0.3f;
                case WOLF:
                    return 0.75f;
                default:
                    return 0;
            }
        }

        public static int GetStartHealth(int enemyType){
            switch (enemyType){
                case ORC:
                    return 100;
                case BAT:
                    return 60;
                case KNIGHT:
                    return 255;
                case WOLF:
                    return 85;
                default:
                    return 0;
            }
        }
    }

    public static class Towers{
        public static final int CANNON = 0;
        public static final int ARCHER = 1;
        public static final int WIZARD = 2;

        public static String GetName(int towerType){
            switch (towerType){
                case CANNON:
                    return "Cannon";
                case ARCHER:
                    return "Archer";
                case WIZARD:
                    return "Wizard";
                default:
                    return "";
            }
        }

        public static int GetStartDmg(int towerType){
            switch (towerType){
                case CANNON:
                    return 25;
                case ARCHER:
                    return 50;
                case WIZARD:
                    return 5;
                default:
                    return 0;
            }
        }

        public static float GetDefaultRange(int towerType){
            switch (towerType){
                case CANNON:
                    return 100;
                case ARCHER:
                    return 100;
                case WIZARD:
                    return 100;
                default:
                    return 0;
            }
        }

        public static float GetDefaultCooldown(int towerType){
            switch (towerType){
                case CANNON:
                    return 10;
                case ARCHER:
                    return 10;
                case WIZARD:
                    return 10;
                default:
                    return 0;
            }
        }
    }

    public static class Projectiles{
        public static final int ARROW = 0;
        public static final int BOMB = 1;
        public static final int CHAINS = 2;

        public static float GetSpeed(int type){
            switch (type){
                case ARROW:
                    return 3f;
                case BOMB:
                    return 1f;
                case CHAINS:
                    return 2f;
                default:
                    return 0f;
            }
        }
    }
}
