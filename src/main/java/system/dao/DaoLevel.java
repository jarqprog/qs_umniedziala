package system.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import system.model.Level;

public class DaoLevel extends SqlDao implements IDaoLevel {

    private final String DATABASE_TABLE = "levels";
    private final String ID_LABEL = "id_level";

    DaoLevel(Connection connection) {
        super(connection);
    }

    @Override
    public Level createLevel(String name, int coinsLimit) {
        try {
            int id = getLowestFreeIdFromGivenTable(DATABASE_TABLE, ID_LABEL);
            Level level = new Level(id, name, coinsLimit);
            exportLevel(level);
            return level;
        } catch (SQLException e) {
            e.printStackTrace();
            return new NullLevel();
        }
    }

    @Override
    public Level importLevel(int levelId) {

        String query = "SELECT name, coins_limit FROM levels WHERE id_level = ?;";

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
             preparedStatement.setInt(1, levelId);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {

                if(resultSet.next() ) {
                    String name = resultSet.getString("name");
                    int coinsLimit = resultSet.getInt("coins_limit");
                    return new Level(levelId, name, coinsLimit);
                }
                return new NullLevel();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return new NullLevel();
        }
    }

    @Override
    public List<Level> getAllLevels() {
        List<Level> levels = new ArrayList<>();
        String query = "SELECT id_level FROM levels ORDER BY coins_limit;";

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()){
                int levelId = resultSet.getInt(ID_LABEL);
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
    public List<Level> getMatchingLevels(int allCoins){

        String query = "SELECT * FROM levels WHERE coins_limit <= ?";
        List <Level> levels = new ArrayList<>();

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, allCoins);

            try( ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int levelId = resultSet.getInt(ID_LABEL);
                    levels.add(importLevel(levelId));
                }
            }
        }catch(SQLException e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return levels;

    }

    @Override
    public Level getRightLevel(List<Level> levels, int availableCoins){

        if (levels.size() == 0) {
            return new NullLevel();
        }

        Level level = levels.get(0);

        for(Level elem: levels){
            if(elem.getCoinsLimit() >= level.getCoinsLimit()){
                level = elem;
            }
        }
        return level;
    }

    private boolean exportLevel(Level level) throws SQLException {

        String query = "INSERT INTO levels (id_level, name, coins_limit)" +
                "VALUES (?, ?, ?);";

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {

            preparedStatement.setInt(1, level.getLevelId());
            preparedStatement.setString(2, level.getName());
            preparedStatement.setInt(3, level.getCoinsLimit());
            preparedStatement.executeUpdate();
            return true;
        }
    }
}
