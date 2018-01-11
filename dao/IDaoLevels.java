package dao;

import model.*;
import java.util.ArrayList;

public interface IDaoLevels {

    public ArrayList<Levels> getLevels();
    public Levels getLevelById(int id);
    public Levels createLevels();
    public void importData();
    public void exportData();

}