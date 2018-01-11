package dao;

import java.util.ArrayList;
import model.*;

public class DaoLevels implements IDaoLevels{

    private static ArrayList <Levels> levels;

    public Levels getLevelById(int id) {return levels.get(0);}
    public void createLevels() { }
    public ArrayList <Levels> importData() {return levels;}
    public void exportData(ArrayList <Levels> list) {}


}