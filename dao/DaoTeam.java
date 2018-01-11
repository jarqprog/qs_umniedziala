package dao;

import model.*;
import java.util.ArrayList;

public class DaoTeam implements IDaoTeam{

    public static ArrayList <Team> teams = new ArrayList<>();

    public Team getTeamById() {return teams.get(0);}
    public void createTeam(String name) {
        teams.add(new Team(name));
    }
    public ArrayList <Team> importData() {
        return teams;
    }
    public void exportData(ArrayList <Team> updatedTeams) {
        teams = updatedTeams;
    }
    
}