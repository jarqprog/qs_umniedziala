package controller;

//import dao.DaoArtifact;
//import dao.DaoLevel;
//import dao.DaoQuest;
import dao.DaoArtifact;
import dao.DaoWallet;
import model.Artifact;
import model.Student;
import model.Wallet;
import model.Level;
import view.ViewStudent;

import java.sql.SQLException;

public class ControllerStudent implements IUserController{

    private ViewStudent viewStudent;
    private Student student;

    public ControllerStudent(Student student) {
        this.viewStudent = new ViewStudent();
        this.student = student;
    }

    public void seeWallet() {
        viewStudent.displayText(student.getWallet().toString());
    }

    public void buyArtifact() {
        Artifact artifact = getArtifact("individual");
        int artifactId = artifact.getItemId();
        int studentId = student.getUserId();

        int price = artifact.getValue();
        if (student.hasEnoughCoins(price)) {
            student.subtractCoins(price);
            student.getWallet().addArtifact();

            DaoWallet daoWallet = new DaoWallet();
            daoWallet.updateWallet(studentId);
            daoWallet.exportStudentArtifact(studentId, artifactId);

        } else {
            viewStudent.displayText("You do not have enough money to buy this artifact!");
        }
    }

    public Artifact getArtifact(String type) {
        DaoArtifact daoArtifact = new DaoArtifact();

        viewStudent.displayText("Available artifacts:\n");
        viewStudent.displayList(daoArtifact.getArtifacts(type));

        int artifactId = viewStudent.getIntInputFromUser("\nEnter id of artifact: ");
        Artifact artifact = daoArtifact.importArtifact(artifactId);

        return artifact;
    }

    public void seeExpLevel() {
//        DaoLevel daoLevels = new DaoLevel();
//        Level level = daoLevels.checkLevel(this.student.getWallet().getCoins());
//        System.out.println(level);
    }

    public void manageTeam() {
        viewStudent.displayText("Implementation in progress");
    }

    public void seeQuests() {
//        viewStudent.displayText("Available quests: ");
//        viewStudent.displayList(new DaoQuest().importData());
    }

    public void seeArtifacts() {
//        viewStudent.displayText("Available artifacts: ");
//        viewStudent.displayList(new DaoArtifact().importData());
    }

    public void runMenu() {

        String studentOption = "";
        while (!studentOption.equals("0")) {

            viewStudent.displayText("\nWhat would like to do?");
            viewStudent.displayList(viewStudent.getStudentOptions());

            studentOption = viewStudent.getInputFromUser("Option: ");
            switch (studentOption) {
                case "1": seeWallet();
                        break;
                case "2": buyArtifact();
                        break;
                case "3": seeExpLevel();
                        break;
                case "4": manageTeam();
                        break;
                case "5": seeQuests();
                        break;
                case "6": seeArtifacts();
                        break;
                case "0": break;

                default: viewStudent.displayText("Wrong option. Try again!");
                         break;
            }
        }

    }

}
