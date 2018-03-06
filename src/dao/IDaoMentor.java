package dao;

import model.Mentor;

import java.util.ArrayList;

public interface IDaoMentor {
    Mentor createInstance(String name, String password, String email);

    Mentor createInstance(int userId, String name, String password, String email);

    Mentor importInstance(int mentorId);

    boolean exportInstance(Mentor mentor);

    boolean updateInstance(Mentor mentor);

    int getRoleID(String roleName);

    Integer getMentorClassId(Mentor mentor);

    ArrayList<Mentor> getAllMentors();
}
