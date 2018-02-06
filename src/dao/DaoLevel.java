package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Level;

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
        return null;
    }

    public ArrayList<Level> getAllLevels() {
        return new ArrayList<Level>();
    }
}
