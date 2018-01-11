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

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        String userInfo = super.toString();

        int noClassID = 0;
        sb.append(userInfo);
        if (classId != noClassID) {
            sb.append(", Class ID: " + getClassId());
        }
        return sb.toString();
    }
}
