package dao;

import manager.database.DatabaseManager;

import java.sql.Connection;

public class DaoFactory implements IDaoFactory {

    private final DatabaseManager dbManager;
    private Connection connection;

    public static IDaoFactory getInstance(DatabaseManager dbManager) {

        return new DaoFactory(dbManager);
    }

    private DaoFactory(DatabaseManager dbManager) {

        this.dbManager = dbManager;
        connection = dbManager.getConnection();
    }

    public <T extends Dao> T create(Class<T> daoType) {

        if( ! dbManager.isConnectionValid(connection) ) {
            connection = dbManager.getConnection();
        }

        String daoName = daoType.getSimpleName();
        SqlDao dao = null;

        switch(daoName) {
            case("DaoAdmin"):
                dao = new DaoAdmin(connection);
                break;
            case("DaoArtifact"):
                dao = new DaoArtifact(connection);
                break;
            case("DaoClass"):
                dao = new DaoClass(connection, create(DaoStudent.class));
                break;
            case("DaoLevel"):
                dao = new DaoLevel(connection);
                break;
            case("DaoLogin"):
                dao = new DaoLogin(connection, create(DaoAdmin.class),
                        create(DaoMentor.class), create(DaoStudent.class));
                break;
            case("DaoMentor"):
                dao = new DaoMentor(connection);
                break;
            case("DaoQuest"):
                dao = new DaoQuest(connection);
                break;
            case("DaoStudent"):
                dao = new DaoStudent(connection, create(DaoWallet.class));
                break;
            case("DaoTeam"):
                dao = new DaoTeam(connection, create(DaoStudent.class));
                break;
            case("DaoWallet"):
                dao = new DaoWallet(connection, create(DaoArtifact.class));
                break;
        }
        return daoType.cast(dao);
    }
}
