package dao;

import model.*;
import java.util.ArrayList;


public class DaoMentor implements IDaoMentor{

    private static ArrayList <Mentor> mentors;

    public ArrayList <Mentor> getMentors() { return mentors; }

    public void DaoMentor(){
        Mentor m1 = new Mentor("Jan", "haslo", "jan@mail.pl");
        Mentor m2 = new Mentor("Anna", "haslo", "anna@mail.pl");
        mentors.add(m1);
        mentors.add(m2);
    }

    public Mentor createMentor(String name, String password, String email){
        Mentor mentor = new Mentor(name, password, email);
        return mentor;
    }

    public Mentor getMentorById(int id){
        for(Mentor mentor: mentors){
            if(mentor.getUserId() == id){
                return mentor;
            }
        }
        return null;
    }

    public void exportData(){}

    public void importData(Mentor mentor){
        mentors.add(mentor);
    }

}
