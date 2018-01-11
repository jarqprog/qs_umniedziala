package controller;

import java.util.ArrayList;
import dao.DaoClass;
import dao.DaoMentor;
import model.Admin;
import model.CodecoolClass;
import model.Mentor;
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

        String nameRequest = "Enter name of new mentor: ";
        String mentorName = viewAdmin.getInputFromUser(nameRequest);

        String passwordRequest = "Enter password of new mentor: ";
        String mentorPassword = viewAdmin.getInputFromUser(passwordRequest);

        String emailRequest = "Enter email of new mentor: ";
        String mentorEmail = viewAdmin.getInputFromUser(emailRequest);

        daoMentor.createMentor(mentorName, mentorPassword, mentorEmail);

    }

    public void createClass() {
        viewAdmin.displayText("Implementation in progress");
    }

    public void editMentor() {
        DaoMentor daoMentor = new DaoMentor();
        ArrayList<Mentor> mentorList = daoMentor.importData();

        viewAdmin.displayList(mentorList);
        int mentorId = viewAdmin.getIntInputFromUser("\nEnter id of mentor: ");
        Mentor mentor = daoMentor.getMentorById(mentorId);
        while (mentor == null) {
            viewAdmin.displayText("No mentor with such id found!");
            mentorId = viewAdmin.getIntInputFromUser("\nEnter id of mentor: ");
            mentor = daoMentor.getMentorById(mentorId);
        }

        String choice = viewAdmin.getInputFromUser("\nModify email or class? (e/c) ");
        while (!(choice.equals("e") || choice.equals("c"))) {
            viewAdmin.displayText("Wrong input!");
            choice = viewAdmin.getInputFromUser("\nModify email or class? (e/c) ");
        }

        if (choice.equals("e")) {
            String newEmail = viewAdmin.getInputFromUser("\nEnter mentor's new email: ");
            mentor.setEmail(newEmail);
        } else if (choice.equals("c")) {
            DaoClass daoClass = new DaoClass();
            ArrayList<CodecoolClass> classList = daoClass.importData();
            int newClassId = viewAdmin.getIntInputFromUser("\nEnter id of mentor's new class: ");
            CodecoolClass newClass = daoClass.getClassById(newClassId);

            while (newClass == null) {
                viewAdmin.displayText("No class with such id found!");
                newClassId = viewAdmin.getIntInputFromUser("\nEnter id of mentor's new class: ");
                newClass = daoClass.getClassById(newClassId);
            }
            mentor.setClassId(newClass.getGroupId());
        }
        daoMentor.exportData(mentorList);
    }

    public void seeMentorData() {
        viewAdmin.displayText("Implementation in progress");
    }

    public void assignMentorToClass() {
        viewAdmin.displayText("Implementation in progress");
    }

    public void createLevel() {
        viewAdmin.displayText("Implementation in progress");
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
