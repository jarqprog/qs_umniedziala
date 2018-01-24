package dao;

import model.*;

import java.sql.*;
import java.util.ArrayList;


public class DaoUser {
    private Connection connection = null;

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

    public User getUser(String email, String password){
        User user = null;
        Statement statement = null;
        String query = "SELECT * from users where email='" + email + "' AND password='" + password + "';";
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
                user = Admin(userId, name, password, email);
                break;
            case "MENTOR":
                user = Mentor(userId, name, password, email);
                break;
            case "STUDENT":
                Wallet wallet = getWallet(userId);
                user = Student(userId, name, password, email, wallet);
                break;
        }

        return user;
    }

    //Dao Wallet
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

    //Dao artifact lub Wallet
    private ArrayList<Artifact> getUserArtifacts(int userID) {
        ArrayList<Artifact> artifacts = null;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(); /*Dopisać i przekazać zapytanie
            inner join artifacts_in_wallets and artifacts on id_artifact where id_user=userID???
            */

            while(resultSet.next()){
                int idArtifact = resultSet.getInt("id_artifact");
                String name = resultSet.getString("name");
                int value = resultSet.getInt("value");
                String description = resultSet.getString("description");
                String type = resultSet.getString("status");

                artifacts.add(Artifact(idArtifact, name, value, description, type));

            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        return artifacts;
    }
}
