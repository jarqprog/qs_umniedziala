package dao;

import java.util.ArrayList;
import model.*;

public class DaoLevels implements IDaoLevels{

    private static ArrayList <Levels> levels = new ArrayList<>();

    public void createLevels(String name, int coins) {
        levels.add(new Levels(name, coins));
    }

    public Levels getLevelById(int id) {
        for(Levels level: levels){
            if(level.getId() == id){
                return level;
            }
        }
        return null;
    }

    public Levels checkLevel(int coins){
        Levels studentLevel = new Levels("No levels", 0);
        for(Levels level : levels){
            if(level.getCoins() <= coins & studentLevel.getCoins() < coins){
                studentLevel = level;
            }
        }
        return studentLevel;

    }
    
    public ArrayList <Levels> importData() {return levels;}

    public void exportData(ArrayList <Levels> list) {
        levels = list;
    }


}