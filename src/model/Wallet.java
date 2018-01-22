package model;

import model.Artifact;
import java.util.ArrayList;
import iterator.MyIterator;

public class Wallet{
    private int coins;
    private int balance;
    private ArrayList <Artifact> artifacts;

    public Wallet(){
        this.coins = 0;
        this.balance = 0;
        this.artifacts = new ArrayList<Artifact>();
    }

    public Wallet(int balance, int coins, ArrayList <Artifact> artifacts){
        this.coins = coins;
        this.balance = balance;
        this.artifacts = artifacts;
    }

    public void setCoins(int coins){
        this.coins = coins;
    }

    public int getCoins(){
        return this.coins;
    }
    public void setBalance(int balance){
        this.balance = balance;
    }

    public int getBalance(){
        return this.balance;
    }

    public void setArtifacts(ArrayList <Artifact> artifacts){
        this.artifacts = artifacts;
    }

    public ArrayList <Artifact> getArtifacts(){
        return this.artifacts;
    }

    public void addArtifact(Artifact artifact) {
        artifacts.add(artifact);
    }

    public String toString(){
        MyIterator <Artifact> myIterator = new MyIterator<>(this.artifacts);
        String myWallet = "Wallet contains " + this.coins + " coolcoins\n\nArtifacts:\n";
        while(myIterator.hasNext()){
            myWallet += myIterator.next().toString() + "\n";
        }

        return myWallet;
    }
}