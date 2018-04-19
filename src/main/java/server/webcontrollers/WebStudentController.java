package server.webcontrollers;

import dao.*;
import model.CodecoolClass;
import model.Student;
import model.Team;

public class WebStudentController implements IStudentController {


    private Student student;
    private IDaoWallet daoWallet;
    private IDaoStudent daoStudent;
    private IDaoArtifact daoArtifact;
    private IDaoLevel daoLevel;
    private IDaoTeam daoTeam;
    private IDaoClass daoClass;


    public static IStudentController create(IDaoWallet daoWallet, IDaoStudent daoStudent,
                                            IDaoArtifact daoArtifact, IDaoLevel daoLevel,
                                            IDaoTeam daoTeam, IDaoClass daoClass) {
        return new WebStudentController(daoWallet, daoStudent, daoArtifact,
                daoLevel, daoTeam, daoClass);
    }

    private WebStudentController(
                             IDaoWallet daoWallet, IDaoStudent daoStudent,
                             IDaoArtifact daoArtifact, IDaoLevel daoLevel,
                             IDaoTeam daoTeam, IDaoClass daoClass) {

        // proper student will be set in method (by calling getStudentById(int studentId))
        this.student = new Student(0, "n/a", "n/a", "n/a");  // by default

        this.daoWallet = daoWallet;
        this.daoStudent = daoStudent;
        this.daoArtifact = daoArtifact;
        this.daoLevel = daoLevel;
        this.daoTeam = daoTeam;
        this.daoClass = daoClass;
    }

    @Override
    public String getStudentName(int studentId) {
        Student student = getStudentById(studentId);
        if(student != null) {
            return student.getName();
        }
        return "";
    }

    @Override
    public String getStudentEmail(int studentId) {
        Student student = getStudentById(studentId);
        if(student == null) {
            return "";
        }
        return student.getEmail();
    }

    @Override
    public String getStudentWallet(int studentId) {
        Student student = getStudentById(studentId);
        if(student != null) {
            return student.getWallet().toString();
        }
        return "";
    }

    @Override
    public String getStudentGroup(int studentId) {
        Team team = daoTeam.getTeamByStudentId(studentId);
        if(team != null) {
            return team.getName();
        }
        return "";
    }

    @Override
    public String getStudentExpLevel(int studentId) {
        Student student = getStudentById(studentId);
        if(student == null) {
            return "n/a";
        }
        int coins = student.getWallet().getAllCoins();
        String level = daoLevel.importLevelByCoins(coins).getName();
        return String.format("level: %s / collected coins: %s", level, coins);
    }

    @Override
    public String getStudentClass(int studentId) {
        CodecoolClass codecoolClass = daoClass.getStudentClass(studentId);
        if(codecoolClass != null) {
            return codecoolClass.getName();
        }
        return "";
    }

    private Student getStudentById(int studentId) {
        return daoStudent.importStudent(studentId);
    }

}





