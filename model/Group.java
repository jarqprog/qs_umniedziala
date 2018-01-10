package model;

import java.util.ArrayList;

public abstract class Group{
    protected String name;
    protected ArrayList<Student> students;
    protected int groupId;

    public Group(){
        ;
    }

    public Group(String name, ArrayList<Student> students, int groupId){
        ;
    }
    public setGroupId(int groupId){}
    public int getGroupId(){}
    public setName(String name){}
    public String getName(){}
    public setStudents(ArrayLIst<Student> students){}
    public ArrayList<Student> getStudents(){}
    public boolean addMember(Student student){}
    public boolean removeMember(Student student){}
    public String toString(){}
    
}