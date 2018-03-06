package controller;


import dao.DbConnection;
import model.Admin;
import model.Mentor;
import model.Student;
import model.User;
import view.ViewAdmin;
import view.ViewLogin;
import dao.DaoLogin;

import java.sql.SQLException;

public class ControllerLogin{
    private ViewLogin viewLogin;
    private DaoLogin daoLogin;

    public ControllerLogin(ViewLogin viewLogin){
        this.viewLogin = viewLogin;
        this.daoLogin = new DaoLogin();
    }

    public void runMenu(){
        try {
            if(DbConnection.getConnection() != null){

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

            DbConnection.getConnection().close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
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
            ViewAdmin viewAdmin = new ViewAdmin();
            controller = new ControllerAdmin((Admin)user, viewAdmin);
        }else if(user instanceof Mentor){
            controller = new ControllerMentor((Mentor)user);
        }else if(user instanceof Student){
            controller = new ControllerStudent((Student)user);
        }

        return controller;
    }

}
