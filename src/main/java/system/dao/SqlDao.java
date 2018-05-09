package system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class SqlDao implements Dao {

    private Connection connection;

    SqlDao(Connection connection) {
        this.connection = connection;
    }

    protected Connection getConnection() {
        return connection;
    }

    protected int getLowestFreeIdFromGivenTable(String databaseTable, String idLabel) throws SQLException {
        // to avoid relying on database autoincrement mechanic
        final int MINIMUM_ID_VALUE = 1;
        String EXCEPTION_INFO = "Couldn't gather lowest free id, exception occurred. ";

        String query = String.format("SELECT %s FROM %s", idLabel, databaseTable);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery() ) {

            List<Integer> idCollection = new ArrayList<>();
            while (resultSet.next()) {
                idCollection.add(resultSet.getInt(idLabel));
            }

            int idCollectionLength = idCollection.size();

            if (idCollectionLength == 0) {
                return MINIMUM_ID_VALUE;
            }

            Collections.sort(idCollection);

            for (int i = MINIMUM_ID_VALUE; i <= idCollectionLength + 1; i++) {
                if (!idCollection.contains(i)) {
                    return i;  // return first not occupied lowest value
                }
            }
            throw new SQLException(EXCEPTION_INFO);
        }
    }
}
