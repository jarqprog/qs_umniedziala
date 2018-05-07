package postgresTest;

import enums.dbEnums.DbDriver;
import enums.dbEnums.DbFilePath;
import enums.dbEnums.DbUrl;
import manager.database.DatabaseConfiguration;
import manager.database.DatabaseManager;
import manager.database.SqlConfig;
import manager.database.SqlManager;

import java.sql.Connection;

public class PostgresTests {

    public static void main(String[] args) {

        DatabaseConfiguration databaseConfiguration = SqlConfig
                .createPosgresConfiguration(DbUrl.POSTGRES_MAIN_URL, DbDriver.POSTGRES, DbFilePath.POSTGRES_MAIN_DATABASE);

        DatabaseManager databaseManager = SqlManager.getSQLiteManager(databaseConfiguration);

        Connection connection = databaseManager.getConnection();

        System.out.println(connection);

    }
}
