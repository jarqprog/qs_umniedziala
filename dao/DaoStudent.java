package dao;

import java.util.ArrayList;
import model.*;

public class DaoStudent implements IDaoStudent{

    private static ArrayList <Student> students;

    public ArrayList <Student> getStudents() { return students; }

    public void DaoStudent(){
        Student s1 = new Student("Jan", "haslo", "jan@mail.pl", 1);
        Student s2 = new Student("Anna", "haslo", "anna@mail.pl", 1);
        Student s3 = new Student("Filip", "haslo", "filip@mail.pl", 2);
        Student s4 = new Student("Joanna", "haslo", "joanna@mail.pl", 2);
        students.add(s1);
        students.add(s2);
        students.add(s3);
        students.add(s4);
    }

    public Student createStudent(String name, String password, String email, int classId){
        Student student = new Student(name, password, email, classId);
        return student;
    }

    public Student getStudentById(int id){
        for(Student student: students){
            if(student.getUserId() == id){
                return student;
            }
        }
        return null;
    }

    public void exportData(){}

    public void importData(Student student){
        students.add(student);
    }
    public class DaoWallet{
        
    }

}