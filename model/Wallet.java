package model;

import model.Artifact;

public class Wallet{
    private int coins;
    private int balance;
    private ArrayList <Artifacts> artifacts;

    public Wallet(){
        this.coins = 0;
        this.balance = 0;
        this.artifacts = new ArrayList<Artifacts>();
    }
    public Wallet(int balance, int coins, ArrayList <Artifacts> artifacts){}
    public setCoins(int coins){}
    public int getCoins(){}
    public setBalance(int balance){}
    public int getBalance(){}
    public setArtifacts(ArrayList <Artifacts> artifacts){}
    public ArrayList <Artifacts> getArtifacts(){}
    public String toString(){}
}