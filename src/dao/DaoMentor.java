package dao;

import model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoMentor extends Dao {

    public Mentor createMentor(String name, String password, String email) {
        return new Mentor(name, password, email);
    }

    public Mentor createMentor(int userId, String name, String password, String email) {
        return new Mentor(userId, name, password, email);
    }

    public Mentor importInstance(int mentorId) {
        Mentor mentor = null;
        PreparedStatement preparedStatement = null;
        String query = "SELECT * FROM users WHERE id_user = ?;";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, mentorId);
            ResultSet resultSet = preparedStatement.executeQuery(query);
            int userId = resultSet.getInt("id_user");
            String name = resultSet.getString("name");
            String password = resultSet.getString("password");
            String email = resultSet.getString("email");
            resultSet.close();
            preparedStatement.close();

            mentor = createMentor(userId, name, password, email);

        } catch (SQLException e) {
            return mentor;
        }
        return mentor;
    }

}
