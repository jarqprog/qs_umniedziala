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

        daoStudent.createStudent(studentName, studentPassword, studentEmail, classId);

    }

    public void createTeam() {
        DaoTeam daoTeam = new DaoTeam();

        String nameRequest = "Enter name of new team: ";
        String teamName = viewMentor.getInputFromUser(nameRequest);

        daoTeam.createTeam(); //tu musi być przekazany argment teamName
    }

    public void addQuest(){
        DaoQuest daoQuest = new DaoQuest();

        String nameRequest = "Enter name of new quest: ";
        String questName = viewMentor.getIntInputFromUser(nameRequest);

        String valueRequest = "Enter value of new quest: ";
        int questValue = viewMentor.getIntInputFromUser(valueRequest);

        String descriptionRequest = "Enter description of quest";
        String questDescription = viewMentor.getInputFromUser(descriptionRequest);

        daoQuest.createQuest(); //tu muszą być przekazane argmenty
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
