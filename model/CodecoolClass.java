package model;

import java.util.ArrayList;

public class CodecoolClass extends Group{

    public CodecoolClass(String name){
        super(name);
    }
    
    public CodecoolClass(String name, ArrayList<Student> students, int groupId){
        super(name, students, groupId);
    }
}