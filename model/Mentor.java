package model;

public class Mentor extends User{
    private int classId;

    public Mentor(String name, String password, String email) {
        super(name, password, email);
    }

    public Mentor(String name, String password, String email, int userId, int classId) {
        super(name, password, email, userId);
        this.classId = classId;
    }
}
