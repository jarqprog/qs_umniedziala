package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Level;
import model.Mentor;

public class DaoLevel{
    public Level createLevel(String name, int coinsLimit) {
        return new Level(name, coinsLimit);
    }

    public Level createLevel(int levelId, String name, int coinsLimit) {
        return new Level(levelId, name, coinsLimit);
    }

    public void exportLevel(Level level){

        String name = level.getName();
        int coinsLimit = level.getCoinsLimit();

        PreparedStatement preparedStatement = null;
        String query = "INSERT into levels (name, coins_limit)" +
                "values (?, ?);";

        try{
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, coinsLimit);
            preparedStatement.executeUpdate();
            preparedStatement.close();

        }catch (SQLException | ClassNotFoundException e){
            System.out.println("Level insertion failed");
        }
    }


    public Level importLevel(int levelId) {
        Level level = null;
        PreparedStatement preparedStatement = null;
        String query = "SELECT name, coins_limit FROM levels WHERE id_level = ?;";

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
        ArrayList<Level> levels = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        String query = "SELECT id_level FROM levels ORDER BY coins_limit;";

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int levelId = resultSet.getInt("id_level");
                Level level = importLevel(levelId);
                levels.add(level);
            }

            resultSet.close();
            preparedStatement.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        return levels;
    }
}
