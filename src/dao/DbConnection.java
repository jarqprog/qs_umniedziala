package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnection {
    private static DbConnection firstInstance = null;
    private Connection dbConnection;

    private DbConnection(String databaseName) {
        try {
            Class.forName("org.sqlite.JDBC");
            dbConnection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);
            dbConnection.setAutoCommit(false);
        }
        catch (ClassNotFoundException | SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
    }

    public static DbConnection getInstance(String databaseName) {
            if (firstInstance == null) {
                firstInstance = new DbConnection(databaseName);
            }
        return firstInstance;
    }
    public Statement createStatement() {
        Statement stmt = null;
        try {
            stmt = dbConnection.createStatement();
        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
        return stmt;
    }

    public void commit() {
        try {
            dbConnection.commit();
        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
    }

    public void close() {
        try {
            dbConnection.close();
        } catch (SQLException ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
    }
}
