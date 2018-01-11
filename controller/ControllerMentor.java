package controller;

import view.ViewMentor;
import dao.*;
import model.*;

import java.util.ArrayList;

public class ControllerMentor{
    private ViewMentor viewMentor;
    private Mentor mentor;

    public ControllerMentor(Mentor mentor){
        this.viewMentor = new ViewMentor();
        this.mentor = mentor;
    }

    public void createStudent() {
        DaoStudent daoStudent = new DaoStudent();

        String nameRequest = "Enter name of new student: ";
        String studentName = viewMentor.getInputFromUser(nameRequest);

        String passwordRequest = "Enter password of new student: ";
        String studentPassword = viewMentor.getInputFromUser(passwordRequest);

        String emailRequest = "Enter email of new student: ";
        String studentEmail = viewMentor.getInputFromUser(emailRequest);

        String classIdRequest = "Enter ID of class which student will attend: ";
        int classId = viewMentor.getIntInputFromUser(classIdRequest);

        Student newStudent = daoStudent.createStudent(studentName, studentPassword, studentEmail, classId);

        daoStudent.export; // skończ to !!!!!!!!!!!!!!!!!!!!
    }

    public void createTeam() {
        DaoTeam daoTeam = new DaoTeam();

        String nameRequest = "Enter name of new team: ";
        String teamName = viewMentor.getInputFromUser(nameRequest);

        // jakieś metody w dao do tworzenia nowego teamu i exportowanie go 
    }

    public void addQuest(){

    }

    public void addArtifact() {

    }

    public void updateQuest() {

    }

    public void updateArtifact() {

    }

    public void markStudentAchivedQuest() {

    }

    public void markTeamAchivedQuest() {

    }

    public void markStudentBoughtArtifact() {

    }

    public void markTeamBoughtArtifact() {

    }

    public void seeAllWallets() {

    }

    public void seeQuests() {

    }

    public void seeArtifacts() {

    }

    public void runMenu() {

    }
}
