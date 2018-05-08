package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.*;

public class DaoStudent extends SqlDao implements IDaoStudent{

    private final IDaoWallet daoWallet;
    private final String DATABASE_TABLE = "users";
    private final String ID_LABEL = "id_user";

    DaoStudent(Connection connection, IDaoWallet daoWallet) {
        super(connection);
        this.daoWallet = daoWallet;
    }

    @Override
    public Student createStudent(String name, String password, String email) {

        try {
            int id = getLowestFreeIdFromGivenTable(DATABASE_TABLE, ID_LABEL);
            return new Student(id, name, password, email);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public Student importStudent(int studentId) {
        Student student = null;

        int roleId = getRoleID();

        String query = "SELECT * FROM users WHERE id_user = ? AND id_role = ?;";

        try (
             PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
             preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, roleId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){

                int userId = resultSet.getInt(ID_LABEL);

                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");

                student = new Student(userId, name, password, email);
                Wallet wallet = daoWallet.importWallet(studentId);
                student.setWallet(wallet);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return student;
    }

    @Override
    public Student importNewStudent(String userEmail){

        Student student = null;

        int roleId = getRoleID();

        String query = "SELECT * FROM users WHERE email = ? AND id_role = ?;";

        try (
             PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {

            preparedStatement.setString(1, userEmail);
            preparedStatement.setInt(2, roleId);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {

                    int userId = resultSet.getInt(ID_LABEL);

                    String name = resultSet.getString("name");
                    String password = resultSet.getString("password");
                    String email = resultSet.getString("email");

                    student = new Student(userId, name, password, email);
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return student;
    }


    @Override
    public boolean exportStudent(Student student) {

        String name = student.getName();
        String password = student.getPassword();
        String email = student.getEmail();
        int roleId = getRoleID();

        String query = "INSERT INTO users (name, password, email, id_role)" +
                "VALUES (?, ?, ?, ?);";

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setInt(4, roleId);

            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateStudent(Student student) {
        String name = student.getName();
        String password = student.getPassword();
        String email = student.getEmail();
        int studentId = student.getUserId();
        int roleId = getRoleID();

        String query = "UPDATE users set name = ?, password = ?, email = ? WHERE id_user= ? AND id_role = ?;";

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setInt(4, studentId);
            preparedStatement.setInt(5, roleId);

            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        int roleId = getRoleID();
        String query = "SELECT id_user FROM users WHERE id_role = ?;";

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {

            preparedStatement.setInt(1, roleId);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int userId = resultSet.getInt(ID_LABEL);
                    Student student = importStudent(userId);
                    students.add(student);
                }
            }

        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return students;
    }

    private int getRoleID(){

        String roleName = "student";
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

