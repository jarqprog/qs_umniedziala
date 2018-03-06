package dao;

import model.Artifact;
import java.util.List;

public interface IDaoArtifact {
    Artifact createArtifact(String name, int value, String description, String type);
    Artifact createArtifact(int itemId, String name, int value, String description, String type);
    Artifact importArtifact(int itemId);
    List<Artifact> getAllArtifacts();
    boolean updateArtifact(Artifact artifact);
    boolean exportArtifact(Artifact artifact);
    List<Artifact> getArtifacts(String type);

    }
