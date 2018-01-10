package dao;

import model.*;
import java.util.ArrayList;

public interface IDaoArtifact{

    public ArrayList<Artifact> getArtifacts();
    public Artifact getArtifactById(int Id);
    public Artifact createArtifact();
    public void importData(Atrifact artifact);
    public void exportData();

}