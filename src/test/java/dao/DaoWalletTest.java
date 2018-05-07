//package dao;
//
//import model.Artifact;
//import model.Student;
//import model.Wallet;
//import org.junit.Test;
//import static org.mockito.Mockito.*;
//
//import java.io.ByteArrayOutputStream;
//import java.io.FileDescriptor;
//import java.io.FileOutputStream;
//import java.io.PrintStream;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//public class DaoWalletTest extends DaoTest {
//
//    private DaoWallet dao;
//    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//
//    public void setUp() {
//        dao = new DaoWallet();
//        System.setOut(new PrintStream(outContent));  // setUpStream
//    }
//
//    public void tearDown() {
//        dao = null;
//        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));  // restoreStreams
//    }
//
//    @Test
//    public void TestIf_createWalletIsNotNull() {
//        Wallet wallet = dao.createWallet();
//        assertNotNull(wallet);
//    }
//
//    @Test
//    public void TestIf_createWalletWithParametersWorks() {
//        List<Artifact> artifacts = new ArrayList<>();
//        List<Artifact> usedArtifacts = new ArrayList<>();
//        int counter = 5;
//        while(counter > 0){
//            artifacts.add(mock(Artifact.class));
//            usedArtifacts.add(mock(Artifact.class));
//            counter--;
//        }
//       Wallet wallet =  dao.createWallet(0, 0, artifacts, usedArtifacts);
//       assertNotNull(wallet);
//       assertEquals(5, wallet.getNewArtifacts().size());
//    }
//
//    @Test
//    public void TestIf_importWallet_returnCorrectObjectFromDB() {
//        Wallet wallet = dao.importWallet(6);
//        assertNotNull(wallet);
//        assertEquals(1, wallet.getUsedArtifacts().size());
//        assertEquals("Item id: 3, name: Time Travel, value: 750, " +
//                                "description: extend SI week assignment deadline by one day, " +
//                                "type: individual",
//                        wallet.getNewArtifacts().get(0).toString());
//    }
//
//    @Test
//    public void updateWallet() {
//        dao.updateWallet(createStudentThatAlreadyExistsInDatabase());
//        String expectedInfo = "";
//        assertEquals(expectedInfo, outContent.toString().trim());
//    }
//
//    @Test
//    public void updateWalletWithSQLInjectionInParameters() {
//        dao.updateWallet(createStudentWithSQLInjectionInParameters());
//        String expectedInfo = "";
//        assertEquals(expectedInfo, outContent.toString().trim());
//    }
//
//    @Test
//    public void exportWallet() {
//        dao.exportWallet(createStudentWhichIsNotInDatabase());
//        String expectedInfo = "";
//        assertEquals(expectedInfo, outContent.toString().trim());
//    }
//
//    @Test
//    public void exportWalletOfStudentThatAlreadyExistsInDatabase() {
//        dao.exportWallet(createStudentThatAlreadyExistsInDatabase());
//        String expectedInfo = "Wallet insertion failed";
//        assertEquals(expectedInfo, outContent.toString().trim());
//    }
//
//    @Test
//    public void exportWalletWithSQLInjectionInParameters() {
//        dao.exportWallet(createStudentWithSQLInjectionInParameters());
//        String expectedInfo = "Wallet insertion failed";
//        assertEquals(expectedInfo, outContent.toString().trim());
//    }
//
//    @Test
//    public void exportStudentArtifact() {
//        int artifactId = 1;
//        int studentId = 7;
//        dao.exportStudentArtifact(artifactId, studentId);
//        String expectedInfo = "";
//        assertEquals(expectedInfo, outContent.toString().trim());
//    }
//
//    @Test
//    public void updateStudentsArtifact() {
//        int artifactId = 1;
//        int studentId = 5;
//        dao.updateStudentsArtifact(artifactId, studentId);
//        String expectedInfo = "";
//        assertEquals(expectedInfo, outContent.toString().trim());
//    }
//
//
//    private Student createStudentWhichIsNotInDatabase() {
//        String name = "Brand New Student";
//        String password = "test";
//        String email = "test_student@cc.com";
//        return new Student(name, password, email);
//    }
//
//    private Student createStudentThatAlreadyExistsInDatabase() {
//        Integer id = 6;
//        String name = "Filip Hartman";
//        String password = "filip";
//        String email = "filip@cc.com";
//        return new Student(id, name, password, email);
//    }
//
//    private Student createStudentWithSQLInjectionInParameters() {
//        String name = "Magda OR 1=1";
//        String password = "109 OR 1=1";
//        String email = "test_johnSQL_student@test.com";
//        return new Student(name, password, email);
//    }
//
//}