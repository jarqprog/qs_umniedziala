package dao;

import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DaoQuest implements IDaoQuest {

    @Override
    public Quest createQuest(String name, int value, String description, String type, String category) {
        return new Quest(name, value, description, type, category);
    }

    @Override
    public Quest createQuest(int itemId, String name, int value, String description, String type, String category) {
        return new Quest(itemId, name, value, description, type, category);
    }

    @Override
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
    @Override
    public ArrayList<Quest> getAllQuests() {
        ArrayList<Quest> quests = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        String query = "SELECT id_quest FROM quests";

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int questId = resultSet.getInt("id_quest");
                Quest quest = importQuest(questId);
                quests.add(quest);
            }
            preparedStatement.close();
            resultSet.close();
        }catch(SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return quests;
    }

    @Override
    public boolean updateQuest(Quest quest) {
        int itemId = quest.getItemId();
        String name = quest.getName();
        int value = quest.getValue();
        String description = quest.getDescription();
        String type = quest.getType();
        String category = quest.getCategory();

        PreparedStatement preparedStatement = null;

        String query = "UPDATE quests SET " +
        "name = ?, value = ?, description = ?, type = ?, category =? " +
        "WHERE id_quest = ?";

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
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
    }

    @Override
    public boolean exportQuest(Quest quest) {

        String query = "INSERT INTO quests VALUES (?, ?, ?, ?, ?, ?);";

        try{
            Connection connection = DbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(2, quest.getName());
            preparedStatement.setInt(3, quest.getValue());
            preparedStatement.setString(4, quest.getDescription());
            preparedStatement.setString(5, quest.getType());
            preparedStatement.setString(6, quest.getCategory());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;

        }catch (SQLException | ClassNotFoundException e){
            return false;
        }
    }

    @Override
    public ArrayList<Quest> getTeamQuests() {
        ArrayList<Quest> quests = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        String query = "SELECT id_quest FROM quests WHERE type = ?;";

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, "team");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int questId = resultSet.getInt("id_quest");
                Quest quest = importQuest(questId);
                quests.add(quest);
            }
            preparedStatement.close();
            resultSet.close();
        }catch(SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return quests;
    }

    @Override
    public ArrayList<Quest> getIndividualQuests() {
        ArrayList<Quest> quests = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        String query = "SELECT id_quest FROM quests WHERE type = ?;";

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, "individual");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int questId = resultSet.getInt("id_quest");
                Quest quest = importQuest(questId);
                quests.add(quest);
            }
            preparedStatement.close();
            resultSet.close();
        }catch(SQLException | ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return quests;
    }

}

