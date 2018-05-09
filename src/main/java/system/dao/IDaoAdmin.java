package dao;

import model.Admin;

public interface IDaoAdmin {
    Admin createAdmin(String name, String password, String email);
    Admin importAdmin(int userId);
    boolean updateAdmin(Admin user);
}
