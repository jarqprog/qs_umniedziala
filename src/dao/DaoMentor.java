package dao;

import model.*;
import java.util.ArrayList;

public class DaoMentor extends Dao {

    public Mentor createMentor(String name, String password, String email) {
        return new Mentor(name, password, email);
    }

    public Mentor createMentor(int userId, String name, String password, String email) {
        return new Mentor(userId, name, password, email);
    }

    public Mentor getMentorById(int id) {
        for(Mentor mentor: mentors){
            if(mentor.getUserId() == id) {
                return mentor;
            }
        }
        return null;
    }

    public void exportData(ArrayList <Mentor> updatedMentors) {
        mentors = updatedMentors;
    }

    public ArrayList <Mentor> importData() {
        return mentors;
    }

}
