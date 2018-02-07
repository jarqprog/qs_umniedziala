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

    public Quest importQuest(int itemId) {
        Quest quest = null;
        PreparedStatement preparedStatement = null;
        String query = "SELECT * FROM quests WHERE id_quest = ?";
        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, itemId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.isClosed()) {
                String name = resultSet.getString("name");
                int value = resultSet.getInt("value");
                String description = resultSet.getString("description");
                String type = resultSet.getString("type");
                String category = resultSet.getString("category");

                quest = createQuest(itemId, name, value, description, type, category);
                resultSet.close();
            }

            preparedStatement.close();
        }catch(SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return quest;
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

    public void updateQuest(Quest quest) {
        int itemId = quest.getItemId();
        String name = quest.getName();
        int value = quest.getValue();
        String description = quest.getDescription();
        String type = quest.getType();
        String category = quest.getCategory();

        PreparedStatement preparedStatement = null;

        String query = createQueryForUpdateQuest();

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, value);
            preparedStatement.setString(3, description);
            preparedStatement.setString(4, type);
            preparedStatement.setString(5, category);
            preparedStatement.setInt(6, itemId);

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Quest update failed");
        }
    }

    private String createQueryForUpdateQuest() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE quests SET ");
        sb.append("name = ?, value = ?, description = ?, type = ?, category =? ");
        sb.append("WHERE id_quest = ?");
        return sb.toString();
    }
}
