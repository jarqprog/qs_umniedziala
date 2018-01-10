package dao;

import model.*;
import java.util.ArrayList;

public interface IDaoClass extends IDao{

    public ArrayList<CodecoolClass> getTeams();
    public CodecoolClass getTeamById();
    public CodecoolClass createTeam();

}