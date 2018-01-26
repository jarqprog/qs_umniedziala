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
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
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

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return wallet;
    }

    private ArrayList<Artifact> getUserArtifacts(int userID) {

        ArrayList<Artifact> artifacts = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        String query = "Select artifacts.id_artifact from artifacts inner join artifacts_in_wallets "
                       + "on artifacts.id_artifact = artifacts_in_wallets.id_artifact "
                       + "where artifacts_in_wallets.id_student = ?;";

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int idArtifact = resultSet.getInt("id_artifact");
                Artifact artifact = new DaoArtifact().importInstance(idArtifact);
                artifacts.add(artifact);
            }
            resultSet.close();
            preparedStatement.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        return artifacts;
    }

}
