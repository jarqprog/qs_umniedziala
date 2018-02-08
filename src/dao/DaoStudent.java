package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

import model.*;

public class DaoStudent implements IDaoUser <Student>{

    public Student createInstance(String name, String password, String email) {
        return new Student(name, password, email);
    }

    public Student createInstance(int userId, String name, String password, String email) {
        return new Student(userId, name, password, email);
    }


    public Student importInstance(int studentId) {
        Student student = null;
        PreparedStatement preparedStatement = null;
        int roleId = getRoleID("mentor");

        String query = "SELECT * FROM users WHERE id_user = ? AND id_role = ?;";

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, roleId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.isClosed()){

                int userId = resultSet.getInt("id_user");

                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");

                student = createInstance(userId, name, password, email);
                Wallet wallet = new DaoWallet().importWallet(studentId);
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

    public Student importNewStudent(String userEmail){

        Student student = null;
        PreparedStatement preparedStatement = null;
        int roleId = getRoleID("mentor");

        String query = "SELECT * FROM users WHERE email = ? AND id_role = ?;";

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, userEmail);
            preparedStatement.setInt(2, roleId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.isClosed()){

                int userId = resultSet.getInt("id_user");

                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");

                student = createInstance(userId, name, password, email);

                resultSet.close();
            }
            preparedStatement.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return student;
        }

        return student;
    }


    public boolean exportInstance(Student student) {

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
            return true;

        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
    }

    public void updateInstance(Student student) {
        String name = student.getName();
        String password = student.getPassword();
        String email = student.getEmail();
        int studentId = student.getUserId();
        int roleId = getRoleID("mentor");


        PreparedStatement preparedStatement = null;
        String query = "update users set name = ?, password = ?, email = ? where id_user= ? AND id_role = ?;";

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setInt(4, studentId);
            preparedStatement.setInt(5, roleId);

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

    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> students = new ArrayList<>();
        int roleId = getRoleID("student");
        String query = "SELECT id_user FROM users WHERE id_role = ?;";

        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, roleId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int userId = resultSet.getInt("id_user");
                Student student = importInstance(userId);
                students.add(student);
            }

            resultSet.close();
            preparedStatement.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        return students;
    }
    
}

