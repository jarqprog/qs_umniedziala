package system.manager.database;

import java.sql.Connection;

public interface DatabaseManager {

    Connection getConnection();
    void closeConnection();
    boolean isConnectionValid(Connection connection);
}
