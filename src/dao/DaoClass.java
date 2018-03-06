package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.CodecoolClass;
import model.Mentor;
import model.Student;

public class DaoClass implements IDaoClass {

    @Override
    public CodecoolClass createClass(String name){
        return new CodecoolClass(name);
    }

    @Override
    public CodecoolClass createClass(int groupId, String name, ArrayList<Student> students){
        return new CodecoolClass(groupId, name, students);
    }

    @Override
    public CodecoolClass importClass(Integer classID){
        CodecoolClass codecoolClass = null;
        PreparedStatement preparedStatement;

        String query = "SELECT * from codecool_classes where id_codecool_class = ?;";

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, classID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.isClosed()) {
                String name = resultSet.getString("name");
                ArrayList<Student> students = getStudentsOfClass(classID);

                codecoolClass = createClass(classID, name, students);
                resultSet.close();
            }
            preparedStatement.close();

        }catch (SQLException | ClassNotFoundException e){
            System.out.println("Class not found");
        }

        return codecoolClass;
    }

    @Override
    public boolean exportClass(CodecoolClass codecoolClass) {

        String name = codecoolClass.getName();

        PreparedStatement preparedStatement = null;
        String query = "INSERT into codecool_classes (name) values (?);";

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, name);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;

        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
    }

    @Override
    public ArrayList<CodecoolClass> getAllClasses(){
        ArrayList <CodecoolClass> allCodecoolClasses = new ArrayList <CodecoolClass> ();

        PreparedStatement preparedStatement = null;
        String query = "SELECT * FROM codecool_classes;";
        CodecoolClass codecoolClass;

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                int classId = resultSet.getInt("id_codecool_class");
                String name = resultSet.getString("name");
                ArrayList<Student> students = getStudentsOfClass(classId);

                codecoolClass = createClass(classId, name, students);
                allCodecoolClasses.add(codecoolClass);

            }
            resultSet.close();
            preparedStatement.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("No Classes");
        }
        return allCodecoolClasses;
    }

    @Override
    public void assignMentorToClass(Integer mentorId, Integer classId){
        PreparedStatement preparedStatement = null;
        String query = "INSERT into mentors_in_classes (id_codecool_class, id_mentor) values (?, ?);";

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, classId);
            preparedStatement.setInt(2, mentorId);

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Assigning mentor to class failed");
        }
    }

    @Override
    public void assignStudentToClass(Integer studentId, Integer classId){
        PreparedStatement preparedStatement = null;
        String query = "INSERT into students_in_classes (id_codecool_class, id_student) values (?, ?);";

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, classId);
            preparedStatement.setInt(2, studentId);

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Assigning student to class failed");
        }
    }

    @Override
    public void updateMentorInClass(Integer mentorId, Integer classId){
        PreparedStatement preparedStatement = null;
        String query = "UPDATE mentors_in_classes SET id_codecool_class=? WHERE id_mentor=?;";

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, classId);
            preparedStatement.setInt(2, mentorId);

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("updating mentors class assignment failed");
        }
    }

    @Override
    public void unsignMentorFromClass(Integer mentorId){
        PreparedStatement preparedStatement = null;
        String query = "DELETE FROM mentors_in_classes WHERE id_mentor=?;";

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, mentorId);

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Unsigning mentor from class failed");
        }
    }

    @Override
    public ArrayList<Student> getStudentsOfClass(Integer classID){
        ArrayList <Student> studentsInClass = new ArrayList <> ();

        PreparedStatement preparedStatement = null;
        String query = "SELECT id_user FROM users JOIN students_in_classes WHERE students_in_classes.id_codecool_class=? AND students_in_classes.id_student=users.id_user;";

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, classID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                int userId = resultSet.getInt("id_user");

                studentsInClass.add(new DaoStudent().importStudent(userId));

            }
            resultSet.close();
            preparedStatement.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("No students");
        }
        return studentsInClass;
    }

    @Override
    public CodecoolClass getMentorsClass(Integer mentorId){
        PreparedStatement preparedStatement;
        CodecoolClass mentorsClass = null;

        String query = "SELECT id_codecool_class FROM mentors_in_classes WHERE id_mentor=?;";

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, mentorId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.isClosed()){
                Integer classId = resultSet.getInt("id_codecool_class");

                mentorsClass = importClass(classId);
                resultSet.close();
            }
            preparedStatement.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("updating mentors class assignment failed");
        }

        return mentorsClass;
    }

}
