package dao;

import model.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoAdmin extends DaoUser implements IDaoAdmin  {

    private final String ROLE_NAME = "admin";

    DaoAdmin(Connection connection) {
        super(connection);
    }

    @Override
    public Admin createAdmin (String name, String password, String email) {

        try {
            int id = getLowestFreeIdFromGivenTable(getDatabaseTable(), getIdLabel());
            Admin admin = new Admin(id, name, password, email);
            exportUser(admin, ROLE_NAME);
            return admin;
        } catch (SQLException e) {
            e.printStackTrace();
            return new NullAdmin();
        }
    }

    @Override
    public Admin importAdmin(int adminId){

        String query = "SELECT * FROM users WHERE id_user = ? AND id_role = ?;";
        int roleId = getRoleID(ROLE_NAME);

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)){
            
            preparedStatement.setInt(1, adminId);
            preparedStatement.setInt(2, roleId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                int userId = resultSet.getInt(getIdLabel());
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");

                return new Admin(userId, name, password, email);
            }
            return new NullAdmin();

        } catch (SQLException e) {
            e.printStackTrace();
            return new NullAdmin();
        }
    }

    @Override
    public boolean updateAdmin(Admin admin){
        return updateUser(admin, ROLE_NAME);
    }
}