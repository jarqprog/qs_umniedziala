package model;

import iterator.MyIterator;
import java.util.List;

public abstract class Group{
    private String name;
    private List<Student> students;
    private final int groupId;

    public Group(int groupId, String name, List<Student> students){
        this.groupId = groupId;
        this.name = name;
        this.students = students;
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

    public void setStudents(List<Student> students){
        this.students = students;
    }

    public List<Student> getStudents(){
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

    public String getBasicInfo(){
        return "Id: " + this.groupId + ", Name: " + this.name;
    }
}
