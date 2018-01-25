package dao;

import model.*;
import java.util.ArrayList;

public interface IDaoTeam {

    public Team getTeamById(int id);
    public void createTeam(String name);
    public ArrayList <Team> importData();
    public void exportData(ArrayList <Team> updatedTeams);

}