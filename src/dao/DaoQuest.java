package dao;

import model.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class DaoQuest{

    public Quest createQuest(String name, int value, String description, String type, String category){
        return new Quest(name, value, description, type, category);
    }

    public Quest createQuest(int itemId, String name, int value, String description, String type, String category){
        return new Quest(itemId,name, value, description, type, category);
    }

    public void exportQuest(Quest quest){

        String name = quest.getName();
        int value = quest.getValue();
        String description = quest.getDescription();
        String type = quest.getType();
        String category = quest.getCategory();

        PreparedStatement preparedStatement = null;
        String query = "INSERT into quests (name, value, description, type, category)" +
                "values (?, ?, ?, ?, ?);";

        try{
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, value);
            preparedStatement.setString(3, description);
            preparedStatement.setString(4, type);
            preparedStatement.setString(5, category);

            preparedStatement.executeUpdate();
            preparedStatement.close();

        }catch (SQLException | ClassNotFoundException e){
            System.out.println("Quest insertion failed");
        }

    }
}