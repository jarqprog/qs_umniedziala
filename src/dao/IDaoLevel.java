package dao;

import model.*;
import java.util.ArrayList;

public interface IDaoLevel {

    public void createLevels(String name, int coins);
    public Level getLevelById(int id);
    public ArrayList <Level> importData();
    public void exportData(ArrayList <Level> list);

}
