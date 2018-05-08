package server.webcontrollers;

public interface IMentorController extends IServerController {


    String getMentorName(int mentorId);
    String getMentorEmail(int mentorId);
    String getMentorClassWithStudents(int mentorId);
}
