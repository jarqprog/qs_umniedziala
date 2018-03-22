package dao;

import model.Mentor;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class DaoMentorTest extends DaoTest {

    private DaoMentor dao;

    public void setUp() {
        dao = new DaoMentor();
    }

    public void tearDown() {
        dao = null;
    }

    @Test
    public void constructorTest() {
        assertNotNull(dao);
    }

    @Test
    public void createMentorUsingThreeParameters() {
        String name = "Testing";
        String password = "test";
        String email = "test_mentor@test.com";
        Mentor mentor = dao.createMentor(name, password, email);
        assertNotNull(mentor);
        assertEquals(name, mentor.getName());
        assertEquals(password, mentor.getPassword());
        assertEquals(email, mentor.getEmail());
    }

    @Test
    public void createAdminUsingFourParameters() {
        int id = 1;
        String name = "Testing";
        String password = "test";
        String email = "test_mentor@test.com";
        Mentor mentor = dao.createMentor(id, name, password, email);
        assertNotNull(mentor);
        assertEquals(id, mentor.getUserId());
        assertEquals(name, mentor.getName());
        assertEquals(password, mentor.getPassword());
        assertEquals(email, mentor.getEmail());
    }

    @Test
    public void importMentor() {
        int mentorId = 2;
        Mentor mentor= dao.importMentor(mentorId);
        assertNotNull(mentor);
        String name = "Dominik Starzyk";
        String password = "dominik";
        String email = "dominik@cc.com";
        assertEquals(mentorId, mentor.getUserId());
        assertEquals(name, mentor.getName());
        assertEquals(password, mentor.getPassword());
        assertEquals(email, mentor.getEmail());
    }

    @Test
    public void exportMentor() {
        assertTrue(dao.exportMentor(createMentor()));
    }

    @Test
    public void exportMentorThatAlreadyExistsInDatabase() {
        assertFalse(dao.exportMentor(createTestMentorThatAlreadyExistsInDatabase()));
    }

    @Test
    public void exportMentorWithSQLInjection() {
        assertTrue(dao.exportMentor(createMentorWithSQLInjectionInParameters()));
    }

    @Test
    public void updateMentor() {
        assertTrue(dao.updateMentor(createMentor()));
    }

    @Test
    public void updateMentorWithSQLInjection() {
        assertTrue(dao.updateMentor(createMentorWithSQLInjectionInParameters()));
    }

    @Test
    public void getRoleID() {
        String idName = "mentor";
        int expected = 2;
        assertEquals(expected, dao.getRoleID(idName));
    }

    @Test
    public void getMentorClassId() {
        int classId = dao.getMentorClassId(createTestMentorThatAlreadyExistsInDatabase());
        int expectedClassId = 1;
        assertEquals(expectedClassId, classId);
    }

    @Test
    public void getMentorClassIdIfMentorIsNotInDatabase() {
        assertNull(dao.getMentorClassId(createMentor()));
    }

    @Test
    public void getMentorClassIdUsingSQLInjection() {
        assertNull(dao.getMentorClassId(createMentorWithSQLInjectionInParameters()));
    }

    @Test
    public void getAllMentors() {
        List<Mentor> artifacts = dao.getAllMentors();
        assertNotNull(artifacts);
        artifacts.forEach(Assert::assertNotNull);
        assertTrue(artifacts
                .stream().filter(a -> a.getEmail().equals("dominik@cc.com"))
                .collect(Collectors.toList())
                .size() > 0);
    }

    private Mentor createMentor() {
        String name = "John the Great";
        String password = "zyx";
        String email = "test_john@test.com";
        return new Mentor(name, password, email);
    }

    private Mentor createTestMentorThatAlreadyExistsInDatabase() {
        Integer id = 2;
        String name = "Dominik Starzyk";
        String password = "dominik";
        String email = "dominik@cc.com";
        return new Mentor(id, name, password, email);
    }

    private Mentor createMentorWithSQLInjectionInParameters() {
        String name = "105 OR 1=1";
        String password = "105 OR 1=1";
        String email = "test_johnSQL@test.com";
        return new Mentor(name, password, email);
    }
}