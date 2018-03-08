package model;

import java.util.List;

public class CodecoolClass extends Group{

    public CodecoolClass(String name){
        super(name);
    }
    
    public CodecoolClass(int groupId, String name, List<Student> students){
        super(groupId, name, students);
    }
}