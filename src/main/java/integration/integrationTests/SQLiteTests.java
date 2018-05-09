package integration.integrationTests;

import dao.DaoAdmin;
import dao.DaoFactory;
import dao.IDaoAdmin;
import dao.IDaoFactory;
import enums.dbEnums.DbDriver;
import enums.dbEnums.DbFilePath;
import enums.dbEnums.DbUrl;
import manager.database.DatabaseConfiguration;
import manager.database.DatabaseManager;
import manager.database.SqlConfig;
import manager.database.SqlManager;

import java.sql.Connection;

public class SQLiteTests {

    public static void main(String[] args) {

        DatabaseConfiguration databaseConfiguration = SqlConfig
                .createSQLiteConfiguration(DbUrl.DATABASE_MAIN_URL, DbDriver.SQLITE, DbFilePath.SQLITE_MAIN_DATABASE);

        DatabaseManager databaseManager = SqlManager.getSQLManager(databaseConfiguration);

        Connection connection = databaseManager.getConnection();

        System.out.println(connection);


        IDaoFactory daoFactory = createSQLiteDaoFactory(databaseManager);

        IDaoAdmin daoAdmin = daoFactory.create(DaoAdmin.class);

//        Admin admin = daoAdmin.createAdmin("Mark", "mark", "mark@cc.com");
////        admin.set
//        System.out.println(admin);
//        System.out.println(daoAdmin.exportAdmin(admin));
        System.out.println(daoAdmin.importAdmin(200));

    }

    private static IDaoFactory createSQLiteDaoFactory(DatabaseManager databaseManager) {
        return DaoFactory.getInstance(databaseManager);
    }
}
