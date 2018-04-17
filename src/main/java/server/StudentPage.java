package server;

import dao.DaoLevel;
import dao.DaoTeam;
import model.Student;
import org.jtwig.JtwigModel;

public class StudentPage {

    public static void setModel(Student student, JtwigModel model){
        DaoLevel daoLevel = new DaoLevel();
        DaoTeam daoTeam = new DaoTeam();
        model.with("name", student.getName());
        model.with("email", student.getEmail());
        model.with("money", student.getWallet().getAvailableCoins());
        model.with("level", daoLevel.importLevelByCoins(student.getWallet().getAllCoins()).getName());
        model.with("team", daoTeam.getTeamByStudentId(student.getUserId()).getBasicTeamInfo());
    }
}
