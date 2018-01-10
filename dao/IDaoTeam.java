package dao;

import model.*;
import java.util.ArrayList;

public interface IDaoTeam extends IDao{

    public ArrayList<Team> getTeams();
    public Team getTeamById();
    public Team createTeam();

}