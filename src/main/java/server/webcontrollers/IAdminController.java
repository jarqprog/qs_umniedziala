package server.webcontrollers;

public interface IAdminController {

    boolean createMentor(String name, String password, String email);
    boolean createClass(String name);
    boolean editMentor(String id);
    String getCodecoolClass(String name);
    String seeMentorData(String mentor);
    boolean createLevel(String name, String coinsLimit);
}