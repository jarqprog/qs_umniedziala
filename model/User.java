package model;

public abstract class User{
    protected String name;
    protected String password;
    protected String email;
    protected int userId;
    protected static int COUNTER;

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public User(String name, String password, String email, int userId) {
        User(name, password, email);
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getUserId() {
        return userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static void setIdCounter(int id) {

    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Name: " + getName());
        sb.append("\nEmail: " + getEmail());
        sb.append("\nID: " + getUserId());

        return sb.toString();

    }
}
