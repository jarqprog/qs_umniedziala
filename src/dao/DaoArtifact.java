package dao;

import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public void exportArtifact(Artifact artifact) {

        String query = "INSERT INTO artifacts VALUES (?, ?, ?, ?, ?);";

        try{
            Connection connection = DbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(2, artifact.getName());
            preparedStatement.setInt(3, artifact.getValue());
            preparedStatement.setString(4, artifact.getDescription());
            preparedStatement.setString(5, artifact.getType());

            preparedStatement.executeUpdate();
            preparedStatement.close();

        }catch (SQLException | ClassNotFoundException e){
            System.out.println("Artifact insertion failed");
        }

    }

    public ArrayList<Artifact> getArtifacts(String status) {
        return new ArrayList<Artifact>();
    }
        
}
