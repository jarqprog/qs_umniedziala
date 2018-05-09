package system.model;

import system.iterator.MyIterator;

import java.util.ArrayList;
import java.util.List;

public class Wallet{

    private final int walletId = 1;
    private int allCoins;
    private int availableCoins;
    private List<Artifact> newArtifacts;
    private List<Artifact> usedArtifacts;

    public Wallet() {
        this.allCoins = 0;
        this.availableCoins = 0;
        newArtifacts = new ArrayList<>();
        usedArtifacts = new ArrayList<>();
    }

    public Wallet(int allCoins, int availableCoins, List<Artifact> newArtifacts, List<Artifact> usedArtifacts){
        this.allCoins = allCoins;
        this.availableCoins = availableCoins;
        this.newArtifacts = newArtifacts;
        this.usedArtifacts = usedArtifacts;
    }

    public void setAllCoins(int allCoins){
        this.allCoins = allCoins;
    }

    public int getAllCoins(){
        return this.allCoins;
    }

    public void setAvailableCoins(int availableCoins){
        this.availableCoins = availableCoins;
    }

    public int getAvailableCoins() {
        return this.availableCoins;
    }

    public void addCoins(int coins) {
        availableCoins += coins;
        allCoins += coins;
    }

    public List<Artifact> getNewArtifacts() { return newArtifacts; }

    public List<Artifact> getUsedArtifacts() { return usedArtifacts; }

    public void addNewArtifact(Artifact artifact) { this.newArtifacts.add(artifact); }

    public void removeNewArtifact(int artifactId) {
        for (Artifact artifact : this.newArtifacts) {
            if (artifact.getItemId() == artifactId) {
                this.newArtifacts.remove(artifact);
                break;
            }

        }
    }

    public boolean hasEnoughCoins(int value) {
        return (availableCoins - value) >= 0;
    }

    public void subtractCoins(int coins) {
        availableCoins -= coins;
    }

    public void addUsedArtifact(Artifact artifact) { this.usedArtifacts.add(artifact); }

    public String toString(){
        String myWallet = "Wallet contains:\n";
        myWallet += this.availableCoins + " available coolcoins\n";
        myWallet += this.allCoins + " earned coolcoins";
        myWallet += "\n\nNew Artifacts:\n" + getStudentArtifacts(newArtifacts);
        myWallet += "\n\nUsed Artifacts:\n" + getStudentArtifacts(usedArtifacts);

        return myWallet;
    }

    private String getStudentArtifacts(List<Artifact> artifacts){
        String allArtifacts = "";
        if(artifacts.size() == 0){
            allArtifacts = "No artifacts";
        }else {
            MyIterator<Artifact> myIterator = new MyIterator<>(artifacts);
            while (myIterator.hasNext()) {
                allArtifacts += myIterator.next().toString() + "\n";
            }
        }

        return allArtifacts;

    }

    public int getWalletId() {
        return walletId;
    }
}
