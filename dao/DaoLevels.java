package dao;

import java.util.ArrayList;
import model.*;

public class DaoLevels implements IDaoLevels{

    private static ArrayList <Levels> levels = new ArrayList<>();

    public void createLevels(String name, int coins) {
        levels.add(new Levels(name, coins));
    }

    public Levels getLevelById(int id) {return levels.get(0);}
    
    public ArrayList <Levels> importData() {return levels;}
    public void exportData(ArrayList <Levels> list) {}


}