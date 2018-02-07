package controller;

import model.Student;

import java.util.ArrayList;
import java.util.Random;

public class ControllerTeam{

    private boolean checkTeamCoinsDivisibleByTeamSize(int teamCoins, int teamSize) {
        return (teamCoins % teamSize) == 0;
    }

    private Student popRandomStudent(ArrayList<Student> students) {
        int randomIndex = new Random.nextInt(students.size());
        Student randomStudent = students.get(randomIndex);
        students.remove(randomStudent);
        return randomStudent;
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
        ArrayList<Student> students = team.getStudents().clone();
        int teamCoins = team.getAvailableCoins();
        int teamSize = team.getSize();

        splitMoneyEqually();
        int remainderCoins = (teamCoins % teamSize);

        while (ramainderCoins > 0) {
            Student luckyStudent = popRandomStudent(students);
            luckyStudent.addCoins(1);
            remainderCoins--;
        }
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
