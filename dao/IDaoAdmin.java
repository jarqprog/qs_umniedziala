package dao;

import model.*;
import java.util.ArrayList;

public interface IDaoAdmin{

    public ArrayList<Admin> getAdmins();
    public Admin getAdminById();
    public Admin createAdmin();
    public void importData(Admin admin);
    public void exportData();

}