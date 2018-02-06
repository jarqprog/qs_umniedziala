package controller;

import java.util.ArrayList;
//import dao.DaoClass;
//import dao.DaoMentor;
//import dao.DaoLevel;

import dao.DaoClass;
import dao.DaoMentor;
import iterator.MyIterator;
import model.Admin;
import model.CodecoolClass;
import model.Mentor;
//import model.Level;
import view.ViewAdmin;

public class ControllerAdmin implements IUserController {

    private ViewAdmin viewAdmin;
    private Admin admin;

    public ControllerAdmin(Admin admin) {
        this.viewAdmin = new ViewAdmin();
        this.admin = admin;
    }

    public void createMentor() {
//        DaoMentor daoMentor = new DaoMentor();
//
//        String nameRequest = "Enter name of new mentor: ";
//        String mentorName = viewAdmin.getInputFromUser(nameRequest);
//
//        String passwordRequest = "Enter password of new mentor: ";
//        String mentorPassword = viewAdmin.getInputFromUser(passwordRequest);
//
//        String emailRequest = "Enter email of new mentor: ";
//        String mentorEmail = viewAdmin.getInputFromUser(emailRequest);
//
//        daoMentor.createMentor(mentorName, mentorPassword, mentorEmail);
//
    }

    public void createClass() {

        String className = viewAdmin.getInputFromUser("Enter the name of the class:");
        DaoClass daoClass = new DaoClass();
        CodecoolClass codecoolClass = daoClass.createClass(className);
        daoClass.exportClass(codecoolClass);

    }

    public void editMentor() {
        seeAllMentors();
        Mentor mentor = getMentor();

        String editMentorOption = "";
        while (!editMentorOption.equals("0")) {

            viewAdmin.displayText("\nWhat would like to do?");
            viewAdmin.displayList(viewAdmin.getEditMentorOptions1());

            editMentorOption = viewAdmin.getInputFromUser("Option: ");
            switch (editMentorOption) {
                case "1": editMentorEmail(mentor);
                    break;
                case "2": editMentorClass(mentor);
                    break;
                case "0": break;

                default: viewAdmin.displayText("Wrong option. Try again!");
                    break;
            }
        }
    }

    private void seeAllMentors() {

        DaoMentor daoMentor = new DaoMentor();
        ArrayList<Mentor> mentorList = daoMentor.getAllMentors();

        viewAdmin.displayText("Mentor's list:");
        viewAdmin.displayList(mentorList);
    }

    private Mentor getMentor() {

        int mentorId = viewAdmin.getIntInputFromUser("\nEnter id of mentor: ");
        DaoMentor daoMentor = new DaoMentor();
        Mentor mentor = daoMentor.importInstance(mentorId);
        while (mentor == null) {
            viewAdmin.displayText("No mentor with such id found!");
            mentorId = viewAdmin.getIntInputFromUser("\nEnter id of mentor: ");
            mentor = daoMentor.importInstance(mentorId);
        }
        return mentor;
    }

    private void editMentorEmail(Mentor mentor) {
        DaoMentor daoMentor = new DaoMentor();
        String newEmail = viewAdmin.getInputFromUser("\nEnter mentor's new email: ");
        mentor.setEmail(newEmail);
        daoMentor.updateInstance(mentor);
    }

    private void editMentorClass(Mentor mentor){

        Integer userId = mentor.getUserId();
        Integer mentorClassId = new DaoMentor().getMentorClassId(mentor);

        if(mentorClassId == null){
            assignMentorToClass(userId);
        }else{
            String adminOption = "";
            while (!adminOption.equals("0")) {

                viewAdmin.displayText("\nWhat would like to do?");
                viewAdmin.displayList(viewAdmin.getEditMentorOptions());

                adminOption = viewAdmin.getInputFromUser("Option: ");
                switch (adminOption) {
                    case "1": unsignMentorFromClass(userId);
                        adminOption = "0"; //to leave menu when operation is done
                        break;
                    case "2": changeMentorClass(userId);
                        adminOption = "0";
                        break;
                    case "0": break;

                    default: viewAdmin.displayText("Wrong option. Try again!");
                        break;
                }
            }
        }
    }

    private void assignMentorToClass(Integer userId){
        CodecoolClass codecoolClass = getCodecoolClass();
        new DaoClass().assignMentorToClass(userId, codecoolClass.getGroupId());
    }

    private void unsignMentorFromClass(Integer userId){
        new DaoClass().unsignMentorFromClass(userId);
    }

    private void changeMentorClass(Integer userId){
        CodecoolClass codecoolClass = getCodecoolClass();
        new DaoClass().updateMentorInClass(userId, codecoolClass.getGroupId());
    }

    private CodecoolClass getCodecoolClass(){
        ArrayList<CodecoolClass> classes = new DaoClass().getAllClasses();

        MyIterator<CodecoolClass> iterator = new MyIterator <CodecoolClass>(classes);
        while(iterator.hasNext()){
            viewAdmin.displayText(iterator.next().getBasicInfo());
        }

        Integer classId = viewAdmin.getIntInputFromUser("Choose class by id: ");
        return new DaoClass().importClass(classId);
    }

    public void seeMentorData() {
        viewAdmin.displayText("Implementation in progress");
    }

    public void createLevel() {
//        DaoLevel daoLevel = new DaoLevel();
//
//        String nameRequest = "Enter name of new level: ";
//        String levelName = viewAdmin.getInputFromUser(nameRequest);
//
//        String numberRequest = "Enter the number of coins required for level: ";
//        int levelNumber = viewAdmin.getIntInputFromUser(numberRequest);
//
//        daoLevel.createLevels(levelName, levelNumber);
//
    }

    public void runMenu() {

        String adminOption = "";
        while (!adminOption.equals("0")) {

            viewAdmin.displayText("\nWhat would like to do?");
            viewAdmin.displayList(viewAdmin.getAdminOptions());

            adminOption = viewAdmin.getInputFromUser("Option: ");
            switch (adminOption) {
                case "1": createMentor();
                          break;
                case "2": createClass();
                          break;
                case "3": editMentor();
                          break;
                case "4": seeMentorData();
                          break;
                case "5": createLevel();
                          break;
                case "0": break;

                default: viewAdmin.displayText("Wrong option. Try again!");
                         break;
            }
        }

    }

}
