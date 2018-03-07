package dao;

import model.Mentor;

import java.util.ArrayList;

public interface IDaoMentor {
    Mentor createMentor(String name, String password, String email);

    Mentor createMentor(int userId, String name, String password, String email);

    Mentor importMentor(int mentorId);

    boolean exportMentor(Mentor mentor);

    boolean updateMentor(Mentor mentor);
    
    Integer getMentorClassId(Mentor mentor);

    ArrayList<Mentor> getAllMentors();
}
