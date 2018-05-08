package server.webcontrollers;

import dao.*;
import model.CodecoolClass;
import model.Mentor;

public class WebMentorController implements IMentorController {

    private final IDaoWallet daoWallet;
    private final IDaoStudent daoStudent;
    private final IDaoArtifact daoArtifact;
    private final IDaoLevel daoLevel;
    private final IDaoTeam daoTeam;
    private final IDaoClass daoClass;
    private final IDaoQuest daoQuest;
    private final IDaoMentor daoMentor;

    public static IMentorController create(IDaoWallet daoWallet, IDaoStudent daoStudent,
                                            IDaoArtifact daoArtifact, IDaoLevel daoLevel,
                                            IDaoTeam daoTeam, IDaoClass daoClass,
                                            IDaoQuest daoQuest, IDaoMentor daoMentor) {
        return new WebMentorController(daoWallet, daoStudent, daoArtifact,
                daoLevel, daoTeam, daoClass, daoQuest, daoMentor);
    }

    private WebMentorController(
                             IDaoWallet daoWallet, IDaoStudent daoStudent,
                             IDaoArtifact daoArtifact, IDaoLevel daoLevel,
                             IDaoTeam daoTeam, IDaoClass daoClass,
                             IDaoQuest daoQuest, IDaoMentor daoMentor) {

        this.daoWallet = daoWallet;
        this.daoStudent = daoStudent;
        this.daoArtifact = daoArtifact;
        this.daoLevel = daoLevel;
        this.daoTeam = daoTeam;
        this.daoClass = daoClass;
        this.daoQuest = daoQuest;
        this.daoMentor = daoMentor;
    }

    @Override
    public String getMentorName(int mentorId) {
        Mentor mentor = getMentorById(mentorId);
        if(mentor == null) {
            return "";
        }
        return mentor.getName();
    }

    @Override
    public String getMentorEmail(int mentorId) {
        Mentor mentor = getMentorById(mentorId);
        if(mentor == null) {
            return "";
        }
        return mentor.getEmail();
    }

    @Override
    public String getMentorClassWithStudents(int mentorId) {
        CodecoolClass codecoolClass = daoClass.getMentorsClass(mentorId);
        if(codecoolClass.getGroupId() == 0) {
            return "no class assigned yet..";
        }
        String[] classData = codecoolClass.toString().split("ID:");
        int counter = 0;
        StringBuilder sb = new StringBuilder(codecoolClass.getBasicInfo());
        sb.append(", students:<br>");
        for(String element : classData) {
            if(counter > 0) {
                sb.append("<br>###   id: ");
                sb.append(element);
            }
            counter++;
        }
        return sb.toString();
    }

    private Mentor getMentorById(int mentorId) {
        return daoMentor.importMentor(mentorId);
    }

}





