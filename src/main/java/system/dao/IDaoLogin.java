package system.dao;

import system.model.User;

public interface IDaoLogin {
    User getUser(String email, String password);

    String getRole(int roleId);
}
