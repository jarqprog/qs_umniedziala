package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public static class DbConnection {
    private static Connection firstInstance = setConnection();

    private Connection setConnection() throws ClassNotFoundException, SQLException{
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:resources/dbStruct.db");
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        return firstInstance;
    }
}
