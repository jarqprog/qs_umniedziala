package model;

public abstract class User{
    private String name;
    private String password;
    private String email;
    private int userId;

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public User(int userId, String name, String password, String email) {
        this(name, password, email);
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

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("ID: " + getUserId());
        sb.append(", Name: " + getName());
        sb.append(", Email: " + getEmail());

        return sb.toString();

    }
}
