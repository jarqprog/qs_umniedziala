package server.webcontrollers;

import dao.*;
import model.Student;

public class WebStudentController implements IStudentController {


    private Student student;
    private IDaoWallet daoWallet;
    private IDaoStudent daoStudent;
    private IDaoArtifact daoArtifact;
    private IDaoLevel daoLevel;
    private IDaoTeam daoTeam;


    public static IStudentController create(IDaoWallet daoWallet, IDaoStudent daoStudent,
                                            IDaoArtifact daoArtifact, IDaoLevel daoLevel,
                                            IDaoTeam daoTeam) {
        return new WebStudentController(daoWallet, daoStudent, daoArtifact, daoLevel, daoTeam);
    }

    private WebStudentController(
                             IDaoWallet daoWallet, IDaoStudent daoStudent,
                             IDaoArtifact daoArtifact, IDaoLevel daoLevel,
                             IDaoTeam daoTeam) {

        // proper student will be set in method (by calling getStudentById(int studentId))
        this.student = new Student(0, "n/a", "n/a", "n/a");  // by default

        this.daoWallet = daoWallet;
        this.daoStudent = daoStudent;
        this.daoArtifact = daoArtifact;
        this.daoLevel = daoLevel;
        this.daoTeam = daoTeam;
    }

    @Override
    public int getWallet(int studentId) {
        return 0;
    }

    @Override
    public String[] getExpLevel(int studentId) {
        return new String[0];
    }

    private void setStudent(int studentId) {
        this.student = getStudentById(studentId);
    }

    private Student getStudentById(int studentId) {
        return daoStudent.importStudent(studentId);
    }

}





