package dao;

import model.*;
import java.util.ArrayList;

public interface IDaoArtifact{

    public ArrayList<Artifact> getArtifacts();
    public Artifact getArtifactById(int id);
    public Artifact createArtifact(String name, int value, String description, String status);
    public void importData(Artifact artifact);
    public void exportData();

}