package controller;

import dao.DaoMentor;
import model.Admin;
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
