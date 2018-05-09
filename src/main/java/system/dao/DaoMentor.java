package system.dao;

import system.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class DaoMentor extends DaoUser implements IDaoMentor {

    private final String ROLE_NAME = "mentor";

    DaoMentor(Connection connection) {
        super(connection);
    }

    @Override
    public Mentor createMentor(String name, String password, String email) {

        try {
            int id = getLowestFreeIdFromGivenTable(getDatabaseTable(), getIdLabel());
            Mentor mentor = new Mentor(id, name, password, email);
            exportUser(mentor, ROLE_NAME);
            return mentor;
        } catch (SQLException e) {
            e.printStackTrace();
            return new NullMentor();
        }
    }

    @Override
    public Mentor importMentor(int mentorId) {

        int roleId = getRoleID(ROLE_NAME);
        String query = "SELECT * FROM users WHERE id_user = ? AND id_role = ?;";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {

            preparedStatement.setInt(1, mentorId);
            preparedStatement.setInt(2, roleId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if ( resultSet.next() ) {
                    int userId = resultSet.getInt(getIdLabel());
                    String name = resultSet.getString("name");
                    String password = resultSet.getString("password");
                    String email = resultSet.getString("email");

                    return new Mentor(userId, name, password, email);
                }
                return new NullMentor();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return new NullMentor();
        }
    }

    @Override
    public boolean updateMentor(Mentor mentor){
        return updateUser(mentor, ROLE_NAME);
    }

    @Override
    public Integer getMentorClassId(Mentor mentor){

        String query = "SELECT id_codecool_class FROM mentors_in_classes WHERE id_mentor = ?;";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {

            preparedStatement.setInt(1, mentor.getUserId());
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                return resultSet.getInt("id_codecool_class");
            }
            return -1;

        }catch (SQLException e){
            System.out.println("Class not found");
            return -1;
        }
    }

    @Override
    public List<Mentor> getAllMentors(){

        List <Mentor> mentorList = new ArrayList <>();
        int roleId = getRoleID(ROLE_NAME);

        String query = "SELECT * FROM users WHERE id_role = ?;";

        try (
             PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
             preparedStatement.setInt(1, roleId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int userId = resultSet.getInt(getIdLabel());
                    String name = resultSet.getString("name");
                    String password = resultSet.getString("password");
                    String email = resultSet.getString("email");

                    mentorList.add(new Mentor(userId, name, password, email));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No mentors");
        }
        return mentorList;
    }
}
