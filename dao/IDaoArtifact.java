package dao;

import model.*;
import java.util.ArrayList;

public interface IDaoArtifact{

    public Artifact getArtifactById(int id);
    public void createArtifact(String name, int value, String description, String status);
    public ArrayList <Artifact> importData();
    public void exportData(ArrayList <Artifact> list);

}