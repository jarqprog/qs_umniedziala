package controller;

import view.ViewTeam;
import model.*;

public class ControllerTeam implements IUserController{

    private ViewTeam viewTeam;
    private Team team;

    public ControllerTeam(Team team){
        this.viewTeam = new ViewTeam();
        this.team = team;
    }


    public void runMenu(){

        String teamOption = "";
        while (!teamOption.equals("0")) {

            viewTeam.displayText("\nWhat would like to do?");
            viewTeam.displayList(viewTeam.getStudentOptions());

            teamOption = viewTeam.getInputFromUser("Option: ");
            switch (teamOption) {
                case "1": seeWallet();
                    break;
                case "2": buyArtifact();

    }
}