package model;

import java.util.ArrayList;

public abstract class Group{
    protected String name;
    protected ArrayList<Student> students;
    protected int groupId;
    protected static int counterId = 0;

    public Group(String name){
        this.name = name;
        this.students = new ArrayList<Student>();
        this.groupId = ++counterId;
    }

    public Group(String name, ArrayList<Student> students, int groupId){
        this.name = name;
        this.students = students;
        this.groupId = groupId;
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