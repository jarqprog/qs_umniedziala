package system.dao;

import system.model.User;


import java.sql.*;

public class DaoLogin extends SqlDao implements IDaoLogin {

    private final IDaoAdmin daoAdmin;
    private final IDaoMentor daoMentor;
    private final IDaoStudent daoStudent;


    DaoLogin(Connection connection, IDaoAdmin daoAdmin, IDaoMentor daoMentor, IDaoStudent daoStudent) {
        super(connection);
        this.daoAdmin = daoAdmin;
        this.daoMentor = daoMentor;
        this.daoStudent = daoStudent;
    }

    @Override
    public User getUser(String email, String password){
        User user = null;

        String query = "SELECT * FROM users WHERE email= ? AND password= ?;";
        try (
             PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                int id_role = resultSet.getInt("id_role");
                String role = getRole(id_role);
                user = importUser(resultSet, role);
            }

        }catch(SQLException e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return user;
    }

    @Override
    public String getRole(int roleId){

        String role = "n/a";

        String query = "SELECT name FROM roles WHERE id_role= ?;";
        try (
             PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
             preparedStatement.setInt(1, roleId);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {

                if ( resultSet.next() ) {
                    role = resultSet.getString("name");

                }
            }

        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return role;
    }

    private User importUser(ResultSet resultSet, String role) throws SQLException {
        User user = null;  // because of login in Login (handler - server package)
        int userId;
        try {
            userId = resultSet.getInt("id_user");
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        switch (role.toUpperCase()) {
            case "ADMIN":
                user = daoAdmin.importAdmin(userId);
                break;
            case "MENTOR":
                user = daoMentor.importMentor(userId);
                break;
            case "STUDENT":
                user = daoStudent.importStudent(userId);
                break;
        }
        return user;
    }

}
