package dao;

import java.util.ArrayList;
import model.*;

public class DaoClass implements IDaoClass{

    private static ArrayList <CodecoolClass> classes = new ArrayList<>();

    public void createClass(String name){
        classes.add(new CodecoolClass(name));
    }

    public CodecoolClass getClassById(int id){
        for(CodecoolClass codecoolClass: classes){
            if(codecoolClass.getGroupId() == id){
                return codecoolClass;
            }
        }
        return null;
    }

    public void exportData(ArrayList <CodecoolClass> updatedCodecoolClass){
        classes = updatedCodecoolClass;
    }

    public ArrayList <CodecoolClass> importData(){
        return classes;
    }

}