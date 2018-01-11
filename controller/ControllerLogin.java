package controller;

import java.util.ArrayList;

import controller.ControllerAdmin;
import controller.ControllerMentor;
import controller.ControllerStudent;
import controller.IUserController;
import dao.DaoAdmin;
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
    private DaoStudent daoStudent;

    public ControllerLogin(){
        daoAdmin = new DaoAdmin();
        daoMentor = new DaoMentor();
        daoStudent = new DaoStudent();
        
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
        userEmail = viewLogin.getInputFromUser("email: ");
        userPassword = viewLogin.getInputFromUser("password: ");

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
        
        DaoAdmin daoAdmin = new DaoAdmin();
        DaoMentor daoMentor = new DaoMentor();
        DaoStudent daoStudent = new DaoStudent();

        users.addAll(importData.getAdmins());
        users.addAll(importData.getMentors());
        users.addAll(importData.getStudents());
        
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
            controller = new ControllerAdmin(user);
        }else if(user instanceof Mentor){
            controller = new ControllerMentor(user);
        }else if(user instanceof Student){
            controller = new ControllerStudent(user);
        }

        return controller;
    }

    private void implementAllTestData(){
        this.daoAdmin.implementTestData();
        this.daoMentor.implementTestData();
        this.daoStudent.implementTestData();
    }
}