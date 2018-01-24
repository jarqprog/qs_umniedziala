package model;

public class Mentor extends User{

    public Mentor(String name, String password, String email) {
        super(name, password, email);
    }

    public Mentor(int userId, String name, String password, String email) {
        super(userId, name, password, email);
    }
}
