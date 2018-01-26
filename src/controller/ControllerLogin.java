package controller;


import model.Admin;
import model.Mentor;
import model.Student;
import model.User;
import view.ViewLogin;
import dao.DaoUser;

public class ControllerLogin{
    private ViewLogin viewLogin = new ViewLogin();
    private DaoUser daoUser;

    public ControllerLogin(){
        this.daoUser = new DaoUser();
    }

    public void runMenu(){
        if(daoUser.setConnection()){

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

        daoUser.closeConnection();
        }
    
    }

    public void login(){
        String userEmail = viewLogin.getInputFromUser("email: ");
        String userPassword = viewLogin.getInputFromUser("password: ");

        User user = daoUser.getUser(userEmail, userPassword);

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
            controller = new ControllerAdmin((Admin)user);
        }else if(user instanceof Mentor){
            controller = new ControllerMentor((Mentor)user);
        }else if(user instanceof Student){
            controller = new ControllerStudent((Student)user);
        }

        return controller;
    }

}
