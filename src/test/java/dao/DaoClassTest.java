//package dao;
//
//import model.CodecoolClass;
//import model.Student;
//import org.junit.Assert;
//import org.junit.Rule;
//import org.junit.Test;
//
//import java.io.ByteArrayOutputStream;
//import java.io.FileDescriptor;
//import java.io.FileOutputStream;
//import java.io.PrintStream;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static org.junit.Assert.*;
//
//public class DaoClassTest extends DaoTest {
//
//    private DaoClass dao;
//    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//
//    public void setUp() {
//        dao = new DaoClass();
//        System.setOut(new PrintStream(outContent));  // setUpStream
//    }
//
//    public void tearDown() {
//        dao = null;
//        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));  // restoreStreams
//    }
//
//    @Test
//    public void constructorTest() {
//        assertNotNull(dao);
//    }
//
//    @Test
//    public void createClassUsingOneParameter() {
//        String name = "T";
//        CodecoolClass codecoolClass = dao.createClass(name);
//        assertNotNull(codecoolClass);
//        assertEquals(name, codecoolClass.getName());
//    }
//
//    @Test
//    public void createClassUsingThreeParameters() {
//        int id = 10;
//        String name = "T";
//        List< Student > students = new ArrayList<>();
//        CodecoolClass codecoolClass = dao.createClass(id, name, students);
//        assertNotNull(codecoolClass);
//        assertEquals(id, codecoolClass.getGroupId());
//        assertEquals(name, codecoolClass.getName());
//    }
//
//    @Test
//    public void importClass() {
//        int id = 1;
//        CodecoolClass codecoolClass = dao.importClass(id);
//        String name = "2017-1-a";
//        assertNotNull(codecoolClass);
//        assertEquals(id, codecoolClass.getGroupId());
//        assertEquals(name, codecoolClass.getName());
//    }
//
//    @Test
//    public void importClassThatNotExists() {
//        int id = 1001;
//        assertNull(dao.importClass(id));
//    }
//
//    @Test
//    public void exportClass() {
//        assertTrue(dao.exportClass(createCodeCoolClass()));
//    }
//
//    @Test
//    public void exportClassWithSQLInjection() {
//        assertTrue(dao.exportClass(createClassWithSQLInjectionInParameters()));
//    }
//
//    @Test
//    public void getAllClasses() {
//        List<CodecoolClass> classes = dao.getAllClasses();
//
//        assertNotNull(classes);
//        classes.forEach(Assert::assertNotNull);
//        assertTrue(classes
//                .stream().filter(a -> a.getName().equals("2017-1-a"))
//                .collect(Collectors.toList())
//                .size() > 0);
//    }
//
//    @Test
//    public void assignMentorToClass() {
//        int mentorId = 20;  // not existing mentor (we can assign him)
//        int classId = 2;
//        dao.assignMentorToClass(mentorId, classId);
//        String expectedInfo = "";
//        assertEquals(expectedInfo, outContent.toString().trim());
//    }
//
//    @Test
//    public void assignStudentToClass() {
//        int studentId = 21;  // not existing student (we can assign him)
//        int classId = 2;
//        dao.assignStudentToClass(studentId, classId);
//        String expectedInfo = "";
//        assertEquals(expectedInfo, outContent.toString().trim());
//    }
//
//    @Test
//    public void assignStudentToClassWhichNotExists() {
//        int studentId = 6;
//        int classId = 2000;
//        dao.assignStudentToClass(studentId, classId);
//        String expectedInfo = "Assigning student to class failed";
//        assertEquals(expectedInfo, outContent.toString().trim());
//    }
//
//    @Test
//    public void updateMentorInClass() {
//        int mentorId = 3;
//        int classId = 1;
//        dao.updateMentorInClass(mentorId, classId);
//        String expectedInfo = "";
//        assertEquals(expectedInfo, outContent.toString().trim());
//    }
//
//    @Test
//    public void unsignMentorFromClass() {
//        int mentorId = 2;
//        dao.unsignMentorFromClass(mentorId);
//        String expectedInfo = "";
//        assertEquals(expectedInfo, outContent.toString().trim());
//    }
//
//    @Test
//    public void getStudentsOfClass() {
//        int classId = 4;
//        List<Student> students = dao.getStudentsOfClass(classId);
//        String expectedInfo = "";
//        assertEquals(expectedInfo, outContent.toString().trim());
//        assertNotNull(students);
//        assertTrue(students.size() > 0);
//    }
//
//    @Test
//    public void getStudentsOfClassWhichNotExists() {
//        int classId = 10;
//        List<Student> students = dao.getStudentsOfClass(classId);
//        assertNotNull(students);
//        assertTrue(students.size() == 0);
//    }
//
//    @Test
//    public void getStudentsFromEmptyClass() {
//        int classId = 3;
//        List<Student> students = dao.getStudentsOfClass(classId);
//        assertNotNull(students);
//        assertTrue(students.size() == 0);
//    }
//
//    @Test
//    public void getMentorsClass() {
//        int mentorId = 4;
//        CodecoolClass codecoolClass = dao.getMentorsClass(mentorId);
//        String expectedInfo = "";
//        assertEquals(expectedInfo, outContent.toString().trim());
//        assertNotNull(codecoolClass);
//        assertTrue(codecoolClass.getGroupId() == 2);
//    }
//
//    @Test
//    public void getMentorsClassIfMentorIdIsInvalid() {
//        int mentorId = 40;
//        CodecoolClass codecoolClass = dao.getMentorsClass(mentorId);
//        assertNull(codecoolClass);
//    }
//
//    private CodecoolClass createCodeCoolClass() {
//        String name = "2018-1-test";
//        return new CodecoolClass(name);
//    }
//
//    private CodecoolClass createClassWithSQLInjectionInParameters() {
//        String name = "105 OR 101=101";
//        return new CodecoolClass(name);
//    }
//}
