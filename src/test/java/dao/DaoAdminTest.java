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
        int adminId = 2;
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
    public void updateAdmin() {
        assertTrue(dao.updateAdmin(createTestAdminThatAlreadyExistsInDatabase()));
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
        Integer id = 2;
        String name = "Adam Mad";
        String password = "adam";
        String email = "adam@cc.com";
        return new Admin(id, name, password, email);
    }
}
