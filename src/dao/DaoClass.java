package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.CodecoolClass;
import model.Student;

public class DaoClass{

    public CodecoolClass createClass(String name){
        return new CodecoolClass(name);
    }

    public CodecoolClass createClass(int groupId, String name, ArrayList<Student> students){
        return new CodecoolClass(groupId, name, students);
    }

    public CodecoolClass importClass(Integer classID){ return null;}

    public void exportClass(CodecoolClass codecoolClass) {

        String name = codecoolClass.getName();

        PreparedStatement preparedStatement = null;
        String query = "INSERT into codecool_classes (name) values (?);";

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, name);

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Class insertion failed");
        }
    }

    public ArrayList<CodecoolClass> getAllClasses(){
        ArrayList <CodecoolClass> allCodecoolClasses = new ArrayList <CodecoolClass> ();

        PreparedStatement preparedStatement = null;
        String query = "SELECT * FROM codecool_classes;";
        CodecoolClass codecoolClass;

        try {
            preparedStatement = DbConnection.getConnection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.isClosed()) {
                int classId = resultSet.getInt("id_codecool_class");
                String name = resultSet.getString("name");
                ArrayList<Student> students = new DaoStudent().getCodecoolClassStudents(classId);

                codecoolClass = createClass(classId, name, students);
                allCodecoolClasses.add(codecoolClass);

                resultSet.close();
            }
            preparedStatement.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("No Classes");
        }
        return allCodecoolClasses;
    }

    public void assignMentorToClass(Integer mentorId, Integer classId){
        //insert
    }

    public void updateMentorInClass(Integer mentorId, Integer classId){
        //update
    }

    public void unsignMentorFromClass(Integer mentorId){
        //remove
    }

}
