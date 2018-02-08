package controller;

import com.sun.org.apache.bcel.internal.classfile.Code;
import view.ViewMentor;
import dao.*;
import model.*;

import java.util.ArrayList;

public class ControllerMentor implements IUserController{
    private ViewMentor viewMentor;
    private Mentor mentor;

    public ControllerMentor(Mentor mentor){
        this.viewMentor = new ViewMentor();
        this.mentor = mentor;
    }

    public void createStudent() {
        
        DaoStudent daoStudent = new DaoStudent();
        String studentName = viewMentor.getInputFromUser("Enter name of new student: ");
        String studentPassword = viewMentor.getInputFromUser("Enter password of new student: ");
        String studentEmail = viewMentor.getInputFromUser( "Enter email of new student: ");

        Student student = daoStudent.createInstance(studentName, studentPassword, studentEmail);
        boolean isInsert = daoStudent.exportInstance(student);

        if(isInsert) {
            Student studentWithId = daoStudent.importNewStudent(studentEmail);

            DaoWallet daoWallet = new DaoWallet();
            Wallet wallet = daoWallet.createWallet();
            studentWithId.setWallet(wallet);
            daoWallet.exportWallet(studentWithId);

            CodecoolClass codecoolClass = getCodecoolClass();
            new DaoClass().assignStudentToClass(studentWithId.getUserId(), codecoolClass.getGroupId());
        }
        else{
            viewMentor.displayText("Creation student failed");
        }

    }

    public void createTeam() {
        DaoTeam daoTeam = new DaoTeam();
        String teamName = viewMentor.getInputFromUser("Enter name of new team: ");

        Team team = daoTeam.createTeam(teamName);
        if(daoTeam.exportTeam(team)){
            viewMentor.displayText("Creation team successful");
        }else{
            viewMentor.displayText("Creation team failed");
        }
    }

    public Student getStudent(){
        DaoStudent daoStudent = new DaoStudent();
        Student student = null;

        viewMentor.displayText("Available students:\n");
        ArrayList<Student> allStudents = daoStudent.getAllStudents();
        if(allStudents.size() != 0) {
            viewMentor.displayList(allStudents);
            Integer studentId = viewMentor.getIntInputFromUser("\nEnter id of student: ");
            student = daoStudent.importInstance(studentId);
        }else{
            viewMentor.displayText("No students");
        }

        return student;
    }

    public CodecoolClass getCodecoolClass(){
        DaoClass daoClass = new DaoClass();
        CodecoolClass chosenClass = null;

        viewMentor.displayText("Available classes: ");
        ArrayList <CodecoolClass> allClasses = daoClass.getAllClasses();
        if(allClasses.size() != 0) {
            for (CodecoolClass codecoolClass : allClasses) {
                viewMentor.displayText(codecoolClass.getBasicInfo());
            }

            Integer classId = viewMentor.getIntInputFromUser("\nEnter id of chosen class: ");
            chosenClass = daoClass.importClass(classId);
        }else{
            viewMentor.displayText("No classes");
        }
        return chosenClass;
    }

    public Team getTeam(){
        DaoTeam daoTeam = new DaoTeam();
        Team chosenTeam = null;

        viewMentor.displayText("Available teams:\n");
        ArrayList<Team> teams = daoTeam.getAllTeams();
        if(teams.size() != 0) {
            for (Team team : teams) {
                viewMentor.displayText(team.getBasicInfo());
            }

            int teamId = viewMentor.getIntInputFromUser("\nEnter id of team: ");
            chosenTeam = daoTeam.importTeam(teamId);
        }else {
            viewMentor.displayText("No teams");
        }

        return chosenTeam;
    }

    public Quest getTeamQuest() {
        DaoQuest daoQuest = new DaoQuest();
        Quest chosenQuest = null;

        viewMentor.displayText("Available team quests:\n");
        ArrayList<Quest> allQuests = daoQuest.getTeamQuests();

        if(allQuests.size() != 0) {
            viewMentor.displayList(allQuests);
            Integer questId = viewMentor.getIntInputFromUser("Enter id of chosen quest");
            chosenQuest = daoQuest.importQuest(questId);
        }else {
            viewMentor.displayText("No team quests");
        }
        return chosenQuest;
    }

