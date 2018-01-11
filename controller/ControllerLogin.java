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

public class ControllerLogin{

    public void login(){
        ;
    }

    private ArrayList<User> getAllUsers(){
        ArrayList <User> users = new ArrayList<>();
        
        DaoAdmin daoAdmin = new DaoAdmin();
        DaoMentor daoMentor = new DaoMentor();
        DaoStudent daoStudent = new DaoStudent();

        users.addAll(daoAdmin.getAdmins());
        users.addAll(daoMentor.getAdmins());
        users.addAll(daoStudent.getAdmins());
        
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
        if(user instanceof Admin){
            return ControllerAdmin;
        }else if(user instanceof Mentor){
            return ControllerMentor;
        }else if(user instanceof Student){
            return ControllerStudent;
        }else{
            return null;
        }
    }
}