package controller;

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

        String nameRequest = "Enter name of new team: ";
        String teamName = viewMentor.getInputFromUser(nameRequest);

        Team team = daoTeam.createTeam(teamName);
        boolean isInsert = daoTeam.exportTeam(team);
        if(isInsert){
            viewMentor.displayText("Creation team successful");
        }else{
            viewMentor.displayText("Creation team failed");
        }
    }

    public Student getStudent(){
        DaoStudent daoStudent = new DaoStudent();

        viewMentor.displayText("Available students:\n");
        viewMentor.displayList(daoStudent.getAllStudents());

        Integer studentId = viewMentor.getIntInputFromUser("\nEnter id of student: ");

        return daoStudent.importInstance(studentId);
    }

    public CodecoolClass getCodecoolClass(){
        DaoClass daoClass = new DaoClass();

        viewMentor.displayText("Available classes: ");
        ArrayList <CodecoolClass> allClasses = daoClass.getAllClasses();
        for(CodecoolClass codecoolClass: allClasses){
            viewMentor.displayText(codecoolClass.getBasicInfo());
        }

        Integer classId = viewMentor.getIntInputFromUser("\nEnter id of chosen class: ");

        return daoClass.importClass(classId);
    }

    public Team getTeam(){
        DaoTeam daoTeam = new DaoTeam();

        viewMentor.displayText("Available teams:\n");
        ArrayList<Team> teams = daoTeam.getAllTeams();
        for(Team team: teams) {
            viewMentor.displayText(team.getBasicInfo());
        }

        int teamId = viewMentor.getIntInputFromUser("\nEnter id of team: ");

        return daoTeam.importTeam(teamId);
    }

    public Quest getTeamQuest(){
        DaoQuest daoQuest = new DaoQuest();

        viewMentor.displayText("Available team quests:\n");
        viewMentor.displayList(daoQuest.getTeamQuests());

        Integer questId = viewMentor.getIntInputFromUser("Enter id of chosen quest");

        return daoQuest.importQuest(questId);
    }

    public Quest getIndividualQuest(){
        DaoQuest daoQuest = new DaoQuest();

        viewMentor.displayText("Available individual quests:\n");
        viewMentor.displayList(daoQuest.getIndividualQuests());

        Integer questId = viewMentor.getIntInputFromUser("Enter id of chosen quest");

        return daoQuest.importQuest(questId);
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
        int option = 0;
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
        int option = 0;
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

        viewMentor.displayText("Available quests:\n");
        viewMentor.displayList(daoQuest.getAllQuests());

        int questId = viewMentor.getIntInputFromUser("\nEnter id of quest: ");

        return daoQuest.importQuest(questId);
    }

    public void updateQuest(){
        Quest quest = getQuest();

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
        new DaoQuest().updateQuest(quest);
    }

    public void updateQuestDescription(Quest quest){
        String description = viewMentor.getInputFromUser("Pass new quest description: ");
        quest.setDescription(description);
        new DaoQuest().updateQuest(quest);
    }

    public void updateQuestValue(Quest quest){
        Integer value = viewMentor.getIntInputFromUser("Pass new quest value: ");
        quest.setValue(value);
        new DaoQuest().updateQuest(quest);
    }

    public void updateQuestType(Quest quest){
        String type = chooseType();
        quest.setType(type);
        new DaoQuest().updateQuest(quest);
    }

    public void updateQuestCategory(Quest quest){
        String category = chooseCategory();
        quest.setCategory(category);
        new DaoQuest().updateQuest(quest);
    }

    public void updateArtifact() {
        Artifact artifact = getArtifact();

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
        new DaoArtifact().updateArtifact(artifact);
    }
    public void updateArtifactValue(Artifact artifact){
        Integer value = viewMentor.getIntInputFromUser("Choose new value: ");
        artifact.setValue(value);
        new DaoArtifact().updateArtifact(artifact);
    }

    public void updateArtifactDescription(Artifact artifact){
        String description = viewMentor.getInputFromUser("Choose new description: ");
        artifact.setDescription(description);
        new DaoArtifact().updateArtifact(artifact);
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
            new DaoArtifact().updateArtifact(artifact);
        }
    }

    private void seeAllArtifacts() {

        DaoArtifact daoArtifact = new DaoArtifact();
        ArrayList<Artifact> artifactList = daoArtifact.getAllArtifacts();

        viewMentor.displayText("List of artifacts:");
        viewMentor.displayList(artifactList);
    }

    private Artifact getArtifact() {

        seeAllArtifacts();
        int artifactId = viewMentor.getIntInputFromUser("\nEnter id of artifact: ");
        DaoArtifact daoArtifact = new DaoArtifact();
        Artifact artifact = daoArtifact.importArtifact(artifactId);
        while (artifact == null) {
            viewMentor.displayText("No artifact with such id found!");
            artifactId = viewMentor.getIntInputFromUser("\nEnter id of artifact: ");
            artifact = daoArtifact.importArtifact(artifactId);
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

        viewMentor.displayText("Student new artifacts:\n");
        viewMentor.displayList(student.getAllNewArtifacts());
        Integer artifactId = viewMentor.getIntInputFromUser("Choose id artifact to be marked as used: ");

        Artifact artifactToBeBougth = new DaoArtifact().importArtifact(artifactId);
        student.markArtifactAsBougth(artifactToBeBougth);

        new DaoWallet().updateStudentsArtifact(artifactToBeBougth.getItemId(), student.getUserId());

    }

    public void markStudentAchivedQuest() {

        Student student = getStudent();
        Quest quest = getIndividualQuest();
        int coins = quest.getValue();
        student.addCoins(coins);
        DaoWallet daoWallet = new DaoWallet();
        daoWallet.updateWallet(student);// na innym branchu

    }

    public void markTeamAchivedQuest() {

        Team team = getTeam();
        Quest quest = getTeamQuest();
        int coins = quest.getValue();
        team.addCoins(coins);
        DaoTeam daoTeam = new DaoTeam();
        daoTeam.updateTeamData(team);

    }

    public void markStudentBoughtArtifact() { }

    public void markTeamBoughtArtifact() { }

    public void seeAllWallets() {
        DaoClass daoClass = new DaoClass();
        CodecoolClass mentorsClass = daoClass.getMentorsClass(mentor.getUserId());
        viewMentor.displayText("Wallets of students of class: \n");
        for (Student student : mentorsClass.getStudents()) {
            Wallet wallet = student.getWallet();
            viewMentor.displayText(student.toString());
            viewMentor.displayText(wallet.toString());
        }
    }
    private ArrayList<Wallet> getWalletsOfStudents(ArrayList<Student> students) {
        ArrayList<Wallet> walletsOfStudents = new ArrayList<>();
        for (Student student : students) {
            walletsOfStudents.add(student.getWallet());
        }
        return walletsOfStudents;
    }

    public void seeQuests() {
//        viewMentor.displayText("Available quests: ");
//        viewMentor.displayList(new DaoArtifact().importData());
    }

    public void seeArtifacts() {
//        viewMentor.displayText("Available artifacts: ");
//        viewMentor.displayList(new DaoArtifact().importData());
    }

    public void assignStudentsToTeam(){
        Team team = getTeam();

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
        new DaoTeam().assignStudentToTeam(student.getUserId(), team.getGroupId());
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
