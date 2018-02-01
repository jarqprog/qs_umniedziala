package dao;

import model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DaoAdmin implements IDaoUser <Admin> {

    public Admin createInstance (String name, String password, String email){
        return new Admin(name, password, email);
    }

    public Admin createInstance(int userId, String name, String password, String email){
        return new Admin(userId, name, password, email);
    }

    public Admin importInstance(int adminId){
        Admin admin = null;
        PreparedStatement preparedStatement = null;
        String query = "SELECT * FROM users WHERE id_user = ?;";

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, adminId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.isClosed()) {
                int userId = resultSet.getInt("id_user");
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");

                admin = createInstance(userId, name, password, email);

                resultSet.close();
            }
            preparedStatement.close();

        } catch (SQLException | ClassNotFoundException e) {
            return admin;
        }
        return admin;
    }

    public void exportInstance(Admin admin){
        String name = admin.getName();
        String password = admin.getPassword();
        String email = admin.getEmail();

        PreparedStatement preparedStatement = null;
        String query = "INSERT into users (name, password, email)" +
                "values (?, ?, ?);";

        try{
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);

            preparedStatement.executeQuery();
            preparedStatement.close();

        }catch (SQLException | ClassNotFoundException e){
            System.out.println("Admin insertion failed");
        }
    }
    public void updateInstance(Admin admin){
        String name = admin.getName();
        String password = admin.getPassword();
        String email = admin.getEmail();
        int adminId = admin.getUserId();

        PreparedStatement preparedStatement = null;
        String query = "update users SET name = ?, password = ?, email = ?"+
                "where id_user= ?;";

        try{
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setInt(4, adminId);
            preparedStatement.executeQuery();
            preparedStatement.close();
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Admin update failed");
        }
    }
}