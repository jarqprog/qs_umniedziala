package dao;

import model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoArtifact{

    public Artifact createArtifact(String name, int value, String description, String type) {
        return new Artifact(name, value, description, type);
    }

    public Artifact createArtifact(int itemId, String name, int value, String description, String type) {
        return new Artifact(itemId, name, value, description, type);
    }

    public Artifact importArtifact(int itemId) {
        Artifact artifact = null;
        PreparedStatement preparedStatement = null;
        String query = "Select * from artifacts where id_artifact = ?";
        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, itemId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.isClosed()) {
                String name = resultSet.getString("name");
                int value = resultSet.getInt("value");
                String description = resultSet.getString("description");
                String type = resultSet.getString("type");

                artifact = new Artifact(itemId, name, value, description, type);
                resultSet.close();
            }

            preparedStatement.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        return artifact;
    }
        
}
