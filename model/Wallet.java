package model;

import model.Artifact;
import java.util.ArrayList;

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
        this.coins = balance;
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
    public String toString(){
        //use iterator to browse threw collection
        //concatenate toString() of artifacts
        //trim last comma
        return"";
    }
}