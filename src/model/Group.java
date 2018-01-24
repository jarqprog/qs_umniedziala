package model;

import java.util.ArrayList;
import iterator.MyIterator;

public abstract class Group{
    protected String name;
    protected ArrayList<Student> students;
    protected int groupId;

    public Group(String name){
        this.name = name;
        this.students = new ArrayList<Student>();
    }

    public Group(int groupId, String name, ArrayList<Student> students){
        this.groupId = groupId;
        this.name = name;
        this.students = students;
    }

    public void setGroupId(int groupId){
        this.groupId = groupId;
    }

    public int getGroupId(){
        return this.groupId;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setStudents(ArrayList<Student> students){
        this.students = students;
    }

    public ArrayList<Student> getStudents(){
        return this.students;
    }

    public String toString(){
        MyIterator <Student> myIterator = new MyIterator<>(this.students);
        String allStudents = "Students in \"" + this.name + "\" group:\n";

        while(myIterator.hasNext()){
            allStudents += myIterator.next().toString() + "\n";
        }

        return allStudents;
    }    
}
