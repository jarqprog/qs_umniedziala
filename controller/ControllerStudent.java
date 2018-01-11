package controller;

import dao.DaoArtifact;
import dao.DaoQuest;
import model.Artifact;
import model.Student;
import model.Wallet;
import view.ViewStudent;

public class ControllerStudent{

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
        seeArtifacts();

        DaoArtifact daoArtifact = new DaoArtifact();
	    int artifactId = viewStudent.getIntInputFromUser("\nEnter id of artifact you want to buy: ");
        Artifact artifact = daoArtifact.getArtifactById(artifactId);

        while (artifact == null) {
            viewStudent.displayText("No artifact with such id found!");
	        artifactId = viewStudent.getIntInputFromUser("\nEnter id of artifact you want to buy: ");
	        artifact = artifactDao.getArtifactById(artifactId);
        }

        Wallet wallet = student.getWallet();
        if (wallet.getCoins() < artifact.getValue()) {
            viewStudent.displayText("You do not hav enough money to buy this artifact!");
        } else {
            wallet.addArtifact(artifact);
            viewStudent.displayText("Artifact was added to wallet!");
        }
    }

    public void seeExpLevel() {

    }

    public void manageTeam() {

    }

    public void seeQuests() {
        viewStudent.displayText("Available quests: ");
        viewStudent.displayList(new DaoQuest().importData());
    }

    public void seeArtifacts() {
        viewStudent.displayText("Available artifacts: ");
        viewStudent.displayList(new DaoArtifact().importData());
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
