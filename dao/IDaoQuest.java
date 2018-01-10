package dao;

import model.*;
import java.util.ArrayList;

public interface IDaoQuest{

    public ArrayList<Quest> getQuest();
    public Quest getQuestById();
    public Quest createQuest();
    public void importData(Quest quest);
    public void exportData();

}