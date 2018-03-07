package dao;

import model.Admin;

public interface IDaoAdmin {
    Admin createAdmin(String name, String password, String email);
    Admin createAdmin(int userId, String name, String password, String email);
    Admin importAdmin(int userId);
    boolean exportAdmin(Admin user);
    boolean updateAdmin(Admin user);
}
