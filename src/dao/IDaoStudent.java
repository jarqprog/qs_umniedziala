package dao;

import model.Student;

public interface IDaoStudent {
    Student createInstance(String name, String password, String email);
    Student createInstance(int userId, String name, String password, String email);
    Student importInstance(int userId);
    boolean exportInstance(Student user);
    boolean updateInstance(Student user);
}
