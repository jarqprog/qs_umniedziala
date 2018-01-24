package model;

public class Admin extends User{
    public Admin(String name, String password, String email) {
        super(name, password, email);
    }

    public Admin(int userId, String name, String password, String email) {
        super(userId, name, password, email);
    }
}
