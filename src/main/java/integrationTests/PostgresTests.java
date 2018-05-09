package integrationTests;

import dao.*;
import enums.dbEnums.DbDriver;
import enums.dbEnums.DbFilePath;
import enums.dbEnums.DbUrl;
import manager.database.DatabaseConfiguration;
import manager.database.DatabaseManager;
import manager.database.SqlConfig;
import manager.database.SqlManager;
import model.Admin;

import java.sql.Connection;

public class PostgresTests {

    public static void main(String[] args) {

        DatabaseConfiguration databaseConfiguration = SqlConfig
                .createPosgresConfiguration(DbUrl.POSTGRES_MAIN_URL, DbDriver.POSTGRES, DbFilePath.POSTGRES_MAIN_DATABASE);

        DatabaseManager databaseManager = SqlManager.getSQLManager(databaseConfiguration);

        Connection connection = databaseManager.getConnection();

        System.out.println(connection);


        IDaoFactory daoFactory = createSQLiteDaoFactory(databaseManager);

        IDaoAdmin daoAdmin = daoFactory.create(DaoAdmin.class);

        Admin admin = daoAdmin.createAdmin("Mark", "mark", "mark@cc.com");
//        admin.set
        System.out.println(admin);
        System.out.println(daoAdmin.exportAdmin(admin));

    }

    private static IDaoFactory createSQLiteDaoFactory(DatabaseManager databaseManager) {
        return DaoFactory.getInstance(databaseManager);
    }
}
