package server.webcontrollers;

public interface IStudentController {

    int getWallet(int studentId);
    String[] getExpLevel(int studentId);
}
