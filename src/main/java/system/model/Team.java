package model;

import java.util.List;

public class Team extends Group {

    private int availableCoins;

    public Team(int groupId, String name, List<Student> students, int availableCoins) {
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
        return getStudents().size();
    }

    public String toString() {
        return super.toString() + "\nTeam coins: " + this.availableCoins;
    }

    public String getBasicTeamInfo() {
        return "Your Team: " + this.getName() + "\nTeam coins: " + this.availableCoins;
    }

    public void addCoins(int coins) {
        availableCoins += coins;
    }

    public void subtractCoins(Integer coins){ this.availableCoins -= coins; }

    public boolean hasEnoughCoins(Integer coins){ return (this.availableCoins - coins) >= 0; }
}
