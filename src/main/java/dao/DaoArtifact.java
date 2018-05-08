package dao;

import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoArtifact extends SqlDao implements IDaoArtifact{

    private final String DATABASE_TABLE = "artifacts";
    private final String ID_LABEL = "id_artifact";

    DaoArtifact(Connection connection) {
        super(connection);
    }

    public Artifact createArtifact(String name, int value, String description, String type) {
        try {
            int id = getLowestFreeIdFromGivenTable(DATABASE_TABLE, ID_LABEL);
            return new Artifact(id, name, value, description, type);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Artifact createArtifact(int itemId, String name, int value, String description, String type) {
        return new Artifact(itemId, name, value, description, type);
    }

    public Artifact importArtifact(int itemId) {
        Artifact artifact = null;
        String query = "SELECT * FROM artifacts WHERE id_artifact = ?";

        try (
             PreparedStatement preparedStatement = getConnection().prepareStatement(query)){
            
            preparedStatement.setInt(1, itemId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.isClosed()) {
                String name = resultSet.getString("name");
                int value = resultSet.getInt("value");
                String description = resultSet.getString("description");
                String type = resultSet.getString("type");

                artifact = new Artifact(itemId, name, value, description, type);
                
            }

            
        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        return artifact;
    }


    public List<Artifact> getAllArtifacts() {
        List<Artifact> artifacts = new ArrayList<>();
        String query = "SELECT id_artifact FROM artifacts;";

        try (
             PreparedStatement preparedStatement = getConnection().prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()){
                int artifactId = resultSet.getInt("id_artifact");
                Artifact artifact = importArtifact(artifactId);
                artifacts.add(artifact);
            }

        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        return artifacts;
    }

    public boolean updateArtifact(Artifact artifact) {
        int itemId = artifact.getItemId();
        String name = artifact.getName();
        int value = artifact.getValue();
        String description = artifact.getDescription();
        String type = artifact.getType();

        

        String query = "UPDATE artifacts SET " +
                "name = ?, value = ?, description = ?, type = ? " +
                "WHERE id_artifact = ?;";

        try (
             PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, value);
            preparedStatement.setString(3, description);
            preparedStatement.setString(4, type);
            preparedStatement.setInt(5, itemId);

            preparedStatement.executeUpdate();
            
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean exportArtifact(Artifact artifact) {

        String query = "INSERT INTO artifacts VALUES (?, ?, ?, ?, ?);";

        try (
             PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {

            preparedStatement.setString(2, artifact.getName());
            preparedStatement.setInt(3, artifact.getValue());
            preparedStatement.setString(4, artifact.getDescription());
            preparedStatement.setString(5, artifact.getType());

            preparedStatement.executeUpdate();
            
            return true;

        }catch (SQLException e){
            return false;
        }
    }

    public List<Artifact> getArtifacts(String type) {
        List<Artifact> artifacts = new ArrayList<>();
        String query = "SELECT id_artifact FROM artifacts WHERE type = ?;";

        try (
             PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {


            preparedStatement.setString(1, type);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int artifactId = resultSet.getInt("id_artifact");
                    Artifact artifact = importArtifact(artifactId);
                    artifacts.add(artifact);
                }
            }

        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return artifacts;
    }
        
}
