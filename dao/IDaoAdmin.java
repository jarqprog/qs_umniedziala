package dao;

import model.*;
import java.util.ArrayList;

public interface IDaoAdmin{

    public Admin getAdminById(int id);
    public Admin createAdmin(String name, String password, String email);
    public ArrayList <Admin> importData();
    public void exportData(ArrayList <Admin> updatedAdmins);
}