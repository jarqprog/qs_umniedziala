package dao;

import model.*;
import java.util.ArrayList;


public class DaoMentor implements IDaoMentor{

    private static ArrayList <Mentor> mentors;

    public void implementTestData() {
        createMentor("Dominik", "haslo", "dominik@mail.pl");
        createMentor("Anna", "haslo", "anna@mail.pl");
    }


    public void createMentor(String name, String password, String email){
        mentors.add(new Mentor(name, password, email));
        
    }

    public Mentor getMentorById(int id){
        for(Mentor mentor: mentors){
            if(mentor.getUserId() == id){
                return mentor;
            }
        }
        return null;
    }

    public void exportData(ArrayList <Mentor> updatedMenotrs){
        mentors = updatedMenotrs;
    }

    public ArrayList <Mentor> importData(Mentor mentor){
        return mentors;
    }

}
