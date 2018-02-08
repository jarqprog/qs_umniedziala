package controller;

//import dao.DaoArtifact;
import dao.DaoLevel;
//import dao.DaoQuest;
import dao.DaoTeam;
import model.*;
import dao.DaoArtifact;
import dao.DaoWallet;
import model.Artifact;
import model.Student;
import model.Wallet;
import model.Level;
import view.ViewStudent;

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
        viewStudent.displayList(daoArtifact.getArtifacts(type));

        int artifactId = viewStudent.getIntInputFromUser("\nEnter id of artifact: ");
        Artifact artifact = daoArtifact.importArtifact(artifactId);

        return artifact;
    }

    public void seeExpLevel() {
        DaoLevel daoLevel = new DaoLevel();
        Level level = daoLevel.importLevelByCoins(this.student.getWallet().getAllCoins());
        viewStudent.displayText("Your wallet: ");
        viewStudent.displayText(student.getWallet().toString());
        viewStudent.displayText("Your level: ");
        String levelString = level.toString();
        viewStudent.displayText(levelString);
    }

    public void manageTeam() {
        Team team = new DaoTeam().getTeamByStudentId(student.getUserId());
        ControllerTeam controllerTeam = new ControllerTeam(team);
        controllerTeam.runMenu();
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
                case "5": manageTeam();
                        break;
                case "0": break;

                default: viewStudent.displayText("Wrong option. Try again!");
                         break;
            }
        }

    }

}
