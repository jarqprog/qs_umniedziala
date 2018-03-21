package dao;

import dao.managers.TestSqliteDbSetter;
import org.junit.*;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class DaoTest {

    public abstract void setUp();
    public abstract void tearDown();
    private Connection connection;

    @BeforeClass
    public static void prepareDatabase() {
        DbConnection.setUrl(DbUrl.DATABASE_TEST_URL);
        setupDatabase();

    }

    @AfterClass
    public static void resetDbConnection() {
        DbConnection.setUrl(DbUrl.DATABASE_MAIN_URL);
    }

    @Before
    public void prepareTest() {
        try {
            connection = DbConnection.getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Problem occurred while preparing test in: " + getClass().getSimpleName());
            System.exit(1);
        }
        setUp();  // implement in concrete DaoTest class
    }

    @After
    public void cleanAfterTest() {
        try {
            connection.rollback();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Problem occurred while ending test in: " + getClass().getSimpleName());
            System.exit(1);
        }
        tearDown();  // implement in concrete DaoTest class
    }

    @Rule
    public MethodRule watchman = new TestWatchman() {
        public void starting(FrameworkMethod method) {
            System.out.println("Starting test: " + method.getName());
        }
    };


    private static void setupDatabase() {
        try {
            TestSqliteDbSetter.getInstance(
                    DbConnection.getConnection(),
                    DbFilePath.SQLITE_TEST_DATABASE,  //
                    DbFilePath.DB_SETUP_SCRIPT)
                    .setDatabase();  // set test database file
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Problem occurred while setting test database!");
            System.exit(1);
        }
    }

}
