package dao;

import model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class DaoMentor extends SqlDao implements IDaoMentor {

    private final String DATABASE_TABLE = "users";
    private final String ID_LABEL = "id_user";

    DaoMentor(Connection connection) {
        super(connection);
    }

    @Override
    public Mentor createMentor(String name, String password, String email) {

        try {
            int id = getLowestFreeIdFromGivenTable(DATABASE_TABLE, ID_LABEL);
            return new Mentor(id, name, password, email);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Mentor importMentor(int mentorId) {

        int roleId = getRoleID();
        String query = "SELECT * FROM users WHERE id_user = ? AND id_role = ?;";

        try (
             PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {

             preparedStatement.setInt(1, mentorId);
            preparedStatement.setInt(2, roleId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if ( resultSet.next() ) {
                    int userId = resultSet.getInt(ID_LABEL);
                    String name = resultSet.getString("name");
                    String password = resultSet.getString("password");
                    String email = resultSet.getString("email");

                    return new Mentor(userId, name, password, email);
                }
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean exportMentor(Mentor mentor) {

        String name = mentor.getName();
        String password = mentor.getPassword();
        String email = mentor.getEmail();
        int roleId = getRoleID();


        String query = "INSERT INTO users (name, password, email, id_role)" +
                       "VALUES (?, ?, ?, ?);";

        try (
             PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setInt(4, roleId);

            preparedStatement.executeUpdate();
            return true;

        }catch (SQLException e){
            return false;
        }
    }

    @Override
    public boolean updateMentor(Mentor mentor){
        String name = mentor.getName();
        String password = mentor.getPassword();
        String email = mentor.getEmail();
        int mentorId = mentor.getUserId();
        int roleId = getRoleID();

        String query = "UPDATE users SET name = ?, password = ?, email = ? "+
                "WHERE id_user= ? AND id_role = ?;";

        try (
             PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setInt(4, mentorId);
            preparedStatement.setInt(5, roleId);
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e){
            return false;
        }
    }

    @Override
    public Integer getMentorClassId(Mentor mentor){

        String query = "SELECT id_codecool_class FROM mentors_in_classes WHERE id_mentor = ?;";

        try (
             PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {

            preparedStatement.setInt(1, mentor.getUserId());
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                return resultSet.getInt("id_codecool_class");
            }
            return -1;

        }catch (SQLException e){
            System.out.println("Class not found");
            return -1;
        }
    }

    @Override
    public List <Mentor> getAllMentors(){

        List <Mentor> mentorList = new ArrayList <Mentor> ();
        int roleId = getRoleID();


        String query = "SELECT * FROM users WHERE id_role = ?;";

        try (
             PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
             preparedStatement.setInt(1, roleId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int userId = resultSet.getInt(ID_LABEL);
                    String name = resultSet.getString("name");
                    String password = resultSet.getString("password");
                    String email = resultSet.getString("email");

                    mentorList.add(new Mentor(userId, name, password, email));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No mentors");
        }
        return mentorList;
    }

    private int getRoleID(){

        String roleName = "mentor";
        int roleId = 0;


        String query = "SELECT id_role FROM roles WHERE name = ?;";

        try (
                PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, roleName);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {

                if (!resultSet.isClosed()) {
                    roleId = resultSet.getInt("id_role");
                }
            }

        }catch (SQLException e){
            System.out.println("Role not found");
        }

        return roleId;
    }
}
