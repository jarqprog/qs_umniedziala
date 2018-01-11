package dao;

import model.*;
import java.util.ArrayList;

public class DaoArtifact implements IDaoArtifact{

    private static ArrayList <Artifact> artifacts;


    public void createArtifact(String name, int value, String description, String status){
        artifacts.add(new Artifact(name, value, description, status));
    }

    public Artifact getArtifactById(int id){
        for(Artifact artifact: artifacts){
            if(artifact.getItemId() == id){
                return artifact;
            }
        }
        return null;
    }

    public void exportData(ArrayList <Artifact> updatedArtifacts){
        artifacts = updatedArtifacts;
    } 

    public ArrayList <Artifact> importData(){
        return artifacts;
    }
        
}