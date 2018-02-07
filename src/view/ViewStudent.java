package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ViewStudent extends View{

    private static ArrayList <String> studentOptions = new ArrayList <String>(Arrays.asList("1. See wallet details",
        "2. Buy artifact", "3. Check experience level", "4. Manage team", "5. See available quests",
        "6. See available artifacts", "7. Manage team", "0. Log out"));

    public ArrayList<String> getStudentOptions() {
        return studentOptions;
    }
    
}
