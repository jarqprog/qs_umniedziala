package dao;

import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoTeam extends SqlDao implements IDaoTeam {

    private final IDaoStudent daoStudent;
    private final String DATABASE_TABLE = "teams";
    private final String ID_LABEL = "id_team";

    DaoTeam(Connection connection, IDaoStudent daoStudent) {
        super(connection);
        this.daoStudent = daoStudent;
    }

    @Override
    public Team createTeam(String name) {

        try {
            int id = getLowestFreeIdFromGivenTable(DATABASE_TABLE, ID_LABEL);
            int coins = 0;
            return new Team(id, name, new ArrayList<>(), coins);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Team createTeam(int groupId, String name, List<Student> students, int availableCoins) {
        return new Team(groupId, name, students, availableCoins);
    }

    @Override
    public Team importTeam(int teamId) {
        Team team = null;
        String query = "SELECT name, available_coins FROM teams WHERE id_team = ?;";

        try (
             PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {

            preparedStatement.setInt(1, teamId);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int availableCoins = resultSet.getInt("available_coins");
                    List<Student> students = getStudentsOfTeam(teamId);

                    team = createTeam(teamId, name, students, availableCoins);
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Team not found");
        }
        return team;
    }

    @Override
    public boolean exportTeam(Team team) {
        String teamName = team.getName();
        int teamCoins = team.getAvailableCoins();
        String query = "INSERT INTO teams (name, available_coins) VALUES (?, ?);";

        try (
             PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {

            preparedStatement.setString(1, teamName);
            preparedStatement.setInt(2, teamCoins);
            preparedStatement.executeUpdate();
             return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void updateTeamData(Team team) {
        String teamName = team.getName();
        int teamId = team.getGroupId();
        int teamCoins = team.getAvailableCoins();

        String query = "UPDATE teams SET name = ?, available_coins = ? WHERE id_team = ?;";

        try (
             PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {

            preparedStatement.setString(1, teamName);
            preparedStatement.setInt(2, teamCoins);
            preparedStatement.setInt(3, teamId);

            preparedStatement.executeUpdate();
         } catch (SQLException e) {
            System.out.println(" insertion failed");
        }
    }

    @Override
    public Team getTeamByStudentId(Integer studentId){

        Team team = null;

        String query = "SELECT id_team FROM students_in_teams WHERE id_student=?;";

        try (
             PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {

            preparedStatement.setInt(1, studentId);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    Integer teamId = resultSet.getInt(ID_LABEL);
                    team = importTeam(teamId);
                }
            }

        } catch (SQLException e) {
            System.out.println("Selecting students team failed");
        }

        return team;
    }

    @Override
    public List<Student> getStudentsOfTeam(int teamId) {
        List<Student> studentsOfTeam = new ArrayList<>();
        String query = "SELECT id_user FROM users JOIN students_in_teams "
                     + "ON users.id_user = students_in_teams.id_student "
                     + "WHERE students_in_teams.id_team = ?;";

        try (
             PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {

            preparedStatement.setInt(1, teamId);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int userId = resultSet.getInt("id_user");
                    Student student = daoStudent.importStudent(userId);
                    studentsOfTeam.add(student);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No students");
        }
        return studentsOfTeam;
    }

    @Override
    public List<Team> getAllTeams() {
        List<Team> teams = new ArrayList<Team>();
        String query = "SELECT id_team FROM teams;";

        try (
             PreparedStatement preparedStatement = getConnection().prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()){

            while (resultSet.next()){
                int teamId = resultSet.getInt("id_team");
                Team team = importTeam(teamId);
                teams.add(team);
            }

        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return teams;
    }

    @Override
    public void assignStudentToTeam(int studentId, int teamId) {
        String query = "INSERT INTO students_in_teams (id_team, id_student) VALUES (?, ?);";

        try (
             PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {

            preparedStatement.setInt(1, teamId);
            preparedStatement.setInt(2, studentId);
            preparedStatement.executeUpdate();
         } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Assignment of student to team failed");
        }
    }
}
