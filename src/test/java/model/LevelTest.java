package model;

import org.junit.*;

import static org.junit.Assert.*;

public class LevelTest extends ModelTest {

    private Level level;

    @Before
    public void instance() {
        level = new Level(1, "Level", 100);
    }

    @Test
    public void testGetName() {
        assertEquals("Level", level.getName());
    }

    @Test
    public void testGetCoinsLimit() {
        assertEquals(100, level.getCoinsLimit());
    }

    @Test
    public void testGetLevelId() {
        assertEquals(1, level.getLevelId());
    }

    @Test
    public void testSetName() {
        level.setName("L");
        assertEquals("L", level.getName());
    }

    @Test
    public void testSetCoinsLimit() {
        level.setCoinsLimit(1000);
        assertEquals(1000, level.getCoinsLimit());
    }

    @Test
    public void testToString() {
        assertEquals("Level: Level, Coins limit: 100", level.toString());
    }
}