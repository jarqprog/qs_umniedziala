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

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        String userInfo = super.toString();
        int noTeamID = 0;
        sb.append(userInfo);
        sb.append(", Class ID: " + getClassId());
        if (teamId != noTeamID) {
            sb.append(", Team ID: " + getClassId());
        }
        return sb.toString();
    }
}
