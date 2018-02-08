package dao;

import model.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class DaoTeam{
    public Team createTeam(String name) {
        return new Team(name);
    }

    public Team createTeam (int groupId, String name, ArrayList<Student> students, int availableCoins) {
        return new Team(groupId, name, students, availableCoins);
    }

    public void exportTeam(Team team) {
        String teamName = team.getName();
        int teamCoins = team.getAvailableCoins();

        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO teams (name, available_coins) VALUES (?, ?);";

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, teamName);
            preparedStatement.setInt(2, teamCoins);

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Team insertion failed");
        }
    }

    public void updateTeamData(Team team) {
        String teamName = team.getName();
        int teamId = team.getGroupId();
        int teamCoins = team.getAvailableCoins();

        PreparedStatement preparedStatement = null;
        String query = "UPDATE teams SET name = ?, available_coins = ? WHERE id_team = ?;";

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, teamName);
            preparedStatement.setInt(2, teamCoins);
            preparedStatement.setInt(3, teamId);

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(" insertion failed");
        }
    }

    public Team getTeamByStudentId(Integer studentId){}
}