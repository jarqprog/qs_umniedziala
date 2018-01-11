package dao;

import java.util.ArrayList;
import model.*;

public class DaoStudent implements IDaoStudent{

    private static ArrayList <Student> students;

    public void implementTestData() {
        createStudent("Jan", "haslo", "jan@mail.pl", 1);
        createStudent("Anna", "haslo", "anna@mail.pl", 1);
        createStudent("Filip", "haslo", "filip@mail.pl", 2);
        createStudent("Joanna", "haslo", "joanna@mail.pl", 2);
    }


    public void createStudent(String name, String password, String email, int classId){
        students.add(new Student(name, password, email, classId));
    }

    public Student getStudentById(int id){
        for(Student student: students){
            if(student.getUserId() == id){
                return student;
            }
        }
        return null;
    }

    public void exportData(ArrayList <Student> updatedStudents){
        students = updatedStudents;
    }

    public ArrayList <Student> importData(){
        return students;
    }
    public class DaoWallet{
        
    }

}