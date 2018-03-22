package dao;

import model.Quest;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


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
    public void testCreateQuest() {
        Quest quest = dao.createQuest(1, "Q", 100, "QQ", "QQQ", "WWW");
        assertEquals(1, quest.getItemId());
        assertEquals("Q", quest.getName());
        assertEquals(100, quest.getValue());
        assertEquals("QQ", quest.getDescription());
        assertEquals("QQQ", quest.getType());
        assertEquals("WWW", quest.getCategory());
    }

    @Test
    public void testCreateQuest1() {
        Quest quest = dao.createQuest("Q", 100, "QQ", "QQQ", "WWW");
        assertEquals("Q", quest.getName());
        assertEquals(100, quest.getValue());
        assertEquals("QQ", quest.getDescription());
        assertEquals("QQQ", quest.getType());
        assertEquals("WWW", quest.getCategory());
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
    public void testGetAllQuests() {
        List<Quest> quests = dao.getAllQuests();
        int counter = 0;
        for (Quest quest : quests){
            counter++;
        }
        assertEquals(counter, quests.size());
    }

    @Test
    public void testUpdateQuest() {
        Quest quest = dao.importQuest(10);
        assertTrue(dao.updateQuest(quest));
    }

    @Test
    public void testExportQuest() {
        Quest quest = new Quest("Q", 100, "QQ", "QQQ", "WWW");
        assertTrue(dao.exportQuest(quest));
    }

    @Test
    public void testGetTeamQuests() {
        List<Quest> quests = dao.getTeamQuests();
        assertEquals(5 ,quests.size());
    }

    @Test
    public void getIndividualQuests() {
        List<Quest> quests = dao.getIndividualQuests();
        assertEquals(8 ,quests.size());
    }
}