//package dao;
//
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.MethodRule;
//import org.junit.rules.TestWatchman;
//import org.junit.runners.model.FrameworkMethod;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
//
//public class DbConnectionTest {
//
//    @Test
//    public void getConnection() {
//        try (Connection con = DbConnection.getConnection()) {
//            assertNotNull(con);
//            assertTrue(! (con.isClosed()));
//            con.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Rule
//    public MethodRule watchman = new TestWatchman() {
//        public void starting(FrameworkMethod method) {
//            System.out.println("Starting test: " + method.getName());
//        }
//    };
//}