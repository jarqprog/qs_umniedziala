package dao;

import model.Quest;

import java.util.ArrayList;

public interface IDaoQuest {
    Quest createQuest(String name, int value, String description, String type, String category);

    Quest createQuest(int itemId, String name, int value, String description, String type, String category);

    Quest importQuest(int itemId);

    ArrayList<Quest> getAllQuests();

    boolean updateQuest(Quest quest);

    boolean exportQuest(Quest quest);

    ArrayList<Quest> getTeamQuests();

    ArrayList<Quest> getIndividualQuests();
}
