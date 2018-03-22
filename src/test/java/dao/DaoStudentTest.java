package dao;

import model.Student;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DaoStudentTest extends DaoTest{

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
    public void testCreateStudent() {
        Student student = dao.createStudent(1, "A", "B", "C");
        assertEquals(1, student.getUserId());
        assertEquals("A", student.getName());
        assertEquals("B", student.getPassword());
        assertEquals("C", student.getEmail());
    }

    @Test
    public void testCreateStudent1() {
        Student student = dao.createStudent("D", "F", "G");
        assertEquals("D", student.getName());
        assertEquals("F", student.getPassword());
        assertEquals("G", student.getEmail());
    }

    @Test
    public void testImportStudent() {
        int id = 9;
        Student student = dao.importStudent(id);
        String name = "Mariusz Trąbalski";
        String password = "mariusz";
        String email = "mariusz@cc.com";
        assertEquals(name, student.getName());
        assertEquals(password, student.getPassword());
        assertEquals(email, student.getEmail());
    }

    @Test
    public void testImportNewStudent() {
        String email = "mariusz@cc.com";
        Student student = dao.importNewStudent(email);
        int id = 9;
        String name = "Mariusz Trąbalski";
        String password = "mariusz";
        assertEquals(id, student.getUserId());
        assertEquals(name, student.getName());
        assertEquals(password, student.getPassword());
        assertEquals(email, student.getEmail());
    }

    @Test
    public void testExportStudent() {
        assertTrue(dao.exportStudent(new Student("F", "D", "C")));
    }

    @Test
    public void testUpdateStudent() {
        Student student = dao.importStudent(9);
        assertTrue(dao.updateStudent(student));
    }

    @Test
    public void testGetRoleID() {
        assertEquals(3, dao.getRoleID("student"));
        assertEquals(2, dao.getRoleID("mentor"));
        assertEquals(1, dao.getRoleID("admin"));
        assertEquals(0, dao.getRoleID(" "));
    }

    @Test
    public void getAllStudents() {
        List<Student> students = new ArrayList<>();
        Student student0 = dao.importStudent(5);
        Student student1 = dao.importStudent(6);
        Student student2 = dao.importStudent(7);
        Student student3 = dao.importStudent(8);
        Student student4 = dao.importStudent(9);
        students.add(student0);
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        List<Student> importedStudents = dao.getAllStudents();
        assertEquals(students.get(0).getName(), importedStudents.get(0).getName());
        assertEquals(students.get(1).getName(), importedStudents.get(1).getName());
        assertEquals(students.get(2).getName(), importedStudents.get(2).getName());
        assertEquals(students.get(3).getName(), importedStudents.get(3).getName());
        assertEquals(students.get(4).getName(), importedStudents.get(4).getName());
    }
}