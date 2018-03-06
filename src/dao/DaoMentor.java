package dao;

import model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoMentor implements IDaoMentor {

    @Override
    public Mentor createMentor(String name, String password, String email) {
        return new Mentor(name, password, email);
    }

    @Override
    public Mentor createMentor(int userId, String name, String password, String email) {
        return new Mentor(userId, name, password, email);
    }

    @Override
    public Mentor importMentor(int mentorId) {
        Mentor mentor = null;
        PreparedStatement preparedStatement = null;
        int roleId = getRoleID("mentor");
        String query = "SELECT * FROM users WHERE id_user = ? AND id_role = ?;";

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, mentorId);
            preparedStatement.setInt(2, roleId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.isClosed()) {
                int userId = resultSet.getInt("id_user");
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");

                mentor = createMentor(userId, name, password, email);

                resultSet.close();
            }
            preparedStatement.close();

        } catch (SQLException | ClassNotFoundException e) {
            return mentor;
        }
        return mentor;
    }

    @Override
    public boolean exportMentor(Mentor mentor) {

        String name = mentor.getName();
        String password = mentor.getPassword();
        String email = mentor.getEmail();
        int roleId = getRoleID("mentor");

        PreparedStatement preparedStatement = null;
        String query = "INSERT into users (name, password, email, id_role)" +
                       "values (?, ?, ?, ?);";

        try{
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setInt(4, roleId);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;

        }catch (SQLException | ClassNotFoundException e){
            return false;
        }
    }

    @Override
    public boolean updateMentor(Mentor mentor){
        String name = mentor.getName();
        String password = mentor.getPassword();
        String email = mentor.getEmail();
        int mentorId = mentor.getUserId();
        int roleId = getRoleID("mentor");


        PreparedStatement preparedStatement = null;
        String query = "update users SET name = ?, password = ?, email = ? "+
                "where id_user= ? AND id_role = ?;";

        try{
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setInt(4, mentorId);
            preparedStatement.setInt(5, roleId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException | ClassNotFoundException e){
            return false;
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

    @Override
    public Integer getMentorClassId(Mentor mentor){
        Integer classId = null;
        PreparedStatement preparedStatement;

        String query = "SELECT id_codecool_class from mentors_in_classes where id_mentor = ?;";

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, mentor.getUserId());
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.isClosed()) {
                classId = resultSet.getInt("id_codecool_class");
                resultSet.close();
            }
            preparedStatement.close();

        }catch (SQLException | ClassNotFoundException e){
            System.out.println("Class not found");
        }

        return classId;
    }

    @Override
    public ArrayList <Mentor> getAllMentors(){

        ArrayList <Mentor> mentorList = new ArrayList <Mentor> ();
        int roleId = getRoleID("mentor");

        PreparedStatement preparedStatement = null;
        String query = "SELECT * FROM users WHERE id_role = ?;";
        Mentor mentor;

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, roleId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                int userId = resultSet.getInt("id_user");
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");

                mentor = createMentor(userId, name, password, email);
                mentorList.add(mentor);

            }
            resultSet.close();
            preparedStatement.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("No mentors");
        }
        return mentorList;
    }

}
