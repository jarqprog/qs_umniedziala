package terminal.controller;

import java.util.List;

import system.dao.*;
import system.model.Admin;
import system.model.CodecoolClass;
import system.model.Mentor;
import system.model.Level;
import terminal.controller.view.ViewAdmin;

public class ControllerAdmin implements IUserController {

    private ViewAdmin viewAdmin;
    private Admin admin;
    private IDaoMentor daoMentor;
    private IDaoClass daoClass;
    private IDaoLevel daoLevel;

    public static ControllerAdmin createController(ViewAdmin viewAdmin, IDaoMentor daoMentor, IDaoClass daoClass, IDaoLevel daoLevel) {
        return new ControllerAdmin(viewAdmin, daoMentor, daoClass, daoLevel);
    }

    ControllerAdmin(Admin admin, ViewAdmin viewAdmin, IDaoMentor daoMentor, IDaoClass daoClass, IDaoLevel daoLevel) {
        this.viewAdmin = viewAdmin;
        this.admin = admin;
        this.daoMentor = daoMentor;
        this.daoClass = daoClass;
        this.daoLevel = daoLevel;
    }

    private ControllerAdmin(ViewAdmin viewAdmin, IDaoMentor daoMentor, IDaoClass daoClass, IDaoLevel daoLevel) {
        this.viewAdmin = viewAdmin;
        this.daoMentor = daoMentor;
        this.daoClass = daoClass;
        this.daoLevel = daoLevel;
    }

    public void setModel(Admin admin) {
        this.admin = admin;
    }

    private void createMentor() {
        String mentorName = viewAdmin.getInputFromUser("Enter name of new mentor: ");
        String mentorPassword = viewAdmin.getInputFromUser("Enter password of new mentor: ");
        String mentorEmail = viewAdmin.getInputFromUser("Enter email of new mentor: ");

         if(daoMentor.createMentor(mentorName, mentorPassword, mentorEmail).getUserId() != 0){
             viewAdmin.displayText("Mentor creation successful");
         }else {
             viewAdmin.displayText("Mentor creation failed");
         }
    }

    private void createClass() {

        String className = viewAdmin.getInputFromUser("Enter the name of the class:");

        if(daoClass.createClass(className).getGroupId() != 0){
            viewAdmin.displayText("Creation class successful");
        }else {
            viewAdmin.displayText("Creation class failed");
        }

    }

    private void editMentor() {
        Mentor mentor = getMentor();
        if(mentor != null) {
            String editMentorOption = "";
            while (!editMentorOption.equals("0")) {

                viewAdmin.displayText("\nWhat would like to do?");
                viewAdmin.displayList(viewAdmin.getEditMentorOptions1());

                editMentorOption = viewAdmin.getInputFromUser("Option: ");
                switch (editMentorOption) {
                    case "1":
                        editMentorEmail(mentor);
                        break;
                    case "2":
                        editMentorClass(mentor);
                        break;
                    case "0":
                        break;

                    default:
                        viewAdmin.displayText("Wrong option. Try again!");
                        break;
                }
            }
        }else {
            viewAdmin.displayText("No such mentor");
        }
    }

    private void seeAllMentors() {
        List<Mentor> mentorList = daoMentor.getAllMentors();

        viewAdmin.displayText("Mentor's list:");
        if(mentorList.size() == 0){
            viewAdmin.displayText("No mentors to print");
        }
        else {
            viewAdmin.displayList(mentorList);
        }
    }

    private Mentor getMentor() {
        Mentor mentor = null;
        seeAllMentors();

        if(daoMentor.getAllMentors().size() != 0) {
            int mentorId = viewAdmin.getIntInputFromUser("\nEnter id of mentor: ");

            mentor = daoMentor.importMentor(mentorId);
        }

        return mentor;
    }

    private void editMentorEmail(Mentor mentor) {
        String newEmail = viewAdmin.getInputFromUser("\nEnter mentor's new email: ");
        mentor.setEmail(newEmail);
        boolean isInsert = daoMentor.updateMentor(mentor);

        if(isInsert){
            viewAdmin.displayText("Edit mentor successful");
        }else {
            viewAdmin.displayText("Edit mentor failed");
        }
    }

    private void editMentorClass(Mentor mentor){

        Integer userId = mentor.getUserId();
        Integer mentorClassId = daoMentor.getMentorClassId(mentor);

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
                        adminOption = "0";
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
        if(codecoolClass != null) {
            daoClass.assignMentorToClass(userId, codecoolClass.getGroupId());
        }else{
            viewAdmin.displayText("No such class");
        }
    }

    private void unsignMentorFromClass(Integer userId){
        daoClass.unsignMentorFromClass(userId);
    }

    private void changeMentorClass(Integer userId){
        CodecoolClass codecoolClass = getCodecoolClass();
        if(codecoolClass != null) {
            daoClass.updateMentorInClass(userId, codecoolClass.getGroupId());
            viewAdmin.displayText("Update was successful");
        }else{
            viewAdmin.displayText("Update failed");
        }
    }

    private CodecoolClass getCodecoolClass() {
        CodecoolClass chosenClass = null;

        viewAdmin.displayText("Available classes: ");
        List<CodecoolClass> codecoolClasses = daoClass.getAllClasses();
        if (codecoolClasses.size() != 0) {
            for (CodecoolClass codecoolClass : codecoolClasses) {
                viewAdmin.displayText(codecoolClass.getBasicInfo());
            }

            Integer classId = viewAdmin.getIntInputFromUser("\nEnter id of chosen class: ");

            chosenClass = daoClass.importClass(classId);
        } else {
            viewAdmin.displayText("No classes found");
        }
        return  chosenClass;
    }


    private void seeMentorData() {
        Mentor mentor = getMentor();
        if(mentor != null) {
            CodecoolClass mentorsClass = daoClass.getMentorsClass(mentor.getUserId());

            viewAdmin.displayText(mentor.toString());
            if (mentorsClass != null) {
                viewAdmin.displayText("");
                viewAdmin.displayText(mentorsClass.toString());
            }
        }

    }

    private void seeAllLevels() {
        List<Level> levelList = daoLevel.getAllLevels();

        viewAdmin.displayText("List of existing levels:");
        if(levelList.size() != 0) {
            viewAdmin.displayList(levelList);
        }else {
            viewAdmin.displayText("No levels found");
        }
    }

    private void createLevel() {
        seeAllLevels();

        String levelName = viewAdmin.getInputFromUser("Enter name of new level: ");
        int coinsLimit = viewAdmin.getIntInputFromUser("Enter the number of coins required for level: ");
        if (daoLevel.createLevel(levelName, coinsLimit).getLevelId() != 0) {
            viewAdmin.displayText("Creation level successful");
        } else {
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
