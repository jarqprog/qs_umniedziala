package dao;

import model.*;
import java.util.ArrayList;

public interface IDaoQuest extends IDao{

    public ArrayList<Quest> getQuest();
    public Quest getQuestById();
    public Quest createQuest();

}