package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static Connection firstInstance = setConnection();

    private static Connection setConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:resources/dbStruct.db");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        return firstInstance;
    }
}
