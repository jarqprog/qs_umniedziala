package dao;

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

}
