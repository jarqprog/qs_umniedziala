package dao;

import model.Mentor;

import java.util.List;

public interface IDaoMentor {

    Mentor createMentor(String name, String password, String email);

    Mentor importMentor(int mentorId);

    boolean updateMentor(Mentor mentor);
    
    Integer getMentorClassId(Mentor mentor);

    List<Mentor> getAllMentors();
}
