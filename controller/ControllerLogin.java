package controller;

import java.util.ArrayList;

import controller.ControllerAdmin;
import controller.ControllerMentor;
import controller.ControllerStudent;
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

    public void login(){}

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
}