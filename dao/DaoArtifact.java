package dao;

import model.*;
import java.util.ArrayList;

public class DaoArtifact implements IDaoArtifact{

    private static ArrayList <Artifact> artifacts;

    public ArrayList <Artifact> getArtifacts() { return artifacts; }

    public Artifact createArtifact(String name, int value, String description, String status, String type){
        Artifact artifact = new Artifact(name, value, description, status);
        return artifact;
    }

    public Artifact getArtifactById(int id){
        for(Artifact artifact: artifacts){
            if(artifact.getItemId() == id){
                return artifact;
            }
        }
        return null;
    }

    public void exportData(){} 

    public void importData(Artifact artifact){
        artifacts.add(artifact);
    }
        
}