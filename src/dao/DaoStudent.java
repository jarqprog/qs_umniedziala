package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import model.*;

public class DaoStudent implements IDaoUser <Student> {

    public Student createInstance(String name, String password, String email) {
        return new Student(name, password, email);
    }

    public Student createInstance(int userId, String name, String password, String email) {
        return new Student(userId, name, password, email);
    }


    public Student importInstance(int studentId) {
        Student student = null;
        PreparedStatement preparedStatement = null;
        String query = "SELECT * FROM users WHERE id_user = ?;";

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, String.valueOf(studentId));
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.isClosed()){

                int userId = resultSet.getInt("id_user");

                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");

                student = createInstance(userId, name, password, email);
                Wallet wallet = new DaoWallet().importInstance(studentId);
                student.setWallet(wallet);

                resultSet.close();
            }
            preparedStatement.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return student;
        }

        return student;
    }

    public Student getByEmail(String userEmail){

        Student student = null;
        PreparedStatement preparedStatement = null;
        String query = "SELECT * FROM users WHERE email = ?;";

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, userEmail);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.isClosed()){

                int userId = resultSet.getInt("id_user");

                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");

                student = createInstance(userId, name, password, email);
                Wallet wallet = new DaoWallet().importInstance(userId);
                student.setWallet(wallet);

                resultSet.close();
            }
            preparedStatement.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return student;
        }

        return student;
    }


    public void exportInstance(Student student) {

        String name = student.getName();
        String password = student.getPassword();
        String email = student.getEmail();
        int roleId = getRoleID("student");

        PreparedStatement preparedStatement = null;
        String query = "INSERT into users (name, password, email, id_role)" +
                "values (?, ?, ?, ?);";

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setInt(4, roleId);

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Student insertion failed");
        }
    }

    public void updateInstance(Student student) {
        String name = student.getName();
        String password = student.getPassword();
        String email = student.getEmail();
        int studentId = student.getUserId();

        PreparedStatement preparedStatement = null;
        String query = "update users set name = ?, password = ?, email = ? where id_user= ?;";

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setInt(3, studentId);

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Student update failed");
        }
    }

    public int getRoleID(String roleName){

        int roleId = 0;
        PreparedStatement preparedStatement = null;

        String query = "SELECT id_role from roles where name = ?;";

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, roleName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.isClosed()) {
                roleId = resultSet.getInt("id_role");
                resultSet.close();
            }
            preparedStatement.close();

        }catch (SQLException | ClassNotFoundException e){
            System.out.println("Role not found");
        }

        return roleId;

    }
    
}

