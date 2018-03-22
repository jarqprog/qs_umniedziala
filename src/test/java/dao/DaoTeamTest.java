package dao;

import model.Student;
import model.Team;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class DaoTeamTest extends DaoTest {

    private IDaoTeam dao;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    public void setUp() {
        dao = new DaoTeam();
        System.setOut(new PrintStream(outContent));  // setUpStream
    }

    public void tearDown() {
        dao = null;
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));  // restoreStreams
    }

    @Test
    public void constructorTest() {
        assertNotNull(dao);
    }

    @Test
    public void createClassUsingOneParameter() {
        String name = "testing";
        Team team = dao.createTeam(name);
        assertNotNull(team);
        assertEquals(name, team.getName());
    }

    @Test
    public void createClassUsingFourParameters() {
        int id = 10;
        String name = "testing team";
        int coins = 100;
        List<Student> students = new ArrayList<>();
        Team team = dao.createTeam(id, name, students, coins);
        assertNotNull(team);
        assertEquals(id, team.getGroupId());
        assertEquals(name, team.getName());
        assertEquals(coins, team.getAvailableCoins());
    }

    @Test
    public void importTeam() {
        int id = 2;
        Team team = dao.importTeam(id);
        String name = "Ziemniaki";
        int coins = 55;
        assertNotNull(team);
        assertEquals(id, team.getGroupId());
        assertEquals(name, team.getName());
        assertEquals(coins, team.getAvailableCoins());
    }


    @Test
    public void importTeamThatNotExists() {
        int id = 1006;
        assertNull(dao.importTeam(id));
    }

    @Test
    public void exportTeam() {
        assertTrue(dao.exportTeam(createTeam()));
    }

    @Test
    public void updateTeamData() {
        dao.updateTeamData(createTeamThatExistsInDatabase());
        String expectedInfo = "";
        assertEquals(expectedInfo, outContent.toString().trim());
    }

    @Test
    public void getTeamByStudentId() {
        int studentId = 5;
        Team team = dao.getTeamByStudentId(studentId);
        assertNotNull(team);
        assertTrue(team.getSize() > 0);
        String expectedInfo = "";
        assertEquals(expectedInfo, outContent.toString().trim());
    }

    @Test
    public void getTeamByStudentWhichIsNotInAnyTeam() {
        int studentId = 9005;
        Team team = dao.getTeamByStudentId(studentId);
        assertNull(team);
    }

    @Test
    public void getStudentsOfTeam() {
        int teamId = 1;
        List<Student> students = dao.getStudentsOfTeam(teamId);
        String expectedInfo = "";
        assertEquals(expectedInfo, outContent.toString().trim());
        assertNotNull(students);
        assertTrue(students.size() > 0);
    }

    @Test
    public void getStudentsOfTeamIfTeamHasNoStudents() {
        int teamId = 4;
        List<Student> students = dao.getStudentsOfTeam(teamId);
        assertNotNull(students);
        assertTrue(students.size() == 0);
    }

    @Test
    public void getAllTeams() {
        List<Team> classes = dao.getAllTeams();
        assertNotNull(classes);
        classes.forEach(Assert::assertNotNull);
        assertTrue(classes
                .stream().filter(a -> a.getName().equals("Cytryny"))
                .collect(Collectors.toList())
                .size() > 0);
    }

    @Test
    public void assignStudentToTeam() {
        int studentId = 21;  // not existing student (we can assign him)
        int teamId = 2;
        dao.assignStudentToTeam(studentId, teamId);
        String expectedInfo = "";
        assertEquals(expectedInfo, outContent.toString().trim());
    }

    @Test
    public void assignStudentToTeamWhichNotExists() {
        int studentId = 6;
        int teamId = 2000;
        dao.assignStudentToTeam(studentId, teamId);
        String expectedInfo = "Assignment of student to team failed";
        assertEquals(expectedInfo, outContent.toString().trim());
    }

    private Team createTeam() {
        String name = "4test";
        return new Team(name);
    }

    private Team createTeamThatExistsInDatabase() {
        String name = "Cytryny";
        return new Team(name);
    }
}