package controller;

//import dao.DaoArtifact;
import dao.*;
//import dao.DaoQuest;
import model.*;
import model.Artifact;
import model.Student;
import model.Wallet;
import model.Level;
import view.ViewStudent;
import view.ViewTeam;

import java.util.ArrayList;

public class ControllerStudent implements IUserController{

    private ViewStudent viewStudent;
    private Student student;

    public ControllerStudent(Student student, ViewStudent viewStudent) {
        this.viewStudent = viewStudent;
        this.student = student;
    }

    public void seeWallet() {
        viewStudent.displayText(student.getWallet().toString());
    }

    public void buyArtifact() {
        Artifact artifact = getArtifact("individual");
        if(artifact == null){
            return;
        }
        int artifactId = artifact.getItemId();
        int studentId = student.getUserId();

        int price = artifact.getValue();
        if (student.hasEnoughCoins(price)) {
            student.subtractCoins(price);
            student.addNewArtifact(artifact);

            DaoWallet daoWallet = new DaoWallet();
            daoWallet.updateWallet(student);
            daoWallet.exportStudentArtifact(artifactId, studentId);

        } else {
            viewStudent.displayText("You do not have enough money to buy this artifact!");
        }
    }

    public Artifact getArtifact(String type) {
        DaoArtifact daoArtifact = new DaoArtifact();

        viewStudent.displayText("Available artifacts:\n");
        ArrayList<Artifact> allArtifacts = daoArtifact.getArtifacts(type);
        Artifact artifact = null;

        if(allArtifacts.size() != 0) {
            viewStudent.displayList(allArtifacts);
            int artifactId = viewStudent.getIntInputFromUser("\nEnter id of artifact: ");
            artifact = daoArtifact.importArtifact(artifactId);
        }else {
            viewStudent.displayText("No artifacts");
        }

        return artifact;
    }

    public void seeExpLevel() {
        DaoLevel daoLevel = new DaoLevel();
        Level level = daoLevel.importLevelByCoins(this.student.getWallet().getAllCoins());
        if (level == null){
            return;
        }
        viewStudent.displayText("Your wallet: ");
        viewStudent.displayText(student.getWallet().toString());
        viewStudent.displayText("Your level: ");
        String levelString = level.toString();
        viewStudent.displayText(levelString);
    }

    public void manageTeam() {
        Team team = new DaoTeam().getTeamByStudentId(student.getUserId());

        if (team != null) {
            ViewTeam viewTeam = new ViewTeam();
            ControllerTeam controllerTeam = new ControllerTeam(team, viewTeam);
            controllerTeam.runMenu();
            student = new DaoStudent().importInstance(student.getUserId());
        }
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
                case "0": break;

                default: viewStudent.displayText("Wrong option. Try again!");
                         break;
            }
        }

    }

}
