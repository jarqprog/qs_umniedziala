package model;

import iterator.MyIterator;

import java.util.ArrayList;

public class Wallet{
    private int allCoins;
    private int availableCoins;
    private ArrayList<Artifact> artifacts;

    public Wallet(){
        this.allCoins = 0;
        this.availableCoins = 0;
        artifacts = new ArrayList<>();
    }

    public Wallet(int allCoins, int availableCoins, ArrayList<Artifact> artifacts){
        this.allCoins = allCoins;
        this.availableCoins = availableCoins;
        this.artifacts = artifacts;
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

    public boolean hasEnoughCoins(int value) {
        return (availableCoins - value) >= 0;
    }

    public void subtractCoins(int coins) {
        availableCoins -= coins;
    }

    public String toString(){
        MyIterator <Artifact> myIterator = new MyIterator<>(this.artifacts);
        String myWallet = "Wallet contains " + this.availableCoins + " coolcoins\n\nArtifacts:\n";
        while(myIterator.hasNext()){
            myWallet += myIterator.next().toString() + "\n";
        }

        return myWallet;
    }
}
