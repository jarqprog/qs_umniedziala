package view;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class ViewLogin extends View{

    private List <String> LoginOptions = new ArrayList <String>(Arrays.asList("1. Log in", "0. Quit"));

    public List<String> getLoginOptions() {
        return LoginOptions;
    }

}