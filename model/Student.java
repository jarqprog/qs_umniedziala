package model;

public class Student extends User{
    private int classId;
    private int teamId;
    private Wallet wallet;

    public Student(String name, String password, String email, int classId) {
        super(name, password, email);
        this.classId = classId;
    }

    public Student(String name, String password, String email, int classId, int teamId) {
        this(name, password, email, classId);
        this.teamId = teamId;
    }
}
