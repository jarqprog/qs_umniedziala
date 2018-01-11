package dao;

import model.*;
import java.util.ArrayList;

public class DaoQuest implements IDaoQuest{

    private static ArrayList <Quest> quests = new ArrayList<>();

    public void createQuest(String name, int value, String description, String status, String type){
        quests.add(new Quest(name, value, description, status, type));
    }

    public Quest getQuestById(int id){
        for(Quest quest: quests){
            if(quest.getItemId() == id){
                return quest;
            }
        }
        return null;
    }
    
    public void exportData(ArrayList <Quest> updatedQuests){
        quests = updatedQuests;
    }

    public ArrayList <Quest> importData(){
        return quests;
    }
    
}