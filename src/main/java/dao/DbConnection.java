package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static String url = DbUrl.DATABASE_MAIN_URL.getUrl();

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return connection;

    }

    public static void setUrl(DbUrl url) {
        DbConnection.url = url.getUrl();
    }

    public static String getUrl() {
        return url;
    }
}