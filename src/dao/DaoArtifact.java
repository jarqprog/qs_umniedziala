package dao;

import model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoArtifact extends Dao{

    public Artifact createArtifact(String name, int value, String description, String type) {
        return new Artifact(name, value, description, type);
    }

    public Artifact createArtifact(int itemId, String name, int value, String description, String type) {
        return new Artifact(itemId, name, value, description, type);
    }

    public Artifact importInstance(int itemId) {
        Artifact artifact = null;
        PreparedStatement preparedStatement = null;
        String query = "Select * from artifacts where id_artifact = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, itemId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                int value = resultSet.getInt("value");
                String description = resultSet.getString("description");
                String type = resultSet.getString("type");

                artifact = new Artifact(itemId, name, value, description, type);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        return artifact;
    }
        
}
