package server.webcontrollers;

import java.util.Map;

public interface IMentorController {


    String getMentorName(int mentorId);
    String getMentorEmail(int mentorId);
    String getMentorClassWithStudents(int mentorId);
    boolean addQuest(String name, int value, String description, String type, String category);
    boolean addArtifact(String name, int value, String type, String caetgory);
    Map<String, String> getAllWallets();
}
