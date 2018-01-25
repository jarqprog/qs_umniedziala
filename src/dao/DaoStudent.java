package dao;

import java.util.ArrayList;
import model.*;

public class DaoStudent implements IDaoStudent{

    public Student createStudent(int userId, String name, String password, String email){
        return new Student(userId, name, password, email);
    }


    private class DaoWallet{

        private Wallet wallet = new Wallet();

        public Wallet getWallet(){
            return wallet;
        }

        public Wallet implementTestData(){
            ArrayList <Artifact> artifacts = new ArrayList<>();
            DaoArtifact daoArtifact = new DaoArtifact();

            artifacts.add(daoArtifact.getArtifactById(1));
            artifacts.add(daoArtifact.getArtifactById(2));
            
            return new Wallet(56, 120, artifacts);
        }
        
    }

}
