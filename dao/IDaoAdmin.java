package dao;

import model.*;
import java.util.ArrayList;

public interface IDaoAdmin{

    public ArrayList<Admin> getAdmins();
    public Admin getAdminById(int id);
    public Admin createAdmin(String name, String password, String email);
    public void importData(Admin admin);
    public void exportData();

}