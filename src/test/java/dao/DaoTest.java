package dao;

import dao.managers.TestSqliteDbSetter;
import org.junit.*;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;

import java.io.IOException;

public abstract class DaoTest {

    public abstract void setUp();
    public abstract void tearDown();

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
        // can't implement rollback() because Dao has own Connection object
        setUp();  // implement in concrete DaoTest class
    }

    @After
    public void cleanAfterTest() {
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
