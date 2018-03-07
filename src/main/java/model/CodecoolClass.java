package model;

import java.util.ArrayList;

public class CodecoolClass extends Group{

    public CodecoolClass(String name){
        super(name);
    }
    
    public CodecoolClass(int groupId, String name, ArrayList<Student> students){
        super(groupId, name, students);
    }
}