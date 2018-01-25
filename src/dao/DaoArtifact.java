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
        
}