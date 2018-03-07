package dao;

import model.Level;

import java.util.ArrayList;

public interface IDaoLevel {
    Level createLevel(String name, int coinsLimit);

    Level createLevel(int levelId, String name, int coinsLimit);

    boolean exportLevel(Level level);

    Level importLevel(int levelId);

    ArrayList<Level> getAllLevels();

    Level importLevelByCoins(int allCoins);

    ArrayList <Level> getMatchingLevels(int allCoins);

    Level getRightLevel(ArrayList<Level> levels, int availableCoins);
}
