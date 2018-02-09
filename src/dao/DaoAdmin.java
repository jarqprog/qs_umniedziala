package dao;

import model.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoAdmin implements IDaoUser <Admin>  {

    public Admin createInstance (String name, String password, String email){
        return new Admin(name, password, email);
    }

    public Admin createInstance(int userId, String name, String password, String email){
        return new Admin(userId, name, password, email);
    }

    public Admin importInstance(int adminId){
        Admin admin = null;
        PreparedStatement preparedStatement = null;
        String query = "SELECT * FROM users WHERE id_user = ? AND id_role = ?;";
        int roleId = getRoleID("admin");


        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, adminId);
            preparedStatement.setInt(2, roleId);
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

    public boolean exportInstance(Admin admin){
        String name = admin.getName();
        String password = admin.getPassword();
        String email = admin.getEmail();
        int roleId = getRoleID("admin");

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
    public boolean updateInstance(Admin admin){
        String name = admin.getName();
        String password = admin.getPassword();
        String email = admin.getEmail();
        int adminId = admin.getUserId();
        int roleId = getRoleID("admin");

        PreparedStatement preparedStatement = null;
        String query = "update users SET name = ?, password = ?, email = ?"+
                "where id_user= ? AND id_role = ?;";

        try{

            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setInt(4, adminId);
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
}