    public Quest getIndividualQuest(){
        DaoQuest daoQuest = new DaoQuest();
        Quest chosenQuest = null;

        ArrayList<Quest> allQuests = daoQuest.getIndividualQuests();
        viewMentor.displayText("Available individual quests:\n");

        if(allQuests.size() == 0) {
            viewMentor.displayList(allQuests);
            Integer questId = viewMentor.getIntInputFromUser("Enter id of chosen quest");
            chosenQuest = daoQuest.importQuest(questId);
        }else {
            viewMentor.displayText("No individual quests");
        }
        return chosenQuest;
    }

    public void addQuest(){

        DaoQuest daoQuest = new DaoQuest();

        String questName = viewMentor.getInputFromUser("Enter name of new quest: ");
        int questValue = viewMentor.getIntInputFromUser( "Enter value of new quest: ");
        String questDescription = viewMentor.getInputFromUser("Enter description of quest");
        String questType = chooseType();
        String questCategory = chooseCategory();

        Quest quest = daoQuest.createQuest(questName, questValue, questDescription, questType, questCategory);
        boolean isInsert = daoQuest.exportQuest(quest);

        if(isInsert){
            viewMentor.displayText("Creation quest successful");
        }else {
            viewMentor.displayText("Creation quest failed");
        }
    }

    public void addArtifact() {
        DaoArtifact daoArtifact = new DaoArtifact();

        String artifactName = viewMentor.getInputFromUser("Enter name of new artifact: ");
        int artifactValue = viewMentor.getIntInputFromUser("Enter value of new artifact: ");
        String artifactDescription = viewMentor.getInputFromUser("Enter description of new artifact");
        String artifactStatus = chooseType();

        Artifact artifact = daoArtifact.createArtifact(artifactName, artifactValue, artifactDescription, artifactStatus);

        if(daoArtifact.exportArtifact(artifact)){
            viewMentor.displayText("Creation artifact successful");
        }else {
            viewMentor.displayText("Creation artifact failed");
        }
    }

    private String chooseType() {

        String statusRequest = "Choose type:\n1. Individual\n2. Team\nOption: ";
        String status = null;
        boolean choosingStatus = true;
        int option;
        while(choosingStatus) {
            option = viewMentor.getIntInputFromUser(statusRequest);
            switch(option) {
                case 1:
                    status = "individual";
                    choosingStatus = false;
                    break;
                case 2:
                    status = "team";
                    choosingStatus = false;
                    break;
                default:
                    viewMentor.displayText("Wrong option number!");
            }
        }
        return status;
    }

    private String chooseCategory() {
        String typeRequest = "Choose category:\n1. Basic\n2. Extra\nOption: ";
        String type = null;
        boolean choosingType = true;
        int option ;
        while(choosingType) {
            option = viewMentor.getIntInputFromUser(typeRequest);
            switch(option) {
                case 1:
                    type = "basic";
                    choosingType = false;
                    break;
                case 2:
                    type = "extra";
                    choosingType = false;
                    break;
                default:
                    viewMentor.displayText("Wrong option number!");
            }
        }
        return type;
    }

    public Quest getQuest(){
        DaoQuest daoQuest = new DaoQuest();
        Quest chosenQuest = null;

        viewMentor.displayText("Available quests:\n");
        ArrayList<Quest> allQuests = daoQuest.getAllQuests();

        if(allQuests.size() != 0) {
            viewMentor.displayList(allQuests);
            int questId = viewMentor.getIntInputFromUser("\nEnter id of quest: ");
            chosenQuest = daoQuest.importQuest(questId);
        }else {
            viewMentor.displayText("No quests");
        }
        return chosenQuest;
    }

    public void updateQuest(){
        Quest quest = getQuest();
        if(quest == null){
            return;
        }

        boolean toContinue = true;
        do{
            viewMentor.displayList(viewMentor.getEditQuestOptions());
            String chosenOption = viewMentor.getInputFromUser("Choose option: ");
            switch(chosenOption){
                case "1": updateQuestName(quest);
                    break;
                case "2": updateQuestDescription(quest);
                    break;
                case "3": updateQuestValue(quest);
                    break;
                case "4": updateQuestType(quest);
                    break;
                case "5": updateQuestCategory(quest);
                    break;
                case "0": toContinue = false;
                    break;
                default: viewMentor.displayText("Wrong option. Try again!");
                    break;
            }
        }while(toContinue);
    }

