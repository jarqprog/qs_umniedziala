package dao;

import java.util.ArrayList;
import model.*;

public class DaoLevels implements IDaoLevels{

    private static ArrayList <Levels> levels;

    public ArrayList<Levels> getLevels() {return levels;}
    public Levels getLevelById(int id) {return levels.get(0);}
    public Levels createLevels() { return levels.get(0);}
    public void importData() {}
    public void exportData() {}


}