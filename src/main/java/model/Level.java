package model;

public class Level{

    private int levelId;
    private String name;
    private int coinsLimit;

    public Level(String name, int coinsLimit){
        
        this.name = name;
        this.coinsLimit = coinsLimit;
    }

    public Level(int levelId, String name, int coinsLimit){

        this.name = name;
        this.coinsLimit = coinsLimit;
        this.levelId = levelId;
    }
    
    public String getName() { return this.name;}
    public int getCoinsLimit() {return this.coinsLimit;}
    public int getLevelId() {return this.levelId;}

    public void setName(String name){
        this.name = name;
    }
    public void setCoinsLimit(int coinsLimit){
        this.coinsLimit = coinsLimit;
    }

    public String toString() {

        StringBuilder sb = new StringBuilder();
        
        sb.append("Level: " + getName());
        sb.append(", Coins limit: " + getCoinsLimit());

        return sb.toString();
    }
}
