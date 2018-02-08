package model;

import java.util.ArrayList;

public class Team extends Group {
    private int availableCoins;

    public Team(String name) {
        super(name);
        this.availableCoins = 0;

    }

    public Team(int groupId, String name, ArrayList<Student> students, int availableCoins) {
        super(groupId, name, students);
        this.availableCoins = availableCoins;
    }

    public void setAvailableCoins(int availableCoins) {
        this.availableCoins = availableCoins;
    }

    public int getAvailableCoins() {
        return this.availableCoins;
    }

    public int getSize() {
        return students.size();
    }

    public String toString() {
        return super.toString() + "\nTeam coins: " + this.availableCoins;
    }

    public String getBasicInfo() {
        return "Your Team: " + this.name + "\nTeam coins: " + this.availableCoins;
    }

    public void addCoins(int coins) {
        availableCoins += coins;
    }

    public void subtractCoins(Integer coins){ availableCoins -= coins; }
}
