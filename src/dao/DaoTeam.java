package dao;

import model.*;
import java.util.ArrayList;

public class DaoTeam implements IDaoTeam{

    public static ArrayList <Team> teams = new ArrayList<>();

    public void createTeam(String name){
        teams.add(new Team(name));
    }

    public Team getTeamById(int id){
        for(Team team: teams){
            if(team.getGroupId() == id){
                return team;
            }
        }
        return null;
    }

    public void exportData(ArrayList <Team> updatedTeams){
        teams = updatedTeams;
    }

    public ArrayList <Team> importData(){
        return teams;
    }
    
}