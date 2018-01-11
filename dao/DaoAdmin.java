package dao;

import model.*;
import java.util.ArrayList;

public class DaoAdmin implements IDaoAdmin{

    private static ArrayList <Admin> admins;

    public ArrayList <Admin> getAdmins() { return admins; }

    public Admin createAdmin(String name, String password, String email){
        Admin admin = new Admin(name, password, email);
        return admin;
    }

    public Admin getAdminById(int id){
        for(Admin admin: admins){
            if(admin.getUserId() == id){
                return admin;
            }
        }
        return null;
    }

    public void exportData(){ }
    
    public void importData(Admin admin){
        admins.add(admin);
    }
    
}