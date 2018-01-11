package dao;

import model.*;
import java.util.ArrayList;

public class DaoQuest implements IDaoQuest{

    private static ArrayList <Quest> quests;

    public ArrayList <Quest> getQuests() { return quests; }

    public Quest createQuest(String name, int value, String description, String status, String type){
        Quest quest = new Quest(name, value, description, status, type);
        return quest;
    }

    public Quest getQuestById(int id){
        for(Quest quest: quests){
            if(quest.getItemId() == id){
                return quest;
            }
        }
        return null;
    }
    
    public void exportData(){}

    public void importData(Quest quest){
        quests.add(quest);
    }
    
}