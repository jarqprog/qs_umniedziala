package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Level;
import model.Mentor;

public class DaoLevel{
    public Level createLevel(String name, int coinsLimit) {
        return null;
    }

    public Level createLevel(int levelId, String name, int coinsLimit) {
        return null;
    }

    public void exportLevel(Level level){

    }

    public Level importLevel(int levelId) {
        Level level = null;
        PreparedStatement preparedStatement = null;
        String query = "SELECT coins_limit FROM levels WHERE id_level = ?;";

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, levelId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.isClosed()) {
                String name = resultSet.getString("name");
                int coinsLimit = resultSet.getInt("coins_limit");
                level = createLevel(levelId, name, coinsLimit);
                resultSet.close();
            }
            preparedStatement.close();

        } catch (SQLException | ClassNotFoundException e) {
            return level;
        }
        return level;
    }

    public ArrayList<Level> getAllLevels() {
        return new ArrayList<Level>();
    }
}
