package dao;

import model.Admin;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;

import static org.junit.Assert.*;

public class DaoAdminTest {

    private DaoAdmin dao;

    @Before
    public void setUp() {
        dao = new DaoAdmin();
    }

    @After
    public void tearDown() {
        dao = null;
    }

    @Test
    public void constructorTest() {
        DaoAdmin testDao = new DaoAdmin();
        assertNotNull(testDao);
    }

    @Test
    public void createAdminUsingThreeParameters() {
        Admin admin = dao.createAdmin("Ludwik", "12321", "ludwik@cc.com");
        assertNotNull(admin);
        String expected = "Ludwik";
        assertEquals(expected, admin.getName());
        expected = "12321";
        assertEquals(expected, admin.getPassword());
        expected = "ludwik@cc.com";
        assertEquals(expected, admin.getEmail());
    }


    @Test
    public void createAdminUsingFourParameters() {
        Admin admin = dao.createAdmin(1, "Marcin", "12321", "marcin@cc.com");
        assertNotNull(admin);
        String expected = "Marcin";
        assertEquals(expected, admin.getName());
        expected = "12321";
        assertEquals(expected, admin.getPassword());
        expected = "marcin@cc.com";
        assertEquals(expected, admin.getEmail());
    }

    @Test
    public void importAdmin() {
        Admin admin = dao.importAdmin(1);
        assertNotNull(admin);
        int expected = 1;
        assertEquals(expected, admin.getUserId());
        assertEquals(Admin.class, admin.getClass());
    }

    @Test
    public void exportAdmin() {
        // todo
    }

    @Test
    public void updateAdmin() {
        // todo
    }

    @Test
    public void getRoleID() {
        String idName = "admin";
        int expected = 1;
        assertEquals(expected, dao.getRoleID(idName));
    }

    @Rule
    public MethodRule watchman = new TestWatchman() {
        public void starting(FrameworkMethod method) {
            System.out.println("Starting test: " + method.getName());
        }
    };
}