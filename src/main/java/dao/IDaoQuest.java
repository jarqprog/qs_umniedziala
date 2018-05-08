package dao;

import model.Quest;

import java.util.List;

public interface IDaoQuest {

    Quest createQuest(String name, int value, String description, String type, String category);

    Quest importQuest(int itemId);

    List<Quest> getAllQuests();

    boolean updateQuest(Quest quest);

    boolean exportQuest(Quest quest);

    List<Quest> getTeamQuests();

    List<Quest> getIndividualQuests();
}
