package dao;

import model.*;
import java.util.ArrayList;

public interface IDaoLevels {

    public void createLevels(String name, int coins);
    public Levels getLevelById(int id);
    public ArrayList <Levels> importData();
    public void exportData(ArrayList <Levels> list);

}