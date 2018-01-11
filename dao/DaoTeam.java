package dao;

import model.*;
import java.util.ArrayList;

public class DaoTeam implements IDaoTeam{

    public static ArrayList <Team> teams;

    public ArrayList<Team> getTeams() { return teams;}
    public Team getTeamById() {return teams.get(0);}
    public Team createTeam() {return teams.get(0);}
    public void importData(Team team) {}
    public void exportData() {}
    
}