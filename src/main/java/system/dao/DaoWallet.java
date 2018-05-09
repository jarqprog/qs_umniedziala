package dao;

import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoWallet extends SqlDao implements IDaoWallet {

    private final IDaoArtifact daoArtifact;

    DaoWallet(Connection connection, IDaoArtifact daoArtifact) {
        super(connection);
        this.daoArtifact = daoArtifact;
    }

    @Override
    public Wallet createWallet(Student student){
        try {
            Wallet wallet = new Wallet();
            exportWallet(student);
            return wallet;
        } catch (SQLException e) {
            e.printStackTrace();
            return new NullWallet();
        }
    }

    @Override
    public Wallet importWallet(int userID) {

        String query = "SELECT * FROM wallets WHERE id_student= ?";
        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {

            preparedStatement.setInt(1, userID);
            try(ResultSet resultSet = preparedStatement.executeQuery()){

                if ( resultSet.next() ) {
                    int allCoins = resultSet.getInt("all_coins");
                    int availableCoins = resultSet.getInt("available_coins");
                    List<Artifact> newArtifacts = getUserArtifacts(userID, "new");
                    List<Artifact> usedArtifacts = getUserArtifacts(userID, "used");
                    return new Wallet(allCoins, availableCoins, newArtifacts, usedArtifacts);
                }
                return new NullWallet();
            }

        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return new NullWallet();
        }
    }

    private boolean exportWallet(Student student) throws SQLException {
        if(student == null || student instanceof NullStudent){
            throw new SQLException("Student is invalid!");
        }
        int value = student.getUserId();
        int allCoins = student.getWallet().getAllCoins();
        int availableCoins = student.getWallet().getAvailableCoins();

        String query = "INSERT INTO wallets (id_student, all_coins, available_coins)" +
                "VALUES (?, ?, ?);";

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {

            preparedStatement.setInt(1, value);
            preparedStatement.setInt(2, allCoins);
            preparedStatement.setInt(3, availableCoins);
            preparedStatement.executeUpdate();
            return true;
        }
    }

    @Override
    public boolean updateWallet(Student student){

        String query = "UPDATE wallets SET all_coins = ?, available_coins = ?"+
                "WHERE id_student= ?;";

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {

            preparedStatement.setInt(1, student.getWallet().getAllCoins());
            preparedStatement.setInt(2, student.getWallet().getAvailableCoins());
            preparedStatement.setInt(3, student.getUserId());
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Wallet update failed");
            return false;
        }
    }

    @Override
    public boolean exportStudentArtifact(int idArtifact, int idStudent) {

        String status = "new";
        String query = "INSERT INTO artifacts_in_wallets (id_artifact, id_student, status)" +
                "VALUES (?, ?, ?);";

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {

            preparedStatement.setInt(1, idArtifact);
            preparedStatement.setInt(2, idStudent);
            preparedStatement.setString(3, status);

            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Artifact insertion failed");
            return false;
        }
    }

    @Override
    public boolean updateStudentsArtifact(int idArtifact, int idStudent){

        String statusArtifact = "used";

        String query = "UPDATE artifacts_in_wallets SET status = ?"+
                    "WHERE id_artifact= ? and id_student = ?;";

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {

            preparedStatement.setString(1, statusArtifact);
            preparedStatement.setInt(2, idArtifact);
            preparedStatement.setInt(3, idStudent);
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Artifact update failed");
            return false;
        }
    }

    private List<Artifact> getUserArtifacts(int userID, String status) {

        List<Artifact> artifacts = new ArrayList<>();

        String query = "SELECT artifacts.id_artifact FROM artifacts inner join artifacts_in_wallets "
                + "on artifacts.id_artifact = artifacts_in_wallets.id_artifact "
                + "WHERE artifacts_in_wallets.id_student = ? and artifacts_in_wallets.status = ?;";

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {

            preparedStatement.setInt(1, userID);
            preparedStatement.setString(2, status);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int idArtifact = resultSet.getInt("id_artifact");
                    Artifact artifact = daoArtifact.importArtifact(idArtifact);
                    artifacts.add(artifact);
                }
            }
        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return artifacts;
    }
}