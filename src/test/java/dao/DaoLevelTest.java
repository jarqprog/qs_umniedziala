package dao;

import model.Level;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class DaoLevelTest extends DaoTest {

    private IDaoLevel dao;

    @Override
    public void setUp() {
        dao = new DaoLevel();
    }

    @Override
    public void tearDown() {
        dao = null;
    }

    @Test
    public void constructorTest() {
        assertNotNull(dao);
    }

    @Test
    public void createLevel() {
        String name = "LevelName";
        int coinsLimit = 100;
        Level level = new Level(name, coinsLimit);
        assertEquals(Level.class, level.getClass());
        assertEquals("Level: LevelName, Coins limit: 100", level.toString());
    }

    @Test
    public void createLevelWithId() {
        String name = "LevelName";
        int coinsLimit = 100;
        int id = 1;
        Level level = new Level(id, name, coinsLimit);
        assertEquals(Level.class, level.getClass());
        assertEquals(id, level.getLevelId());
        assertEquals("Level: LevelName, Coins limit: 100", level.toString());
    }

    @Test
    public void exportNewLevel() {
        assertTrue(dao.exportLevel(new Level("newLevel", 456)));
    }

    @Test
    public void exportExistingLevel() {
        assertFalse(dao.exportLevel(new Level("Private", 100)));
    }

    @Test
    public void importLevel() {
        Level level = dao.importLevel(2);
        assertEquals(Level.class, level.getClass());
        assertEquals("Level: Private, Coins limit: 100", level.toString());
    }

    @Test
    public void getAllLevels() {
        List<Level> levels = dao.getAllLevels();
        assertNotNull(levels);
        levels.forEach(Assert::assertNotNull);
        assertTrue(levels
                .stream().filter(a -> a.getCoinsLimit() >= 0)
                .collect(Collectors.toList())
                .size() > 0);
    }

    @Test
    public void importLevelByCoins() {
        Level level = dao.importLevelByCoins(700);
        assertEquals("Soldier", level.getName());
    }

    @Test
    public void importLevelByCoins2() {
        Level level = dao.importLevelByCoins(0);
        assertEquals("Apprentice", level.getName());
    }

    @Test
    public void importLevelByCoins3() {
        Level level = dao.importLevelByCoins(234569999);
        assertEquals("Master", level.getName());
    }

    @Test
    public void getMatchingLevels() {
        List<Level> levels = dao.getMatchingLevels(700);
        assertTrue(levels.stream().allMatch(a -> a.getCoinsLimit() <= 700));
    }

    @Test
    public void getRightLevel() {
        Level level = dao.getRightLevel(dao.getMatchingLevels(300),450 );
        assertEquals("Private", level.getName());
    }

}