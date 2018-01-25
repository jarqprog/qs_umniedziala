package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.*;

public class DaoStudent extends Dao {

    public Student createStudent(String name, String password, String email) {
        return new Student(name, password, email);
    }

    public Student createStudent(int userId, String name, String password, String email) {
        return new Student(userId, name, password, email);
    }


    public Student importInstance(int studentId) {
        Student student = null;
        PreparedStatement preparedStatement = null;
        String query = "SELECT * FROM users WHERE id_user = ?;";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery(query);
            int userId = resultSet.getInt("id_user");
            String name = resultSet.getString("name");
            String password = resultSet.getString("password");
            String email = resultSet.getString("email");
            resultSet.close();
            preparedStatement.close();

            student = createStudent(userId, name, password, email);
            Wallet wallet = new DaoWallet().importInstance(studentId);
            student.setWallet(wallet);

        } catch (SQLException e) {
            return student;
        }

        return student;
    }

    public void exportInstance(Student student) {

        String name = student.getName();
        String password = student.getPassword();
        String email = student.getEmail();

        PreparedStatement preparedStatement = null;
        String query = "INSERT into users (name, password, email)" +
                "values (?, ?, ?);";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.executeQuery();
            preparedStatement.close();

        } catch (SQLException e) {
            System.out.println("Student insertion failed");
        }
    }

    public void updateInstance(Student student) {

        String query = "update users" +
                "set name = ?, password = ?, email = ?" +
                "where id_user= ?;";

        String name = student.getName();
        String password = student.getPassword();
        String email = student.getEmail();
        int studentId = student.getUserId();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setInt(3, studentId);
            preparedStatement.executeQuery();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Student update failed");
        }
    }

}

