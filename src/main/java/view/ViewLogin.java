package view;

import java.util.Arrays;
import java.util.ArrayList;

public class ViewLogin extends View{

    private ArrayList <String> LoginOptions = new ArrayList <String>(Arrays.asList("1. Log in", "0. Quit"));

    public ArrayList<String> getLoginOptions() {
        return LoginOptions;
    }

}