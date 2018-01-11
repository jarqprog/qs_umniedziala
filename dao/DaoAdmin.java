package dao;

import model.*;
import java.util.ArrayList;

public class DaoAdmin implements IDaoAdmin{

    private static ArrayList <Admin> admins = new ArrayList<>();

    public void implementTestData() {
        createAdmin("Jan", "haslo", "jan@mail.pl");
    }

    public Admin createAdmin(String name, String password, String email){
        Admin admin = new Admin(name, password, email);
        admins.add(admin);
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

    public void exportData(ArrayList <Admin> updatedAdmins){
        admins = updatedAdmins;
    }

    public ArrayList <Admin> importData(){
        return admins;
    }
    
}