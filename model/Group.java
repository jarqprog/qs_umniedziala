package model;

import java.util.ArrayList;
import iterator.MyIterator;

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

    public boolean addMember(Student student){
        return this.students.add(student);
    }

    public boolean removeMember(Student student){
        return this.students.remove(student);
    }

    public String toString(){
        MyIterator <Student> myIterator = new MyIterator<>(this.students);
        String allStudents = "Students:\n";

        while(myIterator.hasNext()){
            allStudents += myIterator.next().toString() + "\n";
        }

        return allStudents;
    }    
}
