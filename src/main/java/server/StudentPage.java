package server;

import model.Student;
import org.jtwig.JtwigModel;

public class StudentPage {

    public static void setModel(Student student, JtwigModel model){
        model.with("name", student.getName());
        model.with("email", student.getEmail());
        model.with("money", student.getWallet().getAvailableCoins());
    }

}
