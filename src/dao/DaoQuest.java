package dao;

import model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DaoQuest{
    public Quest createQuest(int itemId, String name, int value, String description, String type, String category) {
        return new Quest(itemId, name, value, description, type, category);
    }

    public ArrayList<Quest> importData() {
        ArrayList<Quest> allQuests = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        String query = "SELECT * FROM quests";

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Quest quest = createQuestFromDBData(rs);
                allQuests.add(quest);
            }
            preparedStatement.close();
            rs.close();
        }catch(SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return allQuests;
    }

    private Quest createQuestFromDBData(ResultSet rs) throws SQLException {
        int itemId = rs.getInt("id_quest");
        String name = rs.getString("name");
        int value = rs.getInt("value");
        String description = rs.getString("description");
        String type = rs.getString("type");
        String category = rs.getString("category");
        Quest quest = createQuest(itemId, name, value, description, type, category);
        return quest;
    }
}