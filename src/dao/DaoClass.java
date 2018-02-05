package dao;

import java.util.ArrayList;
import dao.DaoStudent;
import model.*;

public class DaoClass{

    public CodecoolClass createClass(String name){
        return new CodecoolClass(name);
    }

    public CodecoolClass createClass(int groupId, String name, ArrayList<Student> students){
        return new CodecoolClass(groupId, name, students);
    }

}