//package dao;
//
//import model.User;
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
//public class DaoLoginTest extends  DaoTest{
//
//    private DaoLogin dao;
//
//    @Override
//    public void setUp() {
//        dao = new DaoLogin();
//    }
//
//    @Override
//    public void tearDown() {
//        dao = null;
//    }
//
//    @Test
//    public void testGetUser() {
//        String email = "joanna@cc.com";
//        String password = "joanna";
//        User user = dao.getUser(email, password);
//        assertEquals("Joanna Baran", user.getName());
//        assertEquals(7 ,user.getUserId());
//    }
//
//    @Test
//    public void testGetUserWithSQLInjection() {
//        String email = "105 OR 101=101";
//        String password = "105 OR 101=101";
//        assertNull(dao.getUser(email, password));
//
//    }
//
//    @Test
//    public void testUserDoesNotExist() {
//        User user = dao.getUser(" ", " ");
//        assertEquals(null, user);
//    }
//
//    @Test
//    public void testGetRole() {
//        assertEquals("student", dao.getRole(3));
//        assertEquals("mentor", dao.getRole(2));
//        assertEquals("admin", dao.getRole(1));
//        assertEquals(null, dao.getRole(0));
//    }
//}