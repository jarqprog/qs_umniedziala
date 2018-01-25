package dao;

import model.*;
import java.util.ArrayList;

public interface IDaoQuest{

    public Quest getQuestById(int id);
    public void createQuest(String name, int value, String description, String status, String type);
    public ArrayList <Quest> importData();
    public void exportData(ArrayList <Quest> updatedQuests);

}