package dao;

import model.*;
import java.util.ArrayList;

public interface IDaoArtifact extends IDao{

    public ArrayList<Artifact> getArtifacts();
    public Artifact getArtifactById(int Id);
    public Artifact createArtifact();

}