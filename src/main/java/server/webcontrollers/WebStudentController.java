package server.webcontrollers;

import system.dao.*;
import system.model.Artifact;
import system.model.CodecoolClass;
import system.model.Student;
import system.model.Team;

import java.util.ArrayList;
import java.util.List;

public class WebStudentController implements IStudentController {

    private final IDaoWallet daoWallet;
    private final IDaoStudent daoStudent;
    private final IDaoArtifact daoArtifact;
    private final IDaoLevel daoLevel;
    private final IDaoTeam daoTeam;
    private final IDaoClass daoClass;

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
        if(student.getUserId() == 0) {
            return "";
        }
        return student.getName();
    }

    @Override
    public String getStudentEmail(int studentId) {
        Student student = getStudentById(studentId);
        if(student.getUserId() == 0) {
            return "";
        }
        return student.getEmail();
    }

    @Override
    public String getStudentWallet(int studentId) {
        Student student = getStudentById(studentId);
        if(student.getUserId() == 0) {
            return "";
        }
        return student.getWallet().toString();
    }

    @Override
    public String getStudentGroup(int studentId) {
        Team team = daoTeam.getTeamByStudentId(studentId);
        if(team.getGroupId() == 0) {
            return "";
        }
        return team.getName();
    }

    @Override
    public String getStudentExpLevel(int studentId) {
        Student student = getStudentById(studentId);

        int coins = student.getWallet().getAllCoins();
        String level = daoLevel.importLevelByCoins(coins).getName();
        return String.format("level: %s / collected coins: %s", level, coins);
    }

    @Override
    public String getStudentClass(int studentId) {
        CodecoolClass codecoolClass = daoClass.getStudentClass(studentId);
        if(codecoolClass.getGroupId() == 0) {
            return "";
        }
        return codecoolClass.getName();
    }

    private Student getStudentById(int studentId) {
        return daoStudent.importStudent(studentId);
    }

    @Override
    public List<String> getTeamMembers(int studentId) {
        Team team = daoTeam.getTeamByStudentId(studentId);
        List<Student> members = team.getStudents();
        List<String> membersNames = new ArrayList<>();
        for (Student student : members){
            membersNames.add(student.getName());
        }
        return membersNames;
    }

    @Override
    public List<String> buyArtifact() {
        List<Artifact> artifacts = daoArtifact.getAllArtifacts();
        List<String> artifactsNames = new ArrayList<>();
        for (Artifact artifact : artifacts){
            artifactsNames.add(artifact.getName());
        }
        return artifactsNames;
    }
}





