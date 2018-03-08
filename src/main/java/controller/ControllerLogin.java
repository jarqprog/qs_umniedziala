package controller;


import dao.*;
import model.Admin;
import model.Mentor;
import model.Student;
import model.User;
import view.ViewAdmin;
import view.ViewLogin;
import view.ViewMentor;
import view.ViewStudent;

import java.sql.SQLException;

public class ControllerLogin{
    private ViewLogin viewLogin;
    private DaoLogin daoLogin;

    public ControllerLogin(ViewLogin viewLogin){
        this.viewLogin = viewLogin;
        this.daoLogin = new DaoLogin();
    }

    public void runMenu(){
        String userOption = "";
        while (!userOption.equals("0")) {

            System.out.println("\nWhat would like to do?");
            viewLogin.displayList(viewLogin.getLoginOptions());

            userOption = viewLogin.getInputFromUser("Option: ");
            switch (userOption) {
                case "1": login();
                break;
                case "0": break;

                default: System.out.println("Wrong option. Try again!");
                break;
            }
        }
    }

    public void login(){
        String userEmail = viewLogin.getInputFromUser("email: ");
        String userPassword = viewLogin.getInputFromUser("password: ");

        User user = daoLogin.getUser(userEmail, userPassword);

        if(user != null){
            IUserController userController = getUserController(user);
            if(userController != null){
                userController.runMenu();
            }
        }else{
            viewLogin.displayText("Incorrect data");
        }  
    }

    private IUserController getUserController(User user){
        IUserController controller = null;

        if(user instanceof Admin){
            controller = new ControllerAdmin((Admin)user, new ViewAdmin(),
                                            new DaoMentor(), new DaoClass(),
                                            new DaoLevel());
        }else if(user instanceof Mentor){
            controller = new ControllerMentor((Mentor)user, new ViewMentor(),
                                            new DaoStudent(), new DaoClass(),
                                            new DaoArtifact(), new DaoQuest(),
                                            new DaoTeam(), new DaoWallet());
        }else if(user instanceof Student){
            controller = new ControllerStudent((Student)user, new ViewStudent(),
                                            new DaoWallet(), new DaoStudent(),
                                            new DaoArtifact(), new DaoLevel(),
                                            new DaoTeam());
        }

        return controller;
    }

}
