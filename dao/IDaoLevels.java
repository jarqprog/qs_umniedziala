package dao;

import model.*;
import java.util.ArrayList;

public interface IDaoLevels {

    public ArrayList<Levels> getTeams();
    public Levels getLevelById(int id);
    public Levels createTeam();
    public void importData(Team team);
    public void exportData();

}