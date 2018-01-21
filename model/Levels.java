package model;

public class Levels{

    private String name;
    private int coins;
    private int id;
    private static int idCounter;

    public Levels(String name, int coins){
        
        this.name = name;
        this.coins = coins;
        this.id = ++idCounter;
    }
    
    public String getName() { return this.name;}
    public int getCoins() {return this.coins;}
    public int getId() {return this.id;}

    public void setName(String name){
        this.name = name;
    }
    public void setCoins(int coins){
        this.coins = coins;
    }

    public String toString() {

        StringBuilder sb = new StringBuilder();
        
        sb.append(", Name: " + getName());
        sb.append(", Coins: " + getCoins());

        return sb.toString();
    }
}