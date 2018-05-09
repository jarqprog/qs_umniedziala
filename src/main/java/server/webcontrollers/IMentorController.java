package server.webcontrollers;

import java.util.List;
import java.util.Map;

public interface IMentorController extends IServerController {

    String getMentorName(int mentorId);
    String getMentorEmail(int mentorId);
    String getMentorClassWithStudents(int mentorId);
    String getClassNames();
    boolean createStudent(String name, String password, String email, int classId);
    boolean createTeam(String teamName);
    List<String> getQuests();
    boolean editQuest(Map<String, String> inputs);
    boolean addQuest(String name, int value, String description, String type, String category);
    boolean addArtifact(String name, int value, String type, String caetgory);
    Map<String, String> getAllWallets();
    List<String> getArtifacts();
    boolean editArtifact(Map<String, String> inputs);
}
