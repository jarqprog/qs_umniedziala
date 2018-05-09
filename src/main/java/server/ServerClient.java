package server;

import system.dao.DaoFactory;
import system.dao.IDaoFactory;
import system.enums.dbEnums.DbDriver;
import system.enums.dbEnums.DbFilePath;
import system.enums.dbEnums.DbUrl;
import system.manager.database.DatabaseConfiguration;
import system.manager.database.DatabaseManager;
import system.manager.database.SqlConfig;
import system.manager.database.SqlManager;
import server.factory.IControllerFactory;
import server.factory.IHandlerFactory;
import server.helpers.IResponseManager;
import server.helpers.ResponseManager;
import server.sessions.ISessionManager;
import server.sessions.SessionManager;
import server.webcontrollers.ControllerFactory;

import java.io.IOException;
import java.util.Scanner;

public class ServerClient implements IClient {

    public static IClient create() {
        return new ServerClient();
    }

    // todo - implement log (to register exceptions)

    private ServerClient() {}

    @Override
    public void runClient() {

        try {

            System.out.println("Choose client - 'p' for Postgres client or anything else for SQLite: ");
            Scanner sc = new Scanner(System.in);
            String choice = sc.nextLine().toLowerCase();

            // build application:
            DatabaseManager databaseManager;
            switch (choice) {
                case("p"):
                    databaseManager = createPostgresManager();
                    break;
                default:
                    databaseManager = createSQLiteManager();
            }

            IDaoFactory daoFactory = DaoFactory.getInstance(databaseManager);
            IHandlerFactory handlerFactory = createHandlerFactory(daoFactory);
            int PORT = 8080;
            Server.getInstance(PORT, handlerFactory).run();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DatabaseManager createSQLiteManager() {

        DatabaseConfiguration databaseConfiguration = SqlConfig
                .createPosgresConfiguration(DbUrl.DATABASE_MAIN_URL, DbDriver.SQLITE,
                        DbFilePath.SQLITE_MAIN_DATABASE);

        return SqlManager.getSQLManager(databaseConfiguration);
    }

    private DatabaseManager createPostgresManager() {

        DatabaseConfiguration databaseConfiguration = SqlConfig
                .createPosgresConfiguration(DbUrl.POSTGRES_MAIN_URL, DbDriver.POSTGRES,
                        DbFilePath.POSTGRES_MAIN_DATABASE);

        return SqlManager.getSQLManager(databaseConfiguration);
    }

    private IHandlerFactory createHandlerFactory(IDaoFactory daoFactory) {

        IResponseManager responseManager = ResponseManager.create();
        int sessionExpirationTime = 300000;
        ISessionManager sessionManager = SessionManager.create(sessionExpirationTime);
        IControllerFactory controllerFactory = ControllerFactory.getInstance(daoFactory);

        return HandlerFactory.getInstance(controllerFactory,
                responseManager, sessionManager, daoFactory);
    }
}
