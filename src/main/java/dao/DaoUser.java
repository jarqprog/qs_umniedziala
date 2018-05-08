package dao;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DaoUser extends SqlDao {

    private final String DATABASE_TABLE = "users";
    private final String ID_LABEL = "id_user";

    DaoUser(Connection connection) {
        super(connection);
    }

    protected boolean exportUser(User user, String roleName) {

        String query = "INSERT INTO users (id_user, name, password, email, id_role)" +
                "VALUES (?, ?, ?, ?, ?);";

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, user.getUserId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setInt(5, getRoleID(roleName));
            preparedStatement.executeUpdate();
            return true;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    protected boolean updateUser(User user, String roleName){

        String query = "UPDATE users SET name = ?, password = ?, email = ?"+
                "WHERE id_user= ? AND id_role = ?;";

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setInt(4, user.getUserId());
            preparedStatement.setInt(5, getRoleID(roleName));
            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    protected int getRoleID(String roleName){

        int roleId = 0;
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

    protected String getDatabaseTable() {
        return DATABASE_TABLE;
    }

    protected String getIdLabel() {
        return ID_LABEL;
    }
}
