package dao;

public interface IDaoUser <T>{
    T createInstance(String name, String password, String email);
    T createInstance(int userId, String name, String password, String email);
    T importInstance(int userId);
    void exportInstance(T user);
    void updateInstance(T user);
}


