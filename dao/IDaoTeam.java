package dao;

import model.*;
import java.util.ArrayList;

public interface IDaoTeam {

    public ArrayList<Team> getTeams();
    public Team getTeamById();
    public Team createTeam(String name);
    public void importData(Team team);
    public void exportData();

}