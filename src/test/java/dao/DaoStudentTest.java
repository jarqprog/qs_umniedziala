package dao;

import model.Student;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class DaoStudentTest extends DaoTest {

    private DaoStudent dao;

    public void setUp() {
        dao = new DaoStudent();
    }

    public void tearDown() {
        dao = null;
    }

    @Test
    public void testConstructor(){
        assertNotNull(dao);
    }

    @Test
    public void testCreateStudentUsingThreeParameters() {
        String name = "Brand New Student";
        String password = "test";
        String email = "test_student_29@cc.com";
        Student student = dao.createStudent(name, password, email);
        assertEquals(name, student.getName());
        assertEquals(password, student.getPassword());
        assertEquals(email, student.getEmail());
    }

    @Test
    public void testCreateStudentUsingFourParameters() {
        int id = 1009;
        String name = "Brand New Student";
        String password = "test";
        String email = "test_student_29@cc.com";
        Student student = dao.createStudent(id, name, password, email);
        assertEquals(id, student.getUserId());
        assertEquals(name, student.getName());
        assertEquals(password, student.getPassword());
        assertEquals(email, student.getEmail());
    }

    @Test
    public void testImportStudent() {
        int id = 7;
        Student student = dao.importStudent(id);
        String name = "Joanna Baran";
        String password = "joanna";
        String email = "joanna@cc.com";
        assertEquals(name, student.getName());
        assertEquals(password, student.getPassword());
        assertEquals(email, student.getEmail());
    }

    @Test
    public void testImportNewStudent() {
        String email = "joanna@cc.com";
        Student student = dao.importNewStudent(email);
        int id = 7;
        String name = "Joanna Baran";
        String password = "joanna";
        assertEquals(id, student.getUserId());
        assertEquals(name, student.getName());
        assertEquals(password, student.getPassword());
        assertEquals(email, student.getEmail());
    }

    @Test
    public void testExportStudent() {
        assertTrue(dao.exportStudent(createStudentWhichIsNotInDatabase()));
    }

    @Test
    public void testExportStudentWithSQLInjectionInParameters() {
        assertTrue(dao.exportStudent(createStudentWithSQLInjectionInParameters()));
    }

    @Test
    public void testUpdateStudent() {
        assertTrue(dao.updateStudent(createStudentThatAlreadyExistsInDatabase()));
    }

    @Test
    public void testUpdateStudentWithSQLInjectionInParameters() {
        assertTrue(dao.updateStudent(createStudentWithSQLInjectionInParameters()));
    }

    @Test
    public void testGetRoleID() {
        int classId = dao.getRoleID("student");
        int expectedClassId = 3;
        assertEquals(expectedClassId, classId);
    }

    @Test
    public void getAllStudents() {
        List<Student> artifacts = dao.getAllStudents();
        assertNotNull(artifacts);
        artifacts.forEach(Assert::assertNotNull);
        assertTrue(artifacts
                .stream().filter(a -> a.getEmail().equals("jadzia@cc.com"))
                .collect(Collectors.toList())
                .size() > 0);
    }

    private Student createStudentWhichIsNotInDatabase() {
        String name = "Brand New Student";
        String password = "test";
        String email = "test_student@cc.com";
        return new Student(name, password, email);
    }

    private Student createStudentThatAlreadyExistsInDatabase() {
        Integer id = 8;
        String name = "Jadzia Kot";
        String password = "jadzia";
        String email = "jadzia@cc.com";
        return new Student(id, name, password, email);
    }

    private Student createStudentWithSQLInjectionInParameters() {
        String name = "Jadzia OR 1=1";
        String password = "109 OR 1=1";
        String email = "test_johnSQL_student@test.com";
        return new Student(name, password, email);
    }
}