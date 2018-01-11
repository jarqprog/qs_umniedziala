package controller;

import java.util.ArrayList;

import controller.ControllerAdmin;
import controller.ControllerMentor;
import controller.ControllerStudent;
import controller.IUserController;
import dao.DaoAdmin;
import dao.DaoArtifact;
import dao.DaoMentor;
import dao.DaoStudent;
import iterator.MyIterator;
import model.Admin;
import model.Mentor;
import model.Student;
import model.User;
import view.ViewLogin;

public class ControllerLogin{
    private ViewLogin viewLogin = new ViewLogin();
    private DaoAdmin daoAdmin;
    private DaoMentor daoMentor;
    private DaoArtifact daoArtifact;
    private DaoStudent daoStudent;

    public ControllerLogin(){
        this.daoAdmin = new DaoAdmin();
        this.daoMentor = new DaoMentor();
        this.daoStudent = new DaoStudent();
        this.daoArtifact = new DaoArtifact();
        
    }

    public void runMenu(){
        implementAllTestData();

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

        User user = getUser(userEmail, userPassword);
        if(user != null){
            IUserController userController = getUserController(user);
            if(userController != null){
                userController.runMenu();
            }
        }else{
            viewLogin.displayText("Incorrect data");
        }  
    }

    private ArrayList<User> getAllUsers(){
        ArrayList <User> users = new ArrayList<>();

        users.addAll(this.daoAdmin.importData());
        users.addAll(this.daoMentor.importData());
        users.addAll(this.daoStudent.importData());
        
        return users;
    }

    private User getUser(String email, String password){
        ArrayList <User> users = getAllUsers();
        MyIterator <User> myIterator = new MyIterator<>(users);

        while(myIterator.hasNext()){
            User user = myIterator.next();
            if(user.getEmail().equals(email) && user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
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

    private void implementAllTestData(){
        this.daoAdmin.implementTestData();
        this.daoMentor.implementTestData();
        this.daoArtifact.implementTestData();
        this.daoStudent.implementTestData();
        
    }
}