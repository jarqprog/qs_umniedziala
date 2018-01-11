package dao;

import model.*;
import java.util.ArrayList;

public interface IDaoClass{

    public CodecoolClass getTeamById();
    public CodecoolClass createTeam();
    public void importData(CodecoolClass codecoolclass);
    public void exportData();

}