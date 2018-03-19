package model;

import java.util.ArrayList;
import java.util.List;

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

    public void addCoins(int coins) {
        wallet.addCoins(coins);
    }

    public List<Artifact> getAllNewArtifacts(){
        return this.wallet.getNewArtifacts();
    }

    public void addNewArtifact(Artifact artifact){
        this.wallet.addNewArtifact(artifact);
    }

    public void markArtifactAsBougth(Artifact artifact) {
        this.wallet.removeNewArtifact(artifact.getItemId());
        this.wallet.addUsedArtifact(artifact);
    }

    public boolean hasEnoughCoins(int value) {
        return wallet.hasEnoughCoins(value);
    }

    public void subtractCoins(int coins) {
        wallet.subtractCoins(coins);
    }

}
