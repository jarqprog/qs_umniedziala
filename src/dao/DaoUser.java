package dao;


import model.*;

import java.sql.*;
import java.util.ArrayList;


public class DaoUser extends Dao{

    public User getUser(String email, String password){
        User user = null;
        PreparedStatement preparedStatement = null;
        String query = "SELECT * from users where email= ? AND password= ?;";
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            int id_role = resultSet.getInt("id_role");
            String role = getRole(id_role);

            user = createUser(resultSet, role);

            resultSet.close();
            preparedStatement.close();

        }catch(SQLException e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return user;
    }

    public String getRole(int id_role){

        String role = null;
        PreparedStatement preparedStatement = null;
        String query = "Select name from roles where id_role= ?;";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id_role);
            ResultSet resultSet = preparedStatement.executeQuery();


            role = resultSet.getString("name");

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        return role;
    }

    private User createUser(ResultSet resultSet, String role){
        /*Kiedy dao userów będą gotowe, stworzyć w nich metode
        createstudent/mentor/admin(ResultSet):User
        do wywołania w switch - case
        */
        User user = null;

        int userId = 0;
        String name = null;
        String password = null;
        String email = null;

        try {
            userId = resultSet.getInt("id_user");
            name = resultSet.getString("name");
            password = resultSet.getString("password");
            email = resultSet.getString("email");
        } catch (SQLException e) {
            return user;
        }

        switch(role.toUpperCase()){  //tu podpiąć odpowiednie DAO
            case "ADMIN":
                user = new Admin(userId, name, password, email);
                break;
            case "MENTOR":
                user = new Mentor(userId, name, password, email);
                break;
            case "STUDENT":
                Student student = new Student(userId, name, password, email);
                Wallet wallet = getWallet(userId);
                student.setWallet(wallet);
                user = student;
                break;
        }

        return user;
    }

}
