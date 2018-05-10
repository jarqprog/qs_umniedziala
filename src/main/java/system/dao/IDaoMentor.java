package system.dao;

import system.model.Mentor;

import java.util.List;

public interface IDaoMentor {

    Mentor createMentor(String name, String password, String email);

    Mentor importMentor(int mentorId);

    boolean updateMentor(Mentor mentor);
    
    int getMentorClassId(Mentor mentor);

    List<Mentor> getAllMentors();
}
