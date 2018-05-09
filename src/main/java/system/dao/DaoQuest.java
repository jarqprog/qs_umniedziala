package dao;

import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoQuest extends SqlDao implements IDaoQuest {


    private final String DATABASE_TABLE = "quests";
    private final String ID_LABEL = "id_quest";

    DaoQuest(Connection connection) {
        super(connection);
    }

    @Override
    public Quest createQuest(String name, int value, String description, String type, String category) {
        try {
            int id = getLowestFreeIdFromGivenTable(DATABASE_TABLE, ID_LABEL);
            Quest quest = new Quest(id, name, value, description, type, category);
            exportQuest(quest);
            return quest;
        } catch (SQLException e) {
            e.printStackTrace();
            return new NullQuest();
        }
    }

    @Override
    public Quest importQuest(int itemId) {

        String query = "SELECT * FROM quests WHERE id_quest = ?";
        try (
             PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
             preparedStatement.setInt(1, itemId);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {

                if ( resultSet.next() ) {
                    String name = resultSet.getString("name");
                    int value = resultSet.getInt("value");
                    String description = resultSet.getString("description");
                    String type = resultSet.getString("type");
                    String category = resultSet.getString("category");

                    return new Quest(itemId, name, value, description, type, category);
                }
                return new NullQuest();
            }

         }catch(SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return new NullQuest();
        }
    }

    @Override
    public List<Quest> getAllQuests() {
        List<Quest> quests = new ArrayList<>();

        String query = "SELECT id_quest FROM quests";

        try (
             PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
             try(ResultSet resultSet = preparedStatement.executeQuery()) {

                 while (resultSet.next()) {
                     int questId = resultSet.getInt(ID_LABEL);
                     Quest quest = importQuest(questId);
                     quests.add(quest);
                 }
             }

        }catch(SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return quests;
    }

    @Override
    public boolean updateQuest(Quest quest) {

        String query = "UPDATE quests SET " +
        "name = ?, value = ?, description = ?, type = ?, category =? " +
        "WHERE id_quest = ?";

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {

            preparedStatement.setString(1, quest.getName());
            preparedStatement.setInt(2, quest.getValue());
            preparedStatement.setString(3, quest.getDescription());
            preparedStatement.setString(4, quest.getType());
            preparedStatement.setString(5, quest.getCategory());
            preparedStatement.setInt(6, quest.getItemId());

            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean exportQuest(Quest quest) throws SQLException {

        String query = "INSERT INTO quests VALUES (?, ?, ?, ?, ?, ?);";

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {

            preparedStatement.setInt(1, quest.getItemId());
            preparedStatement.setString(2, quest.getName());
            preparedStatement.setInt(3, quest.getValue());
            preparedStatement.setString(4, quest.getDescription());
            preparedStatement.setString(5, quest.getType());
            preparedStatement.setString(6, quest.getCategory());

            preparedStatement.executeUpdate();
             return true;
        }
    }

    @Override
    public List<Quest> getTeamQuests() {
        List<Quest> quests = new ArrayList<>();

        String query = "SELECT id_quest FROM quests WHERE type = ?;";

        try (
             PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, "team");
            try(ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int questId = resultSet.getInt(ID_LABEL);
                    quests.add(importQuest(questId));
                }
            }

        }catch(SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return quests;
    }

    @Override
    public List<Quest> getIndividualQuests() {
        List<Quest> quests = new ArrayList<>();

        String query = "SELECT id_quest FROM quests WHERE type = ?;";

        try (
             PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, "individual");
            try(ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int questId = resultSet.getInt(ID_LABEL);
                    quests.add(importQuest(questId));
                }
            }

        }catch(SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return quests;
    }
}

