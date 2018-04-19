package server.webcontrollers;

public interface IMentorController {


    String getMentorName(int mentorId);
    String getMentorEmail(int mentorId);
    String getMentorClassWithStudents(int mentorId);
}
