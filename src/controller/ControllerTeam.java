package controller;

import model.Student;

import java.util.ArrayList;

public class ControllerTeam{

    public boolean checkTeamCoinsDivisibleByTeamSize(int teamCoins, int teamSize) {
        return ((double) teamCoins % teamSize) == 0;
    }

}