    public void updateQuestName(Quest quest){
        String name = viewMentor.getInputFromUser("Pass new quest name: ");
        quest.setName(name);
        boolean isUpdate = new DaoQuest().updateQuest(quest);
        updateInfo(isUpdate);
    }

    public void updateQuestDescription(Quest quest){
        String description = viewMentor.getInputFromUser("Pass new quest description: ");
        quest.setDescription(description);
        boolean isUpdate = new DaoQuest().updateQuest(quest);
        updateInfo(isUpdate);
    }

    public void updateQuestValue(Quest quest){
        Integer value = viewMentor.getIntInputFromUser("Pass new quest value: ");
        quest.setValue(value);
        boolean isUpdate = new DaoQuest().updateQuest(quest);
        updateInfo(isUpdate);
    }

    public void updateQuestType(Quest quest){
        String type = chooseType();
        quest.setType(type);
        boolean isUpdate = new DaoQuest().updateQuest(quest);
        updateInfo(isUpdate);
    }

    public void updateQuestCategory(Quest quest){
        String category = chooseCategory();
        quest.setCategory(category);
        boolean isUpdate = new DaoQuest().updateQuest(quest);
        updateInfo(isUpdate);
    }

    public void updateInfo(boolean isInsert){
        if(isInsert){
            viewMentor.displayText("Update was succesful");
        }else {
            viewMentor.displayText("Update failed");
        }
    }

    public void updateArtifact() {
        Artifact artifact = getArtifact();
        if(artifact == null){
            return;
        }

        boolean toContinue = true;
        do{
            viewMentor.displayList(viewMentor.getUpdateArtifactsOptions());
            String chosenOption = viewMentor.getInputFromUser("Choose option: ");
            switch(chosenOption){
                case "1": updateArtifactName(artifact);
                    break;
                case "2": updateArtifactDescription(artifact);
                    break;
                case "3": updateArtifactValue(artifact);
                    break;
                case "4": updateArtifactType(artifact);
                    break;
                case "0": toContinue = false;
                    break;
                default: viewMentor.displayText("Wrong option. Try again!");
                    break;
            }
        }while(toContinue);

    }

    public void updateArtifactName(Artifact artifact){
        String name = viewMentor.getInputFromUser("Choose new name: ");
        artifact.setName(name);
        boolean isUpdate = new DaoArtifact().updateArtifact(artifact);
        updateInfo(isUpdate);
    }
    public void updateArtifactValue(Artifact artifact){
        Integer value = viewMentor.getIntInputFromUser("Choose new value: ");
        artifact.setValue(value);
        boolean isUpdate = new DaoArtifact().updateArtifact(artifact);
        updateInfo(isUpdate);
    }

    public void updateArtifactDescription(Artifact artifact){
        String description = viewMentor.getInputFromUser("Choose new description: ");
        artifact.setDescription(description);
        boolean isUpdate = new DaoArtifact().updateArtifact(artifact);
        updateInfo(isUpdate);
    }
    public void updateArtifactType(Artifact artifact){
        String type = null;
        boolean toContinue = true;
        do{
            viewMentor.displayList(viewMentor.getUpdateArtifactTypeOptions());
            String userChoice = viewMentor.getInputFromUser("Choose type: ");
            switch (userChoice){
                case "1": type = "individual";
                    toContinue = false;
                    break;
                case "2": type = "team";
                    toContinue = false;
                    break;
                case "0": toContinue = false;
                    break;
                default: viewMentor.displayText("Wrong option. Try again!");
            }
        }while (toContinue);

        if(type != null){
            artifact.setType(type);
            boolean isUpdate = new DaoArtifact().updateArtifact(artifact);
            updateInfo(isUpdate);
        }
    }

    private boolean seeAllArtifacts() {

        DaoArtifact daoArtifact = new DaoArtifact();
        ArrayList<Artifact> artifactList = daoArtifact.getAllArtifacts();
        boolean isListNotEmpty = false;

        viewMentor.displayText("List of artifacts:");
        if(artifactList.size() != 0) {
            viewMentor.displayList(artifactList);
            isListNotEmpty = true;
        }else {
            viewMentor.displayText("No artifacts");
        }
        return isListNotEmpty;
    }

