package controller;

import model.Student;

import java.util.ArrayList;

public class ControllerTeam{

    public boolean checkTeamCoinsDivisibleByTeamSize(int teamCoins, int teamSize) {
        return ((double) teamCoins % teamSize) == 0;
    }

    public void splitMoneyEqually() {
        
    }

    public void splitMoneyAlmostEqually() {

    }

    public void splitTeamMoney() {
        int teamCoins = team.getAvailableCoins();
        int teamSize = team.getSize();

        if (teamCoins == 0) {
            teamView.displayText("Team has no coins to split");
            return;
        }

        if (checkTeamCoinsDivisibleByTeamSize(teamCoins, teamSize)) {
            splitMoneyEqually();
        } else {
            splitMoneyAlmostEqually();
        }
    }

}
