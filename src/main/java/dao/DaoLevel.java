package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import model.Level;
import model.Mentor;

public class DaoLevel extends SqlDao implements IDaoLevel {

    DaoLevel(Connection connection) {
        super(connection);
    }

    @Override
    public Level createLevel(String name, int coinsLimit) {
        return new Level(name, coinsLimit);
    }

    @Override
    public Level createLevel(int levelId, String name, int coinsLimit) {
        return new Level(levelId, name, coinsLimit);
    }

    @Override
    public boolean exportLevel(Level level){

        String name = level.getName();
        int coinsLimit = level.getCoinsLimit();

        String query = "INSERT INTO levels (name, coins_limit)" +
                "VALUES (?, ?);";

        try (
             PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, coinsLimit);
            preparedStatement.executeUpdate();
            return true;

        }catch (SQLException e){
            return false;
        }
    }


    @Override
    public Level importLevel(int levelId) {
        Level level = null;
        String query = "SELECT name, coins_limit FROM levels WHERE id_level = ?;";

        try (
             PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
             preparedStatement.setInt(1, levelId);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {

                if (!resultSet.isClosed()) {
                    String name = resultSet.getString("name");
                    int coinsLimit = resultSet.getInt("coins_limit");
                    level = createLevel(levelId, name, coinsLimit);

                }
            }

        } catch (SQLException e) {
            return level;
        }
        return level;
    }

    @Override
    public List<Level> getAllLevels() {
        List<Level> levels = new ArrayList<>();

        String query = "SELECT id_level FROM levels ORDER BY coins_limit;";

        try (
             PreparedStatement preparedStatement = getConnection().prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()){
                int levelId = resultSet.getInt("id_level");
                Level level = importLevel(levelId);
                levels.add(level);
            }

        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        return levels;
    }

    @Override
    public Level importLevelByCoins(int allCoins){

        List <Level> levels = getMatchingLevels(allCoins);
        Level level = getRightLevel(levels, allCoins);
        return level;
    }

    @Override
    public List <Level> getMatchingLevels(int allCoins){

        Level level = null;

        String query = "SELECT * FROM levels WHERE coins_limit <= ?";
        List <Level> levels = new ArrayList<>();

        try (
             PreparedStatement preparedStatement = getConnection().prepareStatement(query)){
            preparedStatement.setInt(1, allCoins);

            try( ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int limitCoins = resultSet.getInt("coins_limit");
                    level = createLevel(name, limitCoins);
                    levels.add(level);
                }
            }


        }catch(SQLException e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return levels;

    }

    @Override
    public Level getRightLevel(List<Level> levels, int availableCoins){

        Level level = levels.get(0);

        for(Level elem: levels){
            if(elem.getCoinsLimit() >= level.getCoinsLimit()){
                level = elem;
            }
        }

        return level;

    }
}
