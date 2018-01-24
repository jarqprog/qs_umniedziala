package dao;

import model.*;
import java.util.ArrayList;

public class DaoArtifact implements IDaoArtifact{

    private static ArrayList <Artifact> artifacts = new ArrayList<>();

    public void implementTestData() {
        createArtifact("Combat training", 50, "Private mentoring", "individual");
        createArtifact("Sanctuary", 300, "You can spend a day in home office", "individual");
        createArtifact("Time travel", 500, "Extend SI week assignment deadline by one day", "individual");
    }

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