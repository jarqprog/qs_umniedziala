package dao;

import model.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoAdmin extends SqlDao implements IDaoAdmin  {

    private final String DATABASE_TABLE = "users";
    private final String ID_LABEL = "id_user";

    DaoAdmin(Connection connection) {
        super(connection);
    }

    @Override
    public Admin createAdmin (String name, String password, String email) {

        try {
            int id = getLowestFreeIdFromGivenTable(DATABASE_TABLE, ID_LABEL);
            return new Admin(id, name, password, email);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Admin importAdmin(int adminId){
        Admin admin = null;
        String query = "SELECT * FROM users WHERE id_user = ? AND id_role = ?;";
        int roleId = getRoleID();


        try (
                 PreparedStatement preparedStatement = getConnection().prepareStatement(query)){
            
            preparedStatement.setInt(1, adminId);
            preparedStatement.setInt(2, roleId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                int userId = resultSet.getInt(ID_LABEL);
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");

                admin = new Admin(userId, name, password, email);
            }
            

        } catch (SQLException e) {
            e.printStackTrace();
            return admin;
        }
        return admin;
    }
    @Override
    public boolean exportAdmin(Admin admin){
        String name = admin.getName();
        String password = admin.getPassword();
        String email = admin.getEmail();
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

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateAdmin(Admin admin){
        String name = admin.getName();
        String password = admin.getPassword();
        String email = admin.getEmail();
        int adminId = admin.getUserId();
        int roleId = getRoleID();

        
        String query = "UPDATE users SET name = ?, password = ?, email = ?"+
                "WHERE id_user= ? AND id_role = ?;";

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setInt(4, adminId);
            preparedStatement.setInt(5, roleId);
            preparedStatement.executeUpdate();
            
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    private int getRoleID(){

        int roleId = 0;
        String roleName = "admin";
        String query = "SELECT id_role FROM roles WHERE name = ?;";

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setString(1, roleName);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                roleId = resultSet.getInt("id_role");
            }

        }catch (SQLException e){
            System.out.println("Role not found");
        }
        return roleId;
    }
}