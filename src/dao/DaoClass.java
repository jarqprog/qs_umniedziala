package dao;

import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.CodecoolClass;

public class DaoClass{

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
