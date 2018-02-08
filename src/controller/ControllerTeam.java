package controller;

import dao.DaoArtifact;
import dao.DaoTeam;
import dao.DaoWallet;
import model.*;
import view.ViewTeam;

import java.util.ArrayList;

public class ControllerTeam implements IUserController {

    private ViewTeam viewTeam;
    private Team team;

    public ControllerTeam(Team team) {
        this.viewTeam = new ViewTeam();
        this.team = team;
    }

    public Artifact getArtifact(String type) {
        DaoArtifact daoArtifact = new DaoArtifact();

        viewTeam.displayText("Available artifacts:\n");
        viewTeam.displayList(daoArtifact.getArtifacts(type));

        int artifactId = viewTeam.getIntInputFromUser("\nEnter id of artifact: ");
        Artifact artifact = daoArtifact.importArtifact(artifactId);

        return artifact;
    }

    public void buyArtifact() {
        
    }

    public void splitTeamMoney() {
        ArrayList<Student> students = team.getStudents();
        int remainderCoins = team.getAvailableCoins();

        if (remainderCoins == 0) {
            viewTeam.displayText("Team has no coins to split");
            return;
        }

        for (Student student: students) {
            String name = student.getName();
            int coins = viewTeam.getIntInputFromUser("How much should student " + name + " get?");
            if (coins <= remainderCoins) {
                student.addCoins(coins);
                team.subtractCoins(coins);
                new DaoWallet().updateWallet(student);
                new DaoTeam().updateTeamData(team);
                remainderCoins -= coins;
            } else {
                viewTeam.displayText("You do not have enough money");
            }
        }

    }

    public void runMenu() {

        String teamOption = "";
        while (!teamOption.equals("0")) {

            viewTeam.displayText(this.team.getBasicTeamInfo());
            viewTeam.displayText("\nWhat would like to do?");
            viewTeam.displayList(viewTeam.getStudentOptions());

            teamOption = viewTeam.getInputFromUser("Option: ");
            switch (teamOption) {
                case "1":
                    buyArtifact();
                    break;
                case "2":
                    splitTeamMoney();
                    break;
                case "0":
                    break;
                default: viewTeam.displayText("Wrong option. Try again!");
                    break;
            }
        }
    }
}
