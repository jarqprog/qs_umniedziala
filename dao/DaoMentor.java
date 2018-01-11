package dao;

import model.*;
import java.util.ArrayList;


public class DaoMentor implements IDaoMentor{

    private static ArrayList <Mentor> mentors = new ArrayList<>();

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

    public void exportData(ArrayList <Mentor> updatedMentors){
        mentors = updatedMentors;
    }

    public ArrayList <Mentor> importData(){
        return mentors;
    }

}
