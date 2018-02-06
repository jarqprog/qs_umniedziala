package dao;

import java.util.ArrayList;
import model.Level;

public class DaoLevel{
    public Level createLevel(String name, int coinsLimit) {
        return new Level(name, coinsLimit);
    }

    public Level createLevel(int levelId, String name, int coinsLimit) {
        return new Level(levelId, name, coinsLimit);
    }

    public void exportLevel(Level level){

    }

    public Level importLevel(int levelId) {
        return null;
    }

    public ArrayList<Level> getAllLevels() {
        return new ArrayList<Level>();
    }
}
