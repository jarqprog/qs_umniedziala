package dao;

import model.*;

import java.sql.*;
import java.util.ArrayList;


public class DaoUser {
    private Connection connection = null;
//    private ViewDao view = new ViewDao();

    public boolean setConnection(String databaseName) {
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);
            return true;

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }
    public void closeConnection(){
        try{
            connection.close();

        }catch(SQLException e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public User selectUser(String query){
        User user = null;
        Statement statement = null;
        try{
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            int id_role = resultSet.getInt("id_role");
            String role = getRole(id_role);

            user = createUser(resultSet, role);

            resultSet.close();
            statement.close();

        }catch(SQLException e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return user;
    }

    public String getRole(int id_role){
        String role = null;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select name from roles where id_role=" + id_role + ";");

            role = resultSet.getString("name");

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        return role;
    }

    public User createUser(ResultSet resultSet, String role){
        User user = null;

        int id = resultSet.getInt("id_user");
        String name = resultSet.getString("name");
        String password = resultSet.getString("password");
        String email = resultSet.getString("email");

        switch(role.toUpperCase()){
            case "ADMIN":
                user = Admin(id, name, password, email);
                break;
            case "MENTOR":
                user = Mentor(id, name, password, email);
                break;
            case "STUDENT":
                Wallet wallet = getWallet();
                break;
        }

        return user;
    }

    private Wallet getWallet(int userID) {
        Wallet wallet = null;
        Statement statement = null;
        ArrayList<Artifact> artifacts = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from wallets where id_student=" + userID + ";");

            int allCoins = resultSet.getInt("all_coins");
            int availableCoins = resultSet.getInt("available_coins");
            artifacts = getUserArtifacts(userID);

            wallet = new Wallet(allCoins, availableCoins, artifacts);

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        return wallet;
    }

    private ArrayList<Artifact> getUserArtifacts(int userID) {
    }
}
