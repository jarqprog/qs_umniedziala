package model;

import java.util.ArrayList;

public class Team extends Group{
    private int coins;

    public Team(String name){
        super(name);
        this.coins = 0;

    }
    public Team(String name, ArrayList<Student> students, int groupId, int coins){
        super(name, students, groupId);
        this.coins = coins;
    }
    public void setCoins(int coins){
        this.coins = coins;
    }
    public int getCoins(){
        return this.coins;
    }

    public String toString(){
        return super.toString() + "\nTeam coins: " + this.coins;
    }
}