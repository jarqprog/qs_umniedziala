package server;

import model.Mentor;
import org.jtwig.JtwigModel;

public class MentorPage {

    public static void setModel(Mentor mentor, JtwigModel model){
        model.with("name", mentor.getName());
        model.with("email", mentor.getEmail());
    }

}
