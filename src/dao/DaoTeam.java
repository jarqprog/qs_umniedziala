package dao;

import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DaoTeam{
    public Team createTeam(String name) {
        return new Team(name);
    }

    public Team createTeam (int groupId, String name, ArrayList<Student> students, int availableCoins) {
        return new Team(groupId, name, students, availableCoins);
    }

    public Team importTeam(int teamId) {
        Team team = null;
        String query = "SELECT name, available_coins FROM teams WHERE id_team = ?;";

        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, teamId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                int availableCoins = resultSet.getInt("available_coins");
                ArrayList<Student> students = getStudentsOfTeam(teamId);

                team = createTeam(teamId, name, students, availableCoins);
                resultSet.close();
            }
            preparedStatement.close();

        }catch (SQLException | ClassNotFoundException e){
            System.out.println("Team not found");
        }
        return team;
    }

    public boolean exportTeam(Team team) {
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
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            return false;
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

    public Team getTeamByStudentId(Integer studentId){
        PreparedStatement preparedStatement;
        Team team = null;

        String query = "SELECT id_team FROM students_in_teams WHERE id_student=?;";

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.isClosed()){
                Integer teamId = resultSet.getInt("id_team");

                team = importTeam(teamId);
                resultSet.close();
            }
            preparedStatement.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Selecting students team failed");
        }

        return team;
    }

    public ArrayList<Student> getStudentsOfTeam(int teamId) {
        ArrayList<Student> studentsOfTeam = new ArrayList<Student>();
        String query = "SELECT id_user FROM users JOIN students_in_teams "
                     + "ON users.id_user = students_in_teams.id_student "
                     + "WHERE students_in_teams.id_team = ?;";

        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, teamId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                int userId = resultSet.getInt("id_user");
                Student student = new DaoStudent().importInstance(userId);
                studentsOfTeam.add(student);
            }
            resultSet.close();
            preparedStatement.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("No students");
        }
        return studentsOfTeam;
    }

    public ArrayList<Team> getAllTeams() {
        ArrayList<Team> teams = new ArrayList<Team>();
        String query = "SELECT id_team FROM teams;";

        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int teamId = resultSet.getInt("id_team");
                Team team = importTeam(teamId);
                teams.add(team);
            }
            resultSet.close();
            preparedStatement.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return teams;
    }

    public void assignStudentToTeam(int studentId, int teamId) {
        String query = "INSERT INTO students_in_teams (id_team, id_student) VALUES (?, ?);";

        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, teamId);
            preparedStatement.setInt(2, studentId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Assignment of student to team failed");
        }
    }
}
