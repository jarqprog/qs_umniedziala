package dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;

import java.sql.Connection;

public abstract class DaoTest {

    public abstract void setUp();
    public abstract void tearDown();

    @Before
    public void prepareTest() {
        DbConnection.setUrl(DbUrl.DATABASE_TEST_URL);
        setUp();

    }

    @After
    public void clearAfterTest() {
        DbConnection.setUrl(DbUrl.DATABASE_MAIN_URL);
        tearDown();
    }

    @Rule
    public MethodRule watchman = new TestWatchman() {
        public void starting(FrameworkMethod method) {
            System.out.println("Starting test: " + method.getName());
        }
    };

    private void prepareDatabase() {

        if(! DbConnection.getUrl().equals(DbUrl.DATABASE_TEST_URL.getUrl())) {
            DbConnection.setUrl(DbUrl.DATABASE_TEST_URL);
        }

        Connection connection = DbConnection.getConnection();

        String query = "Delete * "



    }

}
