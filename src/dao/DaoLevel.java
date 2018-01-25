package dao;

import java.util.ArrayList;
import model.*;

public class DaoLevel implements IDaoLevel {

    private static ArrayList <Level> levels = new ArrayList<>();

    public void createLevels(String name, int coins) {
        levels.add(new Level(name, coins));
    }

    public Level getLevelById(int id) {
        for(Level level: levels){
            if(level.getId() == id){
                return level;
            }
        }
        return null;
    }

    public Level checkLevel(int coins){
        Level studentLevel = new Level("No levels", 0);
        for(Level level : levels){
            if(level.getCoins() <= coins & studentLevel.getCoins() < coins){
                studentLevel = level;
            }
        }
        return studentLevel;

    }
    
    public ArrayList <Level> importData() {return levels;}

    public void exportData(ArrayList <Level> list) {
        levels = list;
    }


}
