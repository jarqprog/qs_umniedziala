package server.webcontrollers;

public interface IMentorController {


    String getMentorName(int mentorId);
    String getMentorEmail(int mentorId);
    String getMentorClassWithStudents(int mentorId);
    boolean addQuest(String name, int value, String description, String type, String category);
}
