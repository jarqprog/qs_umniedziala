package dao;

import model.Quest;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;


public class DaoQuestTest extends DaoTest{

    private DaoQuest dao;

    @Override
    public void setUp() {
        dao = new DaoQuest();
    }

    public void tearDown() {
        dao = null;
    }

    @Test
    public void testCreateQuestFiveParameters() {
        String name = "O";
        int value = 100;
        String description = "QQ";
        String type = "QQQ";
        String category = "WWW";
        Quest quest = dao.createQuest(name, value, description, type, category);
        assertEquals(name, quest.getName());
        assertEquals(value, quest.getValue());
        assertEquals(description, quest.getDescription());
        assertEquals(type, quest.getType());
        assertEquals(category, quest.getCategory());
    }

    @Test
    public void testCreateQuestUsingSixParameters() {
        int id = 1;
        String name = "O";
        int value = 100;
        String description = "QQ";
        String type = "QQQ";
        String category = "WWW";
        Quest quest = dao.createQuest(id, name, value, description, type, category);
        assertEquals(id, quest.getItemId());
        assertEquals(name, quest.getName());
        assertEquals(value, quest.getValue());
        assertEquals(description, quest.getDescription());
        assertEquals(type, quest.getType());
        assertEquals(category, quest.getCategory());
    }

    @Test
    public void testImportQuest() {
        Quest quest = dao.importQuest(1);
        assertEquals("Exploring a dungeon", quest.getName());
        assertEquals(100, quest.getValue());
        assertEquals("Finishing a Teamwork week", quest.getDescription());
        assertEquals("team", quest.getType());
        assertEquals("basic", quest.getCategory());

    }

    @Test
    public void testImportQuestWhichIsNotInDatabase() {
        assertNull(dao.importQuest(1001));
    }

    @Test
    public void testGetAllQuests() {
        List<Quest> quests = dao.getAllQuests();
        assertNotNull(quests);
        quests.forEach(Assert::assertNotNull);
        assertTrue(quests
                .stream().filter(a -> a.getValue() >= 0)
                .collect(Collectors.toList())
                .size() > 0);
    }

    @Test
    public void testUpdateQuest() {
        assertTrue(dao.updateQuest(createQuestThatAlreadyExistsInDatabase()));
    }

    @Test
    public void testUpdateQuestWithSQLInjectionInParameters() {
        assertTrue(dao.updateQuest(createQuestWithSQLInjectionInParameters()));
    }

    @Test
    public void testExportQuest() {
        assertTrue(dao.exportQuest(createQuestWhichIsNotInDatabase()));
    }

    @Test
    public void testExportQuestWithSQLInjectionInParameters() {
        assertTrue(dao.exportQuest(createQuestWithSQLInjectionInParameters()));
    }

    @Test
    public void testGetTeamQuests() {
        List<Quest> quests = dao.getTeamQuests();
        assertNotNull(quests);
        quests.forEach(Assert::assertNotNull);
        assertTrue(quests.stream().allMatch(a -> a.getType().equals("team")));
    }

    @Test
    public void getIndividualQuests() {
        List<Quest> quests = dao.getIndividualQuests();
        assertNotNull(quests);
        quests.forEach(Assert::assertNotNull);
        assertTrue(quests.stream().allMatch(a -> a.getType().equals("individual")));
    }

    private Quest createQuestWhichIsNotInDatabase() {
        int id = 10007;
        String name = "test test test";
        int value = 50;
        String description = "Test mentoring test test";
        String type = "test test";
        String category = "TEST 01 TEST";
        return new Quest(id, name, value, description, type, category);
    }

    private Quest createQuestThatAlreadyExistsInDatabase() {
        int id = 1;
        String name = "Exploring a dungeon";
        int value = 100;
        String description = "Finishing a Teamwork week";
        String type = "team";
        String category = "basic";
        return new Quest(id, name, value, description, type, category);
    }

    private Quest createQuestWithSQLInjectionInParameters() {
        String name = "105 OR 1=1";
        int value = 100;
        String description = "105 OR 1002=1002";
        String type = "'true'='true'";
        String category = "true OR 112=112";
        return new Quest(name, value, description, type, category);
    }
}
