package dao;

import model.Mentor;

import java.util.List;

public interface IDaoMentor {
    Mentor createMentor(String name, String password, String email);

    Mentor createMentor(int userId, String name, String password, String email);

    Mentor importMentor(int mentorId);

    boolean exportMentor(Mentor mentor);

    boolean updateMentor(Mentor mentor);
    
    Integer getMentorClassId(Mentor mentor);

    List<Mentor> getAllMentors();
}
