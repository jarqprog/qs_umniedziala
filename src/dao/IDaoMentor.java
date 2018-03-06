package dao;

import model.Mentor;

public interface IDaoMentor {
    Mentor createInstance(String name, String password, String email);
    Mentor createInstance(int userId, String name, String password, String email);
    Mentor importInstance(int userId);
    boolean exportInstance(Mentor user);
    boolean updateInstance(Mentor user);
}
