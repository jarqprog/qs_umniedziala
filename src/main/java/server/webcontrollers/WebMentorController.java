package server.webcontrollers;


import system.dao.*;
import system.model.*;

import java.util.*;
import java.util.stream.Collectors;


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
        if(mentor.getUserId() == 0) {
            return "";
        }
        return mentor.getName();
    }

    @Override
    public String getMentorEmail(int mentorId) {
        Mentor mentor = getMentorById(mentorId);
        if(mentor.getUserId() == 0) {
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

    @Override
    public boolean createStudent(String name, String password, String email, String codeCoolClass) {
        int studentId = daoStudent.createStudent(name, password, email).getUserId();
        if(studentId == 0) {
            return false;
        }
        int classId = gatherIdFromStringData(codeCoolClass);
        return classId != 0 & daoClass.assignStudentToClass(studentId, classId);
    }

    public List<String> getQuests() {
        List<String> quests = new ArrayList<>();
        for(Quest quest : daoQuest.getAllQuests()) {
            quests.add(quest.getFullInfo());
        }
        return quests;
    }

    @Override
    public boolean editQuest(Map<String, String> questData) {
        Quest quest = daoQuest.importQuest(Integer.parseInt(questData.get("quest-id")));
        if(quest.getItemId() != 0) {
            if(questData.containsKey("questname")) {
                quest.setName(questData.get("questname"));
            }
            if(questData.containsKey("description")) {
                quest.setDescription(questData.get("description"));
            }
            if(questData.containsKey("value")) {
                quest.setValue(Integer.parseInt(questData.get("value")));
            }
            if(questData.containsKey("type")) {
                quest.setType(questData.get("type"));
            }
            if(questData.containsKey("category")) {
                quest.setCategory(questData.get("category"));
            }

            return daoQuest.updateQuest(quest);
        } else{
            return false;
        }
    }

    @Override
    public boolean createTeam(String teamName) {

        return daoTeam.createTeam(teamName).getGroupId() != 0;
    }

    private Mentor getMentorById(int mentorId) {
        return daoMentor.importMentor(mentorId);
    }

    @Override
    public boolean addQuest(String name, int value, String description, String type, String category) {

        return daoQuest.createQuest(name, value, description, type, category).getItemId() != 0;
    }

    @Override
    public boolean addArtifact(String name, int value, String type, String category) {

        return daoArtifact.createArtifact(name, value, type, category).getItemId() != 0;
    }

    @Override
    public Map<String, String> getAllWallets() {
        List<Student> students = daoStudent.getAllStudents();
        Map<String, String> dataFromWallets = new HashMap<>();

        for (Student student : students) {
            dataFromWallets.put(student.getName(), daoWallet.importWallet(student.getUserId()).toString().replaceAll("\n", "<br/>"));
        }

        return dataFromWallets;
    }

    @Override
    public List<String> getArtifacts() {
        List<String> artifacts = new ArrayList<>();
        for(Artifact artifact : daoArtifact.getAllArtifacts()) {
            artifacts.add(artifact.toString());
        }
        return artifacts;
    }

    @Override
    public boolean editArtifact(Map<String, String> artifactData) {
        Artifact artifact = daoArtifact.importArtifact(Integer.parseInt(artifactData.get("artifact_id")));
        if(artifact.getItemId() != 0) {
            if(artifactData.containsKey("name")) {
                artifact.setName(artifactData.get("name"));
            }
            if(artifactData.containsKey("description")) {
                artifact.setDescription(artifactData.get("description"));
            }
            if(artifactData.containsKey("value")) {
                artifact.setValue(Integer.parseInt(artifactData.get("value")));
            }
            if(artifactData.containsKey("type")) {
                artifact.setType(artifactData.get("type"));
            }

            return daoArtifact.updateArtifact(artifact);
        } else{
            return false;
        }
    }

    @Override
    public List<String> getStudentsByMentorId(int mentorId) {
        int classId = daoClass.getMentorsClass(mentorId).getGroupId();
        return daoClass.getStudentsOfClass(classId).stream()
                .map(s -> String.format("#%s %s", s.getUserId(), s.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllTeamsCollection() {
        return daoTeam.getAllTeams().stream()
                .map(t -> String.format("#%s %s", t.getGroupId(), t.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllClassCollection() {
        return daoClass.getAllClasses().stream()
                .map(t -> String.format("#%s %s", t.getGroupId(), t.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getAllClasses() {
        return transformGroupsToTxt(daoClass.getAllClasses());
    }

    @Override
    public String getAllTeams() {
        return transformGroupsToTxt(daoTeam.getAllTeams());
    }

    @Override
    public boolean assignStudentToTeam(String studentData, String teamData) {
        try {
            int studentId = gatherIdFromStringData(studentData);
            int teamId = gatherIdFromStringData(teamData);
            return daoTeam.assignStudentToTeam(studentId, teamId);

        } catch (NumberFormatException | IndexOutOfBoundsException ex){
            ex.printStackTrace();
            return false;
        }
    }

    private String transformGroupsToTxt(List<? extends Group> groups) {
        groups.sort(Comparator.comparing(Group::getGroupId));
        StringBuilder groupsAsText = new StringBuilder();
        int counter = 1;
        int maxNumGroupsInLine = 7;
        for(Group group: groups) {
            int groupId = group.getGroupId();
            if(groupId != 0) {
                if(counter % maxNumGroupsInLine == 1) {
                    groupsAsText.append("<br>");
                }
                String teamAsText = String.format(" id(%s) %s; ", groupId, group.getName());
                groupsAsText.append(teamAsText);
                counter++;
            }
        }
        return groupsAsText.toString();
    }

    private int gatherIdFromStringData(String data) {
        try {
            int idIndex = 0;
            return Integer.parseInt(data.replace("#", "").split(" ")[idIndex]);
        } catch (NumberFormatException | IndexOutOfBoundsException ex){
            ex.printStackTrace();
            return 0;
        }
    }
}





