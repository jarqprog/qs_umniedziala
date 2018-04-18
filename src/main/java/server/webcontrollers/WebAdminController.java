package server.webcontrollers;

import dao.DaoClass;
import dao.DaoLevel;
import dao.DaoMentor;
import model.CodecoolClass;
import model.Level;
import model.Mentor;

public class WebAdminController implements IAdminController {

    private DaoMentor daoMentor;
    private DaoClass daoClass;
    private DaoLevel daoLevel;

    public WebAdminController() {
        daoMentor = new DaoMentor();
        daoClass = new DaoClass();
        daoLevel = new DaoLevel();
    }

    public boolean createMentor(String name, String password, String email) {
        Mentor mentor = daoMentor.createMentor(name, password, email);
        return daoMentor.exportMentor(mentor);
    }


    public boolean createClass(String name) {
        CodecoolClass codecoolClass = daoClass.createClass(name);
        return daoClass.exportClass(codecoolClass);
    }


    public boolean editMentor(String id) {
        Mentor mentor = daoMentor.importMentor(Integer.parseInt(id));
        return daoMentor.updateMentor(mentor);
    }


    public String getCodecoolClass(String name) {
        for (CodecoolClass codecoolClass : daoClass.getAllClasses()) {
            if (codecoolClass.getName().equals(name)) {
                return codecoolClass.getName();
            }
        }
        return null;
    }


    public String seeMentorData(String name) {
        for (Mentor mentor : daoMentor.getAllMentors()) {
            if(mentor.getName().equals(name)) {
                return mentor.getName();
            }
        }
        return null;
    }


    public boolean createLevel(String name, String coinsLimit) {
        Level level = daoLevel.createLevel(name, Integer.parseInt(coinsLimit));
        return daoLevel.exportLevel(level);
    }
}
