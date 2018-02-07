package model;

import iterator.MyIterator;

import java.util.ArrayList;

public class Wallet{
    private int allCoins;
    private int availableCoins;
    private ArrayList<Artifact> newArtifacts;
    private ArrayList<Artifact> usedArtifacts;

    public Wallet(){
        this.allCoins = 0;
        this.availableCoins = 0;
        newArtifacts = new ArrayList<>();
        usedArtifacts = new ArrayList<>();
    }

    public Wallet(int allCoins, int availableCoins, ArrayList<Artifact> newArtifacts,  ArrayList<Artifact> usedArtifacts){
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

    public int getAvailableCoins() { return this.availableCoins; }

    public ArrayList<Artifact> getNewArtifacts() { return newArtifacts; }

    public ArrayList<Artifact> getUsedArtifacts() { return usedArtifacts; }

    public String toString(){
        String myWallet = "Wallet contains " + this.availableCoins + " coolcoins\n\nArtifacts:\n";

        return myWallet;
    }

    private String getStudentArtifacts(ArrayList<Artifact> artifacts){
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
}