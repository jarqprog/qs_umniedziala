package dao;

import model.*;
import java.util.ArrayList;

public interface IDaoQuest{

    public ArrayList<Quest> getQuests();
    public Quest getQuestById(int id);
    public Quest createQuest(String name, int value, String description, String status, String type);
    public void importData(Quest quest);
    public void exportData();

}