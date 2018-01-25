package dao;

import model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoMentor extends Dao {

    public Mentor createMentor(String name, String password, String email) {
        return new Mentor(name, password, email);
    }

    public Mentor createMentor(int userId, String name, String password, String email) {
        return new Mentor(userId, name, password, email);
    }

    public Mentor importInstance(int mentorId) {
        Mentor mentor = null;
        PreparedStatement preparedStatement = null;
        String query = "SELECT * FROM users WHERE id_user = ?;";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, mentorId);
            ResultSet resultSet = preparedStatement.executeQuery(query);
            int userId = resultSet.getInt("id_user");
            String name = resultSet.getString("name");
            String password = resultSet.getString("password");
            String email = resultSet.getString("email");
            resultSet.close();
            preparedStatement.close();

            mentor = createMentor(userId, name, password, email);

        } catch (SQLException e) {
            return mentor;
        }
        return mentor;
    }

    public void exportInstance(Mentor mentor) {

        String name = mentor.getName();
        String password = mentor.getPassword();
        String email = mentor.getEmail();

        PreparedStatement preparedStatement = null;
        String query = "INSERT into users (name, password, email)" +
                       "values (?, ?, ?);";

        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.executeQuery();
            preparedStatement.close();

        }catch (SQLException e){
            System.out.println("Mentor insertion failed");
        }
    }

    public void updateInstance(Mentor mentor){

        String query = "update users" +
                "set name = ?, password = ?, email = ?"+
                "where id_user= ?;";

        String name = mentor.getName();
        String password = mentor.getPassword();
        String email = mentor.getEmail();
        int mentorId = mentor.getUserId();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setInt(4, mentorId);
            preparedStatement.executeQuery();
            preparedStatement.close();
        } catch (SQLException e){
            System.out.println("Student update failed");
        }
    }

}
