package model;

import org.junit.*;

import static junit.framework.Assert.*;

public class AdminTest extends ModelTest {

    private User admin;

    @Before
    public void instance() {
        admin = new Admin(11,"Artur", "123", "artur@cc.com");
    }

    @Test
    public void testGetID() {
        assertEquals(11, admin.getUserId());
    }

    @Test
    public void testGetName(){
        assertEquals("Artur", admin.getName());
    }

    @Test
    public void testSetName() {
        admin.setName("Robstep");
        assertEquals("Robstep", admin.getName());
    }

    @Test
    public void testGetPassword(){
        assertEquals("123", admin.getPassword());
    }

    @Test
    public void testSetPassword() {
        admin.setPassword("321");
        assertEquals("321", admin.getPassword());
    }

    @Test
    public void testGetEmail(){
        assertEquals("artur@cc.com", admin.getEmail());
    }

    @Test
    public void testSetEmail() {
        admin.setEmail("tylek");
        assertEquals("tylek", admin.getEmail());
    }

    @Test
    public void testToString() {
        assertEquals("ID: 11, Name: Artur, Email: artur@cc.com", admin.toString());
    }

}