    private Artifact getArtifact() {
        Artifact artifact = null;
        if (seeAllArtifacts()) {
            int artifactId = viewMentor.getIntInputFromUser("\nEnter id of artifact: ");
            artifact = new DaoArtifact().importArtifact(artifactId);
        }
        return artifact;
    }


    public void markQuest() {
        String mentorOption = "";
        while (!mentorOption.equals("0")) {

            viewMentor.displayText("\nWhat would like to do?");
            viewMentor.displayList(viewMentor.getChooseTeamOrStudent());

            mentorOption = viewMentor.getInputFromUser("Option: ");
            switch (mentorOption) {
                case "1": markTeamAchivedQuest();
                    break;
                case "2":markStudentAchivedQuest();
                    break;
                case "0": break;
                default: viewMentor.displayText("Wrong option. Try again!");
                    break;
            }
        }
    }


    public void markBoughtArtifact(){
        Student student = getStudent();
        if(student == null){
            return;
        }

        viewMentor.displayText("Student new artifacts:\n");
        ArrayList<Artifact> allNewArtifacts = student.getAllNewArtifacts();
        Artifact artifactToBeBougth = null;

        if(allNewArtifacts.size() != 0) {
            viewMentor.displayList(allNewArtifacts);
            Integer artifactId = viewMentor.getIntInputFromUser("Choose id artifact to be marked as used: ");

            artifactToBeBougth = new DaoArtifact().importArtifact(artifactId);
            student.markArtifactAsBougth(artifactToBeBougth);

            new DaoWallet().updateStudentsArtifact(artifactToBeBougth.getItemId(), student.getUserId());
        }
    }

    public void markStudentAchivedQuest() {
        Student student = getStudent();
        if(student == null){
            return;
        }

        Quest quest = getIndividualQuest();
        if(quest == null){
            return;
        }

        int coins = quest.getValue();
        student.addCoins(coins);
        new DaoWallet().updateWallet(student);

    }

    public void markTeamAchivedQuest() {
        Team team = getTeam();
        if(team == null){
            return;
        }

        Quest quest = getTeamQuest();
        if(quest == null){
            return;
        }

        int coins = quest.getValue();
        team.addCoins(coins);
        new DaoTeam().updateTeamData(team);

    }

    public void seeAllWallets() {
        CodecoolClass mentorsClass = new DaoClass().getMentorsClass(mentor.getUserId());
        if(mentorsClass == null){
            viewMentor.displayText("Mentor is not assigned to any class");
            return;
        }

        viewMentor.displayText("Wallets of students of class: \n");
        for (Student student : mentorsClass.getStudents()) {
            Wallet wallet = student.getWallet();
            viewMentor.displayText(student.toString());
            viewMentor.displayText(wallet.toString());
        }
    }

    public void assignStudentsToTeam(){
        Team team = getTeam();
        if(team == null){
            return;
        }

        boolean toContinue = true;
        do{
            viewMentor.displayList(viewMentor.getAssignStudentToTeamOptions());
            String chosenOption = viewMentor.getInputFromUser("Choose option: ");
            switch(chosenOption){
                case "1": assignStudentToTeam(team);
                    break;
                case "0": toContinue = false;
                    break;
                default: viewMentor.displayText("Wrong option. Try again!");
                    break;
            }
        }while(toContinue);
    }

    public void assignStudentToTeam(Team team){
        Student student = getStudent();
        if(student != null) {
            new DaoTeam().assignStudentToTeam(student.getUserId(), team.getGroupId());
        }
    }

    public void runMenu() {
        String mentorOption = "";
        while (!mentorOption.equals("0")) {

            viewMentor.displayText("\nWhat would like to do?");
            viewMentor.displayList(viewMentor.getMentorOptions());

            mentorOption = viewMentor.getInputFromUser("Option: ");
            switch (mentorOption) {
                case "1": createStudent();
                        break;
                case "2": createTeam();
                        break;
                case "3": addQuest();
                        break;
                case "4": addArtifact();
                        break;
                case "5": updateQuest();
                        break;
                case "6": updateArtifact();
                        break;
                case "7": markQuest();
                        break;
                case "8": markBoughtArtifact();
                        break;
                case "9": seeAllWallets();
                        break;
                case "10": assignStudentsToTeam();
                        break;
                case "0": break;

                default: viewMentor.displayText("Wrong option. Try again!");
                         break;
            }
        }

    }

}
