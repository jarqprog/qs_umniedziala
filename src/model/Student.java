package model;

public class Student extends User{
    private Wallet wallet;

    public Student(int userId, String name, String password, String email, Wallet wallet) {

        super(userId, name, password, email);
        this.wallet = wallet;
    }

    public Student(String name, String password, String email) {

        super(name, password, email);
        this.wallet = new Wallet();
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

}
