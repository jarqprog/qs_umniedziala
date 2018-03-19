package dao;

import model.User;

public interface IDaoLogin {
    User getUser(String email, String password);

    String getRole(int id_role);
}
