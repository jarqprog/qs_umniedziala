package server.webcontrollers;

import com.sun.net.httpserver.HttpExchange;
import model.Quest;

import java.util.List;
import java.util.Map;

public interface IMentorController {

    String getMentorName(int mentorId);
    String getMentorEmail(int mentorId);
    String getMentorClassWithStudents(int mentorId);
    String getClassNames();
    boolean createStudent(String name, String password, String email, int classId);
    boolean createTeam(String teamName);
    List<String> getQuests();
    boolean editQuest(Map<String, String> inputs);
}
