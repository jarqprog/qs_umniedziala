package controller;

import java.util.ArrayList;
import dao.DaoLevel;
import dao.DaoMentor;
import dao.DaoClass;
import dao.DaoMentor;
import iterator.MyIterator;
import model.Admin;
import model.CodecoolClass;
import model.Mentor;
import model.Level;
import view.ViewAdmin;

public class ControllerAdmin implements IUserController {

    private ViewAdmin viewAdmin;
    private Admin admin;

    public ControllerAdmin(Admin admin) {
        this.viewAdmin = new ViewAdmin();
        this.admin = admin;
    }

    public void createMentor() {
        DaoMentor daoMentor = new DaoMentor();
        String mentorName = viewAdmin.getInputFromUser("Enter name of new mentor: ");
        String mentorPassword = viewAdmin.getInputFromUser("Enter password of new mentor: ");
        String mentorEmail = viewAdmin.getInputFromUser("Enter email of new mentor: ");

        Mentor mentor = daoMentor.createInstance(mentorName, mentorPassword, mentorEmail);
        boolean isInsert = daoMentor.exportInstance(mentor);
         if(isInsert){
             viewAdmin.displayText("Mentor creation successful");
         }else {
             viewAdmin.displayText("Mentor creation failed");
         }
    }

    public void createClass() {

        String className = viewAdmin.getInputFromUser("Enter the name of the class:");
        DaoClass daoClass = new DaoClass();
        CodecoolClass codecoolClass = daoClass.createClass(className);
        boolean isInsert = daoClass.exportClass(codecoolClass);

        if(isInsert){
            viewAdmin.displayText("Creation class successful");
        }else {
            viewAdmin.displayText("Creation class failed");
        }

    }

    public void editMentor() {

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
        DaoMentor daoMentor = new DaoMentor();
        seeAllMentors();
        int mentorId = viewAdmin.getIntInputFromUser("\nEnter id of mentor: ");

        Mentor mentor = daoMentor.importInstance(mentorId);

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

    public CodecoolClass getCodecoolClass(){
        DaoClass daoClass = new DaoClass();

        viewAdmin.displayText("Available classes: ");
        viewAdmin.displayList(daoClass.getAllClasses());

        Integer classId = viewAdmin.getIntInputFromUser("\nEnter id of chosen class: ");

        return daoClass.importClass(classId);
    }

    public void seeMentorData() {
        Mentor mentor = getMentor();
        CodecoolClass mentorsClass = new DaoClass().getMentorsClass(mentor.getUserId());

        viewAdmin.displayText(mentor.toString());
        if(mentorsClass != null){
            viewAdmin.displayText("");
            viewAdmin.displayText(mentorsClass.toString());
        }

    }

    private void seeAllLevels() {

        DaoLevel daoLevel = new DaoLevel();
        ArrayList<Level> levelList = daoLevel.getAllLevels();

        viewAdmin.displayText("List of existing levels:");
        viewAdmin.displayList(levelList);
    }

    public void createLevel() {
        seeAllLevels();

        String nameRequest = "Enter name of new level: ";
        String levelName = viewAdmin.getInputFromUser(nameRequest);

        String coinsLimitRequest = "Enter the number of coins required for level: ";
        int coinsLimit = viewAdmin.getIntInputFromUser(coinsLimitRequest);

        DaoLevel daoLevel = new DaoLevel();
        Level level = daoLevel.createLevel(levelName, coinsLimit);
        boolean isInsert = daoLevel.exportLevel(level);

        if(isInsert){
            viewAdmin.displayText("Creation level successful");
        }else{
            viewAdmin.displayText("Creation level failed");
        }

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
