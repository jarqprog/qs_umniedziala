package dao;

public interface IDao <T>{
    T createInstance(String name, String password, String email);
    T createInstance(int userId, String name, String password, String email);
    T importInstance(int studentId);
    void exportInstance(T student);
    void updateInstance(T student);
}


