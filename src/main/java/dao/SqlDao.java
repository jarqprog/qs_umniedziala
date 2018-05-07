package dao;

import java.sql.Connection;

public abstract class SqlDao implements Dao {

    private Connection connection;

    public SqlDao(Connection connection) {
        this.connection = connection;
    }

    protected Connection getConnection() {
        return connection;
    }
}
