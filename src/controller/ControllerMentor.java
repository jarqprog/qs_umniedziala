package controller;

import view.ViewMentor;
import dao.*;
import model.*;

import java.util.ArrayList;

public class ControllerMentor implements IUserController{
    private ViewMentor viewMentor;
    private Mentor mentor;

    public ControllerMentor(Mentor mentor){
        this.viewMentor = new ViewMentor();
        this.mentor = mentor;
    }

    public void createStudent() {
        
        DaoStudent daoStudent = new DaoStudent();
        String studentName = viewMentor.getInputFromUser("Enter name of new student: ");
        String studentPassword = viewMentor.getInputFromUser("Enter password of new student: ");
        String studentEmail = viewMentor.getInputFromUser( "Enter email of new student: ");

        Student student = daoStudent.createInstance(studentName, studentPassword, studentEmail);
        daoStudent.exportInstance(student);
        Student studentWithId = daoStudent.importNewStudent(studentEmail);
        DaoWallet daoWallet = new DaoWallet();
        Wallet wallet = daoWallet.createWallet();
        studentWithId.setWallet(wallet);
        daoWallet.exportWallet(studentWithId);

    }

    public void createTeam() {
        DaoTeam daoTeam = new DaoTeam();

        String nameRequest = "Enter name of new team: ";
        String teamName = viewMentor.getInputFromUser(nameRequest);

        Team team = daoTeam.createTeam(teamName);
        daoTeam.exportTeam(team);
    }

    public void addQuest(){

        DaoQuest daoQuest = new DaoQuest();

        String questName = viewMentor.getInputFromUser("Enter name of new quest: ");
        int questValue = viewMentor.getIntInputFromUser( "Enter value of new quest: ");
        String questDescription = viewMentor.getInputFromUser("Enter description of quest");
        String questType = chooseType();
        String questCategory = chooseCategory();

        Quest quest = daoQuest.createQuest(questName, questValue, questDescription, questType, questCategory);
        daoQuest.exportQuest(quest);
    }

    public void addArtifact() {
        DaoArtifact daoArtifact = new DaoArtifact();

        String nameRequest = "Enter name of new artifact: ";
        String artifactName = viewMentor.getInputFromUser(nameRequest);

        String valueRequest = "Enter value of new artifact: ";
        int artifactValue = viewMentor.getIntInputFromUser(valueRequest);

        String descriptionRequest = "Enter description of new artifact";
        String artifactDescription = viewMentor.getInputFromUser(descriptionRequest);

        String artifactStatus = chooseType();
        Artifact artifact = daoArtifact.createArtifact(artifactName, artifactValue, artifactDescription, artifactStatus);
        daoArtifact.exportArtifact(artifact);
    }

    private String chooseType() {
        String statusRequest = "Choose type:\n1. Individual\n2. Team\nOption: ";
        String status = null;
        boolean choosingStatus = true;
        int option = 0;
        while(choosingStatus) {
            option = viewMentor.getIntInputFromUser(statusRequest);
            switch(option) {
                case 1:
                    status = "individual";
                    choosingStatus = false;
                    break;
                case 2:
                    status = "team";
                    choosingStatus = false;
                    break;
                default:
                    viewMentor.displayText("Wrong option number!");
            }
        }
        return status;
    }

    private String chooseCategory() {
        String typeRequest = "Choose category:\n1. Basic\n2. Extra\nOption: ";
        String type = null;
        boolean choosingType = true;
        int option = 0;
        while(choosingType) {
            option = viewMentor.getIntInputFromUser(typeRequest);
            switch(option) {
                case 1:
                    type = "basic";
                    choosingType = false;
                    break;
                case 2:
                    type = "extra";
                    choosingType = false;
                    break;
                default:
                    viewMentor.displayText("Wrong option number!");
            }
        }
        return type;
    }

    public void updateQuest(){
        toBeImplemented();
    }

    public void updateArtifact() {
        //seeArtifacts();
        toBeImplemented();
        
    }

    public void toBeImplemented(){
        String text = "Implementation in progress";
        viewMentor.displayText(text);
    }

    public void markQuest() {
        toBeImplemented();
    }

    public void markArtifact() {
        toBeImplemented();
    }

    public void markBoughtArtifact(){
        Student student = getStudent();

        viewMentor.displayText("Student artifacts:\n");
        viewMentor.displayList(student.getAllNewArtifacts());
        Integer artifactId = viewMentor.getIntInputFromUser("Choose id artifact to be marked as bougth: ");

        Artifact artifactToBeBougth = getArtifact(artifactId);
        student.markArtifactAsBougth(artifactToBeBougth);

    }

    public void markStudentAchivedQuest() {
        toBeImplemented();
    }

    public void markTeamAchivedQuest() {
        toBeImplemented();
    }

    public void markStudentBoughtArtifact() {
        toBeImplemented();
    }

    public void markTeamBoughtArtifact() {
        toBeImplemented();
    }

    public void seeAllWallets() {
//        int idOfMentorClass = mentor.getClassId();
//        viewMentor.displayText("Wallets of students of class: ");
//
//        DaoStudent daoStudent = new DaoStudent();
//        ArrayList<Student> allStudentsOfClass = daoStudent.getStudentsByClassId(idOfMentorClass);
//        MyIterator <Student> myIterator = new MyIterator<>(allStudentsOfClass);
//        while(myIterator.hasNext()){
//            Student student = myIterator.next();
//            viewMentor.displayText(student.toString() + "/n" + student.getWallet().toString());
//        }
    }
    private ArrayList<Wallet> getWalletsOfStudents(ArrayList<Student> students) {
        ArrayList<Wallet> walletsOfStudents = new ArrayList<>();
        for (Student student : students) {
            walletsOfStudents.add(student.getWallet());
        }
        return walletsOfStudents;
    }

    public void seeQuests() {
//        viewMentor.displayText("Available quests: ");
//        viewMentor.displayList(new DaoArtifact().importData());
    }

    public void seeArtifacts() {
//        viewMentor.displayText("Available artifacts: ");
//        viewMentor.displayList(new DaoArtifact().importData());
    }

    public void runMenu() {
        String mentorOption = "";
        while (!mentorOption.equals("0")) {

            viewMentor.displayText("\nWhat would like to do?");
            viewMentor.displayList(viewMentor.getMentorOptions());

    mentorOption = viewMentor.getInputFromUser("Option: ");
    switch (mentorOption) {
        case "1": createStudent();
                break;
        case "2": createTeam();
                break;
        case "3": addQuest();
                break;
        case "4": addArtifact();
                break;
        case "5": updateQuest();
                break;
        case "6": updateArtifact();
                break;
        case "7": markQuest();
                break;
        case "8": markArtifact();
                break;
        case "9": seeAllWallets();
                break;
        case "0": break;

        default: viewMentor.displayText("Wrong option. Try again!");
                 break;
    }
        }

    }

}
