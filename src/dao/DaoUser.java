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
            ResultSet resultSet = preparedStatement.executeQuery(query);

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
            ResultSet resultSet = preparedStatement.executeQuery(query);


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

    //Dao Wallet
    private Wallet getWallet(int userID) {
        Wallet wallet = null;
        PreparedStatement preparedStatement = null;
        ArrayList<Artifact> artifacts = null;
        String query = "Select * from wallets where id_student= ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery(query);

            int allCoins = resultSet.getInt("all_coins");
            int availableCoins = resultSet.getInt("available_coins");
            artifacts = getUserArtifacts(userID);

            wallet = new Wallet(allCoins, availableCoins, artifacts);

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        return wallet;
    }

    //Dao artifact lub Wallet
    private ArrayList<Artifact> getUserArtifacts(int userID) {

        ArrayList<Artifact> artifacts = null;
        PreparedStatement preparedStatement = null;
        String query = "Select name, value, description, type from artifacts inner join artifacts_in_wallets on artifacts.id_artifact = artifacts_in_wallets.id_artifact where artifacts_in_wallets.id_student = ?;";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userID);
            
            ResultSet resultSet = preparedStatement.executeQuery(query);
            /*Dopisać i przekazać zapytanie
            inner join artifacts_in_wallets and artifacts on id_artifact where id_user=userID???
            */


            while(resultSet.next()){
                int idArtifact = resultSet.getInt("id_artifact");
                String name = resultSet.getString("name");
                int value = resultSet.getInt("value");
                String description = resultSet.getString("description");
                String type = resultSet.getString("status");

                artifacts.add(new Artifact(idArtifact, name, value, description, type));

            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        return artifacts;
    }
}
