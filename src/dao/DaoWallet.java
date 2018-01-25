package dao;

import model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DaoWallet extends Dao {

    private Wallet importInstance(int userID) {
        Wallet wallet = null;
        PreparedStatement preparedStatement = null;
        ArrayList<Artifact> artifacts = null;
        String query = "Select * from wallets where id_student= ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

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

}
