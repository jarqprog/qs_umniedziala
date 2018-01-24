package dao;

import model.Student;
import model.User;

import java.sql.*;


public class DaoUser {
    private Connection connection = null;
//    private ViewDao view = new ViewDao();

    public boolean setConnection(String databaseName) {
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);
            return true;

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }
    public void closeConnection(){
        try{
            connection.close();

        }catch(SQLException e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public User executeQuery(String query){
        User user = null;
        Statement statement = null;
        try{
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            int id = resultSet.getInt("id_user");
            String name = resultSet.getString("name");
            String password = resultSet.getString("password");
            String email = resultSet.getString("email");
            System.out.println("n:" + name + "|email:" + email);

            user = new Student(id, name, password, email);

            resultSet.close();
            statement.close();

        }catch(SQLException e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return user;
    }
}
