package server.webcontrollers;

import java.util.List;

public interface IAdminController {

    String getAdminName(int adminId);
    boolean createMentor(String name, String password, String email);
    boolean createClass(String name);
    boolean editMentor(String id);
    String getCodecoolClass(String name);
    String seeMentorData(String mentor);
    boolean createLevel(String name, int coinsLimit);
    List<String> getMentorsNames();
    List<String> getMentorsFullData();
    String getAdminEmail(int adminId);
    List<String> getAllLevels();
}
