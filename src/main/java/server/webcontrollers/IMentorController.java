package server.webcontrollers;

import system.model.Student;

import java.util.List;
import java.util.Map;

public interface IMentorController extends IServerController {

    String getMentorName(int mentorId);
    String getMentorEmail(int mentorId);
    String getMentorClassWithStudents(int mentorId);
    String getAllTeams();
    List<String> getAllTeamsCollection();
    String getAllClasses();
    List<String> getAllClassCollection();
    boolean createStudent(String name, String password, String email, String codeCoolClass);
    boolean createTeam(String teamName);
    List<String> getQuests();
    boolean editQuest(Map<String, String> inputs);
    boolean addQuest(String name, int value, String description, String type, String category);
    boolean addArtifact(String name, int value, String type, String category);
    Map<String, String> getAllWallets();
    List<String> getArtifacts();
    boolean editArtifact(Map<String, String> inputs);
    Map<Integer, String> getStudentsWithIds();
    Student getStudentById(int studentId); // TODO: 10.05.18 check if using if not deleting
    List<String> getStudentsByMentorId(int mentorId);
    boolean assignStudentToTeam(String studentData, String teamData);

}
