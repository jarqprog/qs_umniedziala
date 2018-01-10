package dao;

import model.*;
import java.util.ArrayList;

public interface IDaoLevels extends IDao{

    public ArrayList<Levels> getTeams();
    public Levels getTeamById();
    public Levels createTeam();

}