package dao;

import model.*;
import java.util.ArrayList;

public interface IDaoLevels {

    public Levels getLevelById(int id);
    public void createLevels();
    public ArrayList <Levels> importData();
    public void exportData(ArrayList <Levels> list);

}