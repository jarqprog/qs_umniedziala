package dao;

import model.*;
import java.util.ArrayList;

public class DaoTeam{
    public Team createTeam(String name) {
        return new Team(name);
    }

    public Team createTeam (int groupId, String name, ArrayList<Student> students, int availableCoins) {
        return new Team(groupId, name, students, availableCoins);
    }

    public void exportTeam(Team team) {

    }
}