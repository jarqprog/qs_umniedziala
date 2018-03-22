package dao;

import model.Admin;
import org.junit.Test;

import static org.junit.Assert.*;

public class DaoAdminTest extends DaoTest {

    private DaoAdmin dao;

    public void setUp() {
        dao = new DaoAdmin();
    }

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

        String name = "Testing";
        String password = "test";
        String email = "test12@test.com";
        Admin admin = dao.createAdmin(name, password, email);
        assertNotNull(admin);
        assertEquals(name, admin.getName());
        assertEquals(password, admin.getPassword());
        assertEquals(email, admin.getEmail());
    }


    @Test
    public void createAdminUsingFourParameters() {
        Integer id = 1;
        String name = "Testing";
        String password = "test";
        String email = "test12@test.com";

        Admin admin = dao.createAdmin(id, name, password, email);
        assertNotNull(admin);
        assertEquals(name, admin.getName());
        assertEquals(password, admin.getPassword());
        assertEquals(email, admin.getEmail());
    }

    @Test
    public void importAdmin() {
        int adminId = 10;
        Admin admin = dao.importAdmin(adminId);
        assertNotNull(admin);
        String name = "Adam Mad";
        String password = "adam";
        String email = "adam@cc.com";
        assertEquals(adminId, admin.getUserId());
        assertEquals(name, admin.getName());
        assertEquals(password, admin.getPassword());
        assertEquals(email, admin.getEmail());
    }

    @Test
    public void exportNewAdmin() {
        assertTrue(dao.exportAdmin(createTestAdminWhichIsNotInDatabase()));
    }

    @Test
    public void exportAdminThatExistsInDatabase() {
        assertFalse(dao.exportAdmin(createTestAdminThatAlreadyExistsInDatabase()));
    }

    @Test
    public void exportAdminWithSQLInjection() {
        assertTrue(dao.exportAdmin(createAdminWithSQLInjectionInParameters()));
    }

    @Test
    public void updateAdmin() {
        assertTrue(dao.updateAdmin(createTestAdminThatAlreadyExistsInDatabase()));
    }

    @Test
    public void updateAdminWithSQLInjection() {
        assertTrue(dao.updateAdmin(createAdminWithSQLInjectionInParameters()));
    }

    @Test
    public void getRoleID() {
        String idName = "admin";
        int expected = 1;
        assertEquals(expected, dao.getRoleID(idName));
    }

    private Admin createTestAdminWhichIsNotInDatabase() {
        String name = "Brand New";
        String password = "test";
        String email = "test@cc.com";
        return new Admin(name, password, email);
    }

    private Admin createTestAdminThatAlreadyExistsInDatabase() {
        Integer id = 10;
        String name = "Adam Mad";
        String password = "adam";
        String email = "adam@cc.com";
        return new Admin(id, name, password, email);
    }

    private Admin createAdminWithSQLInjectionInParameters() {
        String name = "105 OR 1=1";
        String password = "105 OR 1=1";
        String email = "test_johnSQL_admin@test.com";
        return new Admin(name, password, email);
    }
}
