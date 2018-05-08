package server;

import dao.DaoFactory;
import dao.IDaoFactory;
import enums.dbEnums.DbDriver;
import enums.dbEnums.DbFilePath;
import enums.dbEnums.DbUrl;
import manager.database.DatabaseConfiguration;
import manager.database.DatabaseManager;
import manager.database.SqlConfig;
import manager.database.SqlManager;

import java.io.IOException;

public class ServerClient implements IClient {

    public static IClient create() {
        return new ServerClient();
    }

    // todo - implement log (to register exceptions)

    private ServerClient() {}

    @Override
    public void runClient() {

        try {
            Server.getInstance(8080, createSQLiteDaoFactory(createSQLiteDatabaseManager())).run();
            System.out.println("http://localhost:8080/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private DatabaseManager createSQLiteDatabaseManager() {

        DatabaseConfiguration databaseConfiguration = SqlConfig
                .createPosgresConfiguration(DbUrl.DATABASE_MAIN_URL, DbDriver.SQLITE,
                        DbFilePath.SQLITE_MAIN_DATABASE);

        return SqlManager.getSQLManager(databaseConfiguration);
    }

    private IDaoFactory createSQLiteDaoFactory(DatabaseManager databaseManager) {
        return DaoFactory.getInstance(databaseManager);
    }
}
