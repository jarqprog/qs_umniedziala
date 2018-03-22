package model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TeamTest {

    Team team;
    List<Student> students;

    @Before
    public void instance() {
        students = new ArrayList<>();
        team = new Team(1, "Team", students, 100);
    }

    @Test
    public void testSetAvailableCoins() {
        team.setAvailableCoins(1000);
        assertEquals(1000, team.getAvailableCoins());
    }

    @Test
    public void testGetAvailableCoins() {
        assertEquals(100, team.getAvailableCoins());
    }

    @Test
    public void testGetSize() {
        assertEquals(0, team.getSize());
    }

    @Test
    public void testGetBasicTeamInfo() {
        assertEquals("Your Team: Team\nTeam coins: 100", team.getBasicTeamInfo());
    }

    @Test
    public void testAddCoins() {
        team.addCoins(100);
        assertEquals(200, team.getAvailableCoins());
    }

    @Test
    public void testSubtractCoins() {
        team.subtractCoins(100);
        assertEquals(0, team.getAvailableCoins());
    }

    @Test
    public void testHasEnoughCoins() {
        assertEquals(true, team.hasEnoughCoins(90));
    }

    @Test
    public void testGetStudents(){
        assertEquals(students, team.getStudents() );
    }

    @Test
    public void testSetStudents() {
        List<Student> students1 = new ArrayList<>();
        Student student = new Student("artur", "1", "1");
        students1.add(student);
        team.setStudents(students1);
        assertEquals(students1, team.getStudents());
    }

    @Test
    public void testSetGroupID() {
        team.setGroupId(2);
        assertEquals(2, team.getGroupId());
    }

    @Test
    public void testGetGroupID() {
        assertEquals(1, team.getGroupId());
    }

    @Test
    public void testSetGroupName() {
        team.setName("OOO");
        assertEquals("OOO", team.getName());
    }

    @Test
    public void testGetName() {
        assertEquals("Team", team.getName());
    }

    @Test
    public void testNewGroupConstructor() {
        assertNotNull(team);
    }

    @Test
    public void testGroupToString() {
        assertEquals("Students in \"Team\" group:\n\nTeam coins: 100", team.toString());
    }

}