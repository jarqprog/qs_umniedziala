package dao;

import model.*;
import java.util.ArrayList;


public class DaoMentor implements IDaoMentor{
    
    public void createMentor(String name, String password, String email){
        mentors.add(new Mentor(name, password, email));
        
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
