package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class QuestTest extends ModelTest {

    private Quest quest;

    @Before
    public void instance() {
        quest = new Quest(1, "Quest", 100, "osom", "questowy", "basic");
    }

    @Test
    public void testGetCategory() {
        assertEquals("basic", quest.getCategory());
    }

    @Test
    public void testSetCategory() {
        quest.setCategory("magic");
        assertEquals("magic", quest.getCategory());
    }

    @Test
    public void testToString() {
        assertEquals("Item id: 1, name: Quest, value: 100, description: osom, " +
                "type: questowy, category: basic", quest.toString());
    }
}