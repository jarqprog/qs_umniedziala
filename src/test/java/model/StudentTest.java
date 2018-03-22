package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class StudentTest extends ModelTest {

    private Student student;
    private Artifact artifact;

    @Before
    public void createStudent() {
        student = new Student(1, "name", "password",  "email");
        student.addCoins(100);
        artifact = mock(Artifact.class);
        student.addNewArtifact(artifact);
    }

    @Test
    public void testGetWallet() {
        assertEquals(Wallet.class, student.getWallet().getClass());
    }

    @Test
    public void testSetWallet() {
        Wallet wallet = mock(Wallet.class);
        student.setWallet(wallet);
        assertEquals(wallet, student.getWallet());
    }

    @Test
    public void testAddCoins() {
        student.addCoins(100);
        assertEquals(200, student.getWallet().getAllCoins());
    }

    @Test
    public void testGetAllNewArtifacts() {
        assertEquals(1, student.getWallet().getNewArtifacts().size());
    }

    @Test
    public void testAddNewArtifact() {
        Artifact artifact = mock(Artifact.class);
        student.addNewArtifact(artifact);
        assertTrue(student.getWallet().getNewArtifacts().size() > 0);
    }

    @Test
    public void testMarkArtifactAsBougth() {
        student.markArtifactAsBougth(artifact);
        assertEquals(0, student.getWallet().getNewArtifacts().size());
        assertEquals(1, student.getWallet().getUsedArtifacts().size());
    }

    @Test
    public void testHasEnoughCoins() {
        assertTrue(student.hasEnoughCoins(99));
        assertFalse(student.hasEnoughCoins(101));
    }

    @Test
    public void testHasEnoughCoins2() {
        student.subtractCoins(100);
        System.out.println(student.getWallet().getAvailableCoins());
        assertTrue(student.hasEnoughCoins(0));
    }

    @Test
    public void testSubtractCoins() {
        student.subtractCoins(100);
        assertEquals(0, student.getWallet().getAvailableCoins());
    }

    @Test
    public void testGetName() {
        assertEquals("name", student.getName());
    }

    @Test
    public void testGetPassword() {
        assertEquals("password", student.getPassword());
    }

    @Test
    public void testGetEmail() {
        assertEquals("email", student.getEmail());
    }

    @Test
    public void testGetUserId() {
        assertEquals(1, student.getUserId());
    }

    @Test
    public void testSetName() {
        student.setName("newName");
        assertEquals("newName", student.getName());
    }

    @Test
    public void setPassword() {
        student.setPassword("123");
        assertEquals("123", student.getPassword());
    }

    @Test
    public void setEmail() {
        student.setEmail("a@cc.com");
        assertEquals("a@cc.com", student.getEmail());
    }

    @Test
    public void testToString() {
        assertEquals("ID: 1, Name: name, Email: email", student.toString());
    }
}