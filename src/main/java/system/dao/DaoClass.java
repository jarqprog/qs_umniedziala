package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import model.CodecoolClass;
import model.Student;

public class DaoClass extends SqlDao implements IDaoClass {

    private final IDaoStudent daoStudent;
    private final String DATABASE_TABLE = "codecool_classes";
    private final String ID_LABEL = "id_codecool_class";

    DaoClass(Connection connection, IDaoStudent daoStudent) {
        super(connection);
        this.daoStudent = daoStudent;
    }

    @Override
    public CodecoolClass createClass(String name){

        try {
            int id = getLowestFreeIdFromGivenTable(DATABASE_TABLE, ID_LABEL);
            CodecoolClass codecoolClass = new CodecoolClass(id, name, new ArrayList<>());
            exportClass(codecoolClass);
            return codecoolClass;
        } catch (SQLException e) {
            e.printStackTrace();
            return new NullCodecoolClass();
        }
    }

    @Override
    public CodecoolClass importClass(Integer classID){

        String query = "SELECT * FROM codecool_classes WHERE id_codecool_class = ?;";
        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {

            preparedStatement.setInt(1, classID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if ( resultSet.next() ) {
                    String name = resultSet.getString("name");
                    List<Student> students = getStudentsOfClass(classID);
                    return new CodecoolClass(classID, name, students);
                }
                return new NullCodecoolClass();
            }

        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Class not found");
            return new NullCodecoolClass();
        }
    }

    private boolean exportClass(CodecoolClass codecoolClass) throws SQLException {

        String query = "INSERT INTO codecool_classes (id_codecool_class, name) VALUES (?, ?);";

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {

            preparedStatement.setInt(1, codecoolClass.getGroupId());
            preparedStatement.setString(2, codecoolClass.getName());
            preparedStatement.executeUpdate();
            return true;
        }
    }

    @Override
    public List<CodecoolClass> getAllClasses() {
        List <CodecoolClass> allCodecoolClasses = new ArrayList <>();

        String query = "SELECT * FROM codecool_classes;";
        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery() ) {

            while(resultSet.next()) {
                int classId = resultSet.getInt(ID_LABEL);
                String name = resultSet.getString("name");
                List<Student> students = getStudentsOfClass(classId);
                allCodecoolClasses.add(new CodecoolClass(classId, name, students));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No Classes");
        }
        return allCodecoolClasses;
    }

    @Override
    public boolean assignMentorToClass(Integer mentorId, Integer classId){
         
        String query = "INSERT INTO mentors_in_classes (id_codecool_class, id_mentor) VALUES (?, ?);";

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
             
            preparedStatement.setInt(1, classId);
            preparedStatement.setInt(2, mentorId);
            preparedStatement.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Assigning mentor to class failed");
            return false;
        }
    }

    @Override
    public boolean assignStudentToClass(Integer studentId, Integer classId){
         
        String query = "INSERT INTO students_in_classes (id_codecool_class, id_student) VALUES (?, ?);";

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
             
            preparedStatement.setInt(1, classId);
            preparedStatement.setInt(2, studentId);
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Assigning student to class failed");
            return false;
        }
    }

    @Override
    public boolean updateMentorInClass(Integer mentorId, Integer classId){
        
        String query = "UPDATE mentors_in_classes SET id_codecool_class=? WHERE id_mentor=?;";

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, classId);
            preparedStatement.setInt(2, mentorId);
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("updating mentors class assignment failed");
            return false;
        }
    }

    @Override
    public boolean unsignMentorFromClass(Integer mentorId){
        String query = "DELETE FROM mentors_in_classes WHERE id_mentor=?;";

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
            preparedStatement.setInt(1, mentorId);
            preparedStatement.executeUpdate();
            return true;
             

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Unsigning mentor from class failed");
            return false;
        }
    }

    @Override
    public List<Student> getStudentsOfClass(Integer classID){
        List <Student> studentsInClass = new ArrayList <> ();

        String query = "SELECT id_user FROM users INNER JOIN students_in_classes ON " +
                "students_in_classes.id_codecool_class=? AND students_in_classes.id_student=users.id_user;";

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
             
            preparedStatement.setInt(1, classID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                int userId = resultSet.getInt("id_user");
                studentsInClass.add(daoStudent.importStudent(userId));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No students");
        }
        return studentsInClass;
    }

    @Override
    public CodecoolClass getMentorsClass(Integer mentorId){
        String query = "SELECT id_codecool_class FROM mentors_in_classes WHERE id_mentor=?;";

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {

            preparedStatement.setInt(1, mentorId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                Integer classId = resultSet.getInt(ID_LABEL);
                return importClass(classId);
            }
            return new NullCodecoolClass();

        } catch (SQLException e) {
            System.out.println("updating mentors class assignment failed");
            return new NullCodecoolClass();
        }
    }

    @Override
    public CodecoolClass getStudentClass(Integer studentId) {

        String query = "SELECT id_codecool_class FROM students_in_classes WHERE id_student=?;";

        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {

            preparedStatement.setInt(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                Integer classId = resultSet.getInt(ID_LABEL);
                return importClass(classId);
            }
            return new NullCodecoolClass();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("updating students class assignment failed");
            return new NullCodecoolClass();
        }
    }
}

//    @Override
//    public List<String> getAllClassNames() {
//        List<String> classes = new ArrayList<>();
//        String query = "SELECT * FROM codecool_classes";
//
//        try ( PreparedStatement preparedStatement = getConnection().prepareStatement(query) ) {
//
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                while (resultSet.next()) {
//                    String id = resultSet.getString(ID_LABEL);
//                    String name = resultSet.getString("name");
//                    classes.add("Id: " + id + " name: " + name + "<br>");
//                }
//            }
//
//        }catch (SQLException e){
//            System.out.println("Class not found");
//        }
//
//        return classes;
//    }