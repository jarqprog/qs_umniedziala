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
        int adminId = 1;
        Admin admin = dao.importAdmin(adminId);
        assertNotNull(admin);
        assertEquals(adminId, admin.getUserId());
        assertEquals(Admin.class, admin.getClass());
    }

    @Test
    public void exportAdmin() {
        int adminId = 100000;
        Admin admin = createTestAdmin(adminId);
        Boolean isDone = dao.exportAdmin(admin);
//        assertTrue(isDone);
//        Admin imported = dao.importAdmin(adminId);
//        assertNotNull(imported);
//        assertEquals(admin.getUserId(), imported.getUserId());
//        assertEquals(admin.getName(), imported.getName());
//        assertEquals(admin.getPassword(), imported.getPassword());
//        assertEquals(admin.getEmail(), imported.getEmail());
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

    private Admin createTestAdmin(int adminId) {
        String name = "Testing";
        String password = "test";
        String email = "test@cc.com";
        return new Admin(adminId, name, password, email);
    }
}