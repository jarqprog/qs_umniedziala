package terminal.controller.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ViewStudent extends View{

    private List <String> studentOptions = new ArrayList <String>(Arrays.asList("1. See wallet details",
        "2. Buy artifact", "3. Check experience level", "4. Manage team", "0. Log out"));

    public List<String> getStudentOptions() {
        return studentOptions;
    }
    
}
