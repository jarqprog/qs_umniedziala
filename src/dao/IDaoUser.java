package dao;

public interface IDaoUser <T>{
    T createInstance(String name, String password, String email);
    T createInstance(int userId, String name, String password, String email);
    T importInstance(int userId);
    boolean exportInstance(T user);
    boolean updateInstance(T user);
}


