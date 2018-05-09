package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.*;

public class DaoStudent extends DaoUser implements IDaoStudent{

    private final IDaoWallet daoWallet;
    private final String ROLE_NAME = "student";

    DaoStudent(Connection connection, IDaoWallet daoWallet) {
        super(connection);
        this.daoWallet = daoWallet;
    }

    @Override
    public Student createStudent(String name, String password, String email) {

        try {
            int id = getLowestFreeIdFromGivenTable(getDatabaseTable(), getIdLabel());
            Student student = new Student(id, name, password, email);
            exportUser(student, ROLE_NAME);
            return student;
        } catch (SQLException e) {
            e.printStackTrace();
            return new NullStudent();
        }
    }

    @Override
    public Student importStudent(int studentId) {

        int roleId = getRoleID(ROLE_NAME);

        String query = "SELECT * FROM users WHERE id_user = ? AND id_role = ?;";

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, roleId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){

                int userId = resultSet.getInt(getIdLabel());

                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");

                Student student = new Student(userId, name, password, email);
                Wallet wallet = daoWallet.importWallet(studentId);
                student.setWallet(wallet);
                return student;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            new NullStudent();
        }
        return new NullStudent();
    }

    @Override
    public Student importNewStudent(String userEmail){

        int roleId = getRoleID(ROLE_NAME);

        String query = "SELECT * FROM users WHERE email = ? AND id_role = ?;";

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {

            preparedStatement.setString(1, userEmail);
            preparedStatement.setInt(2, roleId);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {

                    int userId = resultSet.getInt(getIdLabel());

                    String name = resultSet.getString("name");
                    String password = resultSet.getString("password");
                    String email = resultSet.getString("email");

                    return new Student(userId, name, password, email);
                }
                return new NullStudent();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new NullStudent();
        }
    }

    @Override
    public boolean updateStudent(Student student) {
        return updateUser(student, ROLE_NAME);
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        int roleId = getRoleID(ROLE_NAME);
        String query = "SELECT id_user FROM users WHERE id_role = ?;";

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {

            preparedStatement.setInt(1, roleId);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int userId = resultSet.getInt(getIdLabel());
                    Student student = importStudent(userId);
                    students.add(student);
                }
            }

        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return students;
    }
}

