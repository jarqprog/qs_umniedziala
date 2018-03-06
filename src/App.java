import controller.ControllerLogin;
import view.ViewLogin;

public class App{
    public static void main(String [] args){
        ViewLogin viewLogin = new ViewLogin();
        ControllerLogin login = new ControllerLogin(viewLogin);
        login.runMenu();
    }
}