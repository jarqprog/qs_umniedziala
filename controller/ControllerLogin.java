package controller;

import java.util.ArrayList;

import dao.DaoAdmin;
import dao.DaoMentor;
import dao.DaoStudent;
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
        ;
    }

    private IUserController getUserController(User user){
        ;
    }
}