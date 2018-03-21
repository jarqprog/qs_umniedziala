package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminTest {

    User admin;

    @BeforeEach
    void instance() {
        admin = new Admin(11,"Artur", "123", "artur@cc.com");
    }

    @Test
    void  testGetID() {
        assertEquals(11, admin.getUserId());
    }

    @Test
    void testGetName(){
        assertEquals("Artur", admin.getName());
    }

    @Test
    void testSetName() {
        admin.setName("Robstep");
        assertEquals("Robstep", admin.getName());
    }

    @Test
    void testGetPassword(){
        assertEquals("123", admin.getPassword());
    }

    @Test
    void testSetPassword() {
        admin.setPassword("321");
        assertEquals("321", admin.getPassword());
    }

    @Test
    void testGetEmail(){
        assertEquals("artur@cc.com", admin.getEmail());
    }

    @Test
    void testSetEmail() {
        admin.setEmail("dupa");
        assertEquals("dupa", admin.getEmail());
    }

    @Test
    void testToString() {
        assertEquals("ID: 11, Name: Artur, Email: artur@cc.com", admin.toString());
    }

}