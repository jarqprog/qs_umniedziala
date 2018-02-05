package controller;

import java.util.ArrayList;
//import dao.DaoClass;
//import dao.DaoMentor;
//import dao.DaoLevel;

import dao.DaoClass;
import dao.DaoMentor;
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

    }

    private void editMentorClass(Mentor mentor){

        Integer userId = mentor.getUserId();
        Integer mentorId = new DaoMentor().getMentorClassId(mentor);
        if(mentorId == null){
            assignMentorToClass(userId);
        }else{
            String adminOption = "";
            while (!adminOption.equals("0")) {

                viewAdmin.displayText("\nWhat would like to do?");
                viewAdmin.displayList(viewAdmin.getEditMentorOptions());

                adminOption = viewAdmin.getInputFromUser("Option: ");
                switch (adminOption) {
                    case "1": unsignMentorFromClass(userId);
                        break;
                    case "2": changeMentorClass(userId);
                        break;
                    case "0": break;

                    default: viewAdmin.displayText("Wrong option. Try again!");
                        break;
                }
            }
        }
    }

    private void assignMentorToClass(Integer userId){
        ;
    }

    private void unsignMentorFromClass(Integer userId){
        ;
    }

    private void changeMentorClass(Integer userId){
        ;
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
                case "5": assignMentorToClass();
                          break;
                case "6": createLevel();
                          break;
                case "0": break;

                default: viewAdmin.displayText("Wrong option. Try again!");
                         break;
            }
        }

    }

}
