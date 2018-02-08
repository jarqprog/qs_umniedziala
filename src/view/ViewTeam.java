package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ViewTeam extends View{

    private static ArrayList <String> studentOptions = new ArrayList <String>(Arrays.asList("1. Buy artifact",
        "2. Split money", "0. Go back"));

    public ArrayList <String> getStudentOptions(){ return studentOptions;}
    
}
