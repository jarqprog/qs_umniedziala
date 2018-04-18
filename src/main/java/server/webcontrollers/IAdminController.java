package server.webcontrollers;

import java.util.List;

public interface IAdminController {

    String getAdmin(int adminId);
    boolean createMentor(String name, String password, String email);
    boolean createClass(String name);
    boolean editMentor(String id);
    String getCodecoolClass(String name);
    String seeMentorData(String mentor);
    boolean createLevel(String name, String coinsLimit);
    List<String> getMentorsNames();
    List<String> getMentorsFullData();
}
