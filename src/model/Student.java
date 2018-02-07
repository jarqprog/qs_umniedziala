package model;

import java.util.ArrayList;

public class Student extends User{
    private Wallet wallet;

    public Student(int userId, String name, String password, String email) {

        super(userId, name, password, email);
        this.wallet = new Wallet();
    }

    public Student(String name, String password, String email) {

        super(name, password, email);
        this.wallet = new Wallet();
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public ArrayList<Artifact> getAllNewArtifacts(){
        return this.wallet.getNewArtifacts();
    }

    public void addNewArtifact(Artifact artifact){
        this.wallet.addNewArtifact(artifact);
    }

    public void markArtifactAsBougth(Artifact artifact){
        this.wallet.removeNewArtifact(artifact.getItemId());
        this.wallet.addUsedArtifact(artifact);

    }

}
