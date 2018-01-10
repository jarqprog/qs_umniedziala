package dao;

import model.*;
import java.util.ArrayList;

public interface IDaoQuest extends IDao{

    public ArrayList<Quest> getTeams();
    public Quest getTeamById();
    public Quest createTeam();

}