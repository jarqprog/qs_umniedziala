package dao;

import model.Artifact;

import java.util.ArrayList;

public interface IDaoArtifact {
    Artifact createArtifact(String name, int value, String description, String type);
    Artifact createArtifact(int itemId, String name, int value, String description, String type);
    Artifact importArtifact(int itemId);
    ArrayList<Artifact> getAllArtifacts();
    boolean updateArtifact(Artifact artifact);
    boolean exportArtifact(Artifact artifact);
    ArrayList<Artifact> getArtifacts(String type);

    }
