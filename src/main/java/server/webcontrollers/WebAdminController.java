package server.webcontrollers;

import dao.*;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WebAdminController implements IAdminController {

    private IDaoAdmin daoAdmin;
    private IDaoMentor daoMentor;
    private IDaoClass daoClass;
    private IDaoLevel daoLevel;

    public static IAdminController create(IDaoAdmin daoAdmin, IDaoMentor daoMentor,
                                          IDaoClass daoClass, IDaoLevel daoLevel) {
        return new WebAdminController(daoAdmin, daoMentor, daoClass, daoLevel);
    }

    private WebAdminController(IDaoAdmin daoAdmin, IDaoMentor daoMentor,
                               IDaoClass daoClass, IDaoLevel daoLevel) {
        this.daoAdmin = daoAdmin;
        this.daoMentor = daoMentor;
        this.daoClass = daoClass;
        this.daoLevel = daoLevel;
    }


    @Override
    public String getAdminName(int adminId) {
        Admin admin = daoAdmin.importAdmin(adminId);
        if(admin != null) {
            return admin.getName();
        }
        return "";
    }

    @Override
    public String getAdminEmail(int adminId) {
        Admin admin = daoAdmin.importAdmin(adminId);
        if(admin == null) {
            return "";
        }
        return admin.getEmail();
    }

    @Override
    public List<String> getAllLevels() {
        List<String> levels = daoLevel.getAllLevels().stream().map(Level::toString).collect(Collectors.toList());
        if(levels == null) {
            return new ArrayList<>();
        }
        return levels;
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
        return "";
    }


    public String seeMentorData(String name) {
        for (Mentor mentor : daoMentor.getAllMentors()) {
            if(mentor.getName().equals(name)) {
                return mentor.toString();
            }
        }
        return "";
    }


    public boolean createLevel(String name, int coinsLimit) {
        Level level = daoLevel.createLevel(name, coinsLimit);
        return daoLevel.exportLevel(level);
    }

    @Override
    public List<String> getMentorsNames() {
        return daoMentor.getAllMentors().stream().map(User::getName).collect(Collectors.toList());
    }

    @Override
    public List<String> getMentorsFullData() {
        return daoMentor.getAllMentors().stream().map(User::toString).collect(Collectors.toList());
    }
}
