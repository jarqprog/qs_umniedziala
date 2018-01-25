package dao;

import model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DaoWallet extends Dao {

    public Wallet createWallet(){
        return new Wallet();
    }

    public Wallet createWallet(int allCoins, int availableCoins, ArrayList<Artifact> artifacts){
        return new Wallet(allCoins, availableCoins, artifacts);
    }

    public Wallet importInstance(int userID) {
        Wallet wallet = null;
        PreparedStatement preparedStatement = null;
        ArrayList<Artifact> artifacts = null;
        String query = "Select * from wallets where id_student= ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int allCoins = resultSet.getInt("all_coins");
                int availableCoins = resultSet.getInt("available_coins");
                artifacts = getUserArtifacts(userID);
                wallet = new Wallet(allCoins, availableCoins, artifacts);
            }
            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return wallet;
    }

    private ArrayList<Artifact> getUserArtifacts(int userID) {

        ArrayList<Artifact> artifacts = null;
        PreparedStatement preparedStatement = null;
        String query = "Select name, value, description, type from artifacts inner join artifacts_in_wallets on artifacts.id_artifact = artifacts_in_wallets.id_artifact where artifacts_in_wallets.id_student = ?;";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userID);

            ResultSet resultSet = preparedStatement.executeQuery();
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
