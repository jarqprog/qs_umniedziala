package dao;

import java.util.ArrayList;
import model.*;

public class DaoStudent implements IDaoStudent{

    private static ArrayList <Student> students;

    public ArrayList <Student> getStudents() { return students; }

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