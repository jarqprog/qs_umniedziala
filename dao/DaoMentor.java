package dao;

import model.*;
import java.util.ArrayList;


public class DaoMentor implements IDaoMentor{

    private static ArrayList <Mentor> mentors = new ArrayList<>();

    public void implementTestData() {
        createMentor("Dominik", "haslo", "dominik@mail.pl", 12, 1);
        createMentor("Anna", "haslo", "anna@mail.pl", 13, 2);
    }


    public void createMentor(String name, String password, String email, int userId, int classId){
        mentors.add(new Mentor(name, password, email, userId, classId));
        
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
