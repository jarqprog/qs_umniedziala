package controller;

import model.Student;

import java.util.ArrayList;

public class ControllerTeam{

    private boolean checkTeamCoinsDivisibleByTeamSize(int teamCoins, int teamSize) {
        return (teamCoins % teamSize) == 0;
    }

    private void splitMoneyEqually() {
        ArrayList<Student> students = team.getStudents();
        int teamCoins = team.getAvailableCoins();
        int teamSize = team.getSize();

        int coinsForOneStudent = teamCoins / teamSize;

        for (Student student: students) {
            student.addCoins(coinsForOneStudent);
        }
    }

    private void splitMoneyAlmostEqually() {

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
