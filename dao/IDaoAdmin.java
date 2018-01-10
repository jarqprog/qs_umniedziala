package dao;

import model.*;
import java.util.ArrayList;

public interface IDaoAdmin extends IDao{

    public ArrayList<Admin> getAdmins();
    public Admin getAdminById();
    public Admin createAdmin();

}