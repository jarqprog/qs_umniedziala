package dao;

import model.Admin;

public interface IDaoAdmin {
    Admin createInstance(String name, String password, String email);
    Admin createInstance(int userId, String name, String password, String email);
    Admin importInstance(int userId);
    boolean exportInstance(Admin user);
    boolean updateInstance(Admin user);
}
