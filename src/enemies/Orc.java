package enemies;

import static helpz.Constants.Enemies.ORC;

public class Orc extends Enemy{

    public Orc(float x, float y, int Id) {
        super(x, y, Id, ORC);
        setHealth(50);
    }
}
