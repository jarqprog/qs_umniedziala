package dao;

import java.util.ArrayList;
import model.*;

public class DaoStudent implements IDaoStudent{

    private static ArrayList <Student> students = new ArrayList<>();

    public void implementTestData() {
        createStudent("Jan", "haslo", "jan@mail.pl", 1);
        createStudent("Anna", "haslo", "anna@mail.pl", 1);
        createStudent("Filip", "haslo", "filip@mail.pl", 2);
        createStudent("Joanna", "haslo", "joanna@mail.pl", 2);
    }


    public void createStudent(String name, String password, String email, int classId){
        Student student = new Student(name, password, email, classId);
        DaoWallet daoWallet = new DaoWallet();
        Wallet wallet = daoWallet.getWallet();
        student.setWallet(wallet);
        students.add(student);
    }

    public Student getStudentById(int id){
        for(Student student: students){
            if(student.getUserId() == id){
                return student;
            }
        }
        return null;
    }

    public ArrayList <Student> getStudentsByClassId(int id){
        ArrayList <Student> studentsInClass = new ArrayList<Student>();
        for(Student student: students){
            if(student.getClassId() == id){
                studentsInClass.add(student);
            }
        }
        return studentsInClass;
    }

    public void exportData(ArrayList <Student> updatedStudents){
        students = updatedStudents;
    }

    public ArrayList <Student> importData(){
        return students;
    }
    private class DaoWallet{

        private Wallet wallet = new Wallet();

        public Wallet getWallet(){
            return wallet;
        }
        
    }

}