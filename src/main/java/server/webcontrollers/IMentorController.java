package server.webcontrollers;

import model.Quest;

import java.util.List;

public interface IMentorController {


    String getMentorName(int mentorId);
    String getMentorEmail(int mentorId);
    String getMentorClassWithStudents(int mentorId);
    String getClassNames();
    boolean createStudent(String name, String password, String email, int classId);
    boolean createTeam(String teamName);
    List<String> getQuests();
}
