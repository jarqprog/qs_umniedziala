package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.*;

public class DaoStudent extends Dao {

    public Student createStudent(String name, String password, String email){
        return new Student(name, password, email);
    }

    public Student createStudent(int userId, String name, String password, String email){
        return new Student(userId, name, password, email);
    }


    public Student importInstance(int studentId) {
        Student student = null;
        PreparedStatement preparedStatement = null;
        String query = "SELECT * FROM users WHERE id_user = ?;";

        try {
            preparedStatement = DbConnection.getInstance().prepareStatement(query);
            preparedStatement.setInt(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery(query);
            int userId = resultSet.getInt("id_user");
            String name = resultSet.getString("name");
            String password = resultSet.getString("password");
            String email = resultSet.getString("email");
            resultSet.close();
            preparedStatement.close();

            student = createStudent(userId, name, password, email);

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
                "value (?, ?, ?);";

        try{
            preparedStatement = DbConnection.getInstance().prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.executeQuery();
            preparedStatement.close();

        }catch (SQLException e){
            System.out.println("Student insertion failed");
        }
    }

    private class DaoWallet{

        private Wallet wallet = new Wallet();

        public Wallet getWallet(){
            return wallet;
        }

        public Wallet implementTestData(){
            ArrayList <Artifact> artifacts = new ArrayList<>();
            DaoArtifact daoArtifact = new DaoArtifact();

            artifacts.add(daoArtifact.getArtifactById(1));
            artifacts.add(daoArtifact.getArtifactById(2));
            
            return new Wallet(56, 120, artifacts);
        }
        
    }

}
