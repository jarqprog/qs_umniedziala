package dao;

import model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DaoWallet{

    public Wallet createWallet(){
        return new Wallet();
    }

    public Wallet createWallet(int allCoins, int availableCoins, ArrayList<Artifact> newArtifacts,
                               ArrayList <Artifact> usedArtifacts){
        return new Wallet(allCoins, availableCoins, newArtifacts, usedArtifacts);
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

            if (!resultSet.isClosed()) {
                int allCoins = resultSet.getInt("all_coins");
                int availableCoins = resultSet.getInt("available_coins");
                ArrayList <Artifact> newArtifacts = getUserArtifacts(userID, "new");
                ArrayList <Artifact> usedArtifacts = getUserArtifacts(userID, "used");
                wallet = new Wallet(allCoins, availableCoins, newArtifacts, usedArtifacts);
                resultSet.close();
            }
            preparedStatement.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return wallet;
    }

    public void exportWallet(Student student){

        int value = student.getUserId();
        int allCoins = student.getWallet().getAllCoins();
        int availableCoins = student.getWallet().getAvailableCoins();

        PreparedStatement preparedStatement = null;
        String query = "INSERT into wallets (id_student, all_coins, available_coins)" +
                "values (?, ?, ?);";

        try{
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, value);
            preparedStatement.setInt(2, allCoins);
            preparedStatement.setInt(3, availableCoins);

            preparedStatement.executeUpdate();
            preparedStatement.close();

        }catch (SQLException | ClassNotFoundException e){
            System.out.println("Wallet insertion failed");
        }

    }

    private ArrayList<Artifact> getUserArtifacts(int userID, String status) {

        ArrayList<Artifact> artifacts = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        String query = "Select artifacts.id_artifact from artifacts inner join artifacts_in_wallets "
                       + "on artifacts.id_artifact = artifacts_in_wallets.id_artifact "
                       + "where artifacts_in_wallets.id_student = ? and artifacts_in_wallets.status = ?;";

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, userID);
            preparedStatement.setString(2, status);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int idArtifact = resultSet.getInt("id_artifact");
                Artifact artifact = new DaoArtifact().importArtifact(idArtifact);
                artifacts.add(artifact);
            }
            
            resultSet.close();
            preparedStatement.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        return artifacts;
    }

    public void updateWallet(Student student){

        int allCoins = student.getWallet().getAllCoins();
        int availableCoins = student.getWallet().getAvailableCoins();
        int userId = student.getUserId();

        PreparedStatement preparedStatement = null;
        String query = "update wallets SET all_coins = ?, available_coins = ?"+
                "where id_student= ?;";

        try{
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, allCoins);
            preparedStatement.setInt(2, availableCoins);
            preparedStatement.setInt(3, userId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Wallet update failed");
        }
    }

    public void exportStudentArtifact(int idArtifact, int idStudent) {

        String status = "new";

        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO artifacts_in_wallets (id_artifact, id_student, status)" +
                "VALUES (?, ?, ?);";

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, idArtifact);
            preparedStatement.setInt(2, idStudent);
            preparedStatement.setString(3, status);

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Artifact insertion failed");
        }
    }

    public void updateStudentsArtifact(int idArtifact, int idStudent){

        String statusArtifact = "used";

        PreparedStatement preparedStatement = null;
        String query = "update artifacts_in_wallets SET status = ?"+
                    "where id_artifact= ? and id_student = ?;";

        try{
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, statusArtifact);
            preparedStatement.setInt(2, idArtifact);
            preparedStatement.setInt(3, idStudent);

            preparedStatement.executeUpdate();
            preparedStatement.close();

        }catch (SQLException | ClassNotFoundException e){
            System.out.println("Artifact update failed");
        }
    }
}