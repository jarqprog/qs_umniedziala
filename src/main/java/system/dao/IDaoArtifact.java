package system.dao;

import system.model.Artifact;

import java.util.List;

public interface IDaoArtifact {
    Artifact createArtifact(String name, int value, String description, String type);
    Artifact importArtifact(int itemId);
    List<Artifact> getAllArtifacts();
    boolean updateArtifact(Artifact artifact);
    List<Artifact> getArtifacts(String type);

    }
