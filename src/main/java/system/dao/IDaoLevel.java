package system.dao;

import system.model.Level;

import java.util.List;

public interface IDaoLevel {

    Level createLevel(String name, int coinsLimit);

    Level importLevel(int levelId);

    List<Level> getAllLevels();

    Level importLevelByCoins(int allCoins);

    List <Level> getMatchingLevels(int allCoins);

    Level getRightLevel(List<Level> levels, int availableCoins);
}
