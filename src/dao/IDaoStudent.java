package dao;

import model.Student;

import java.util.ArrayList;

public interface IDaoStudent {
    Student createStudent(String name, String password, String email);

    Student createStudent(int userId, String name, String password, String email);

    Student importStudent(int studentId);

    Student importNewStudent(String userEmail);

    boolean exportStudent(Student student);

    boolean updateStudent(Student student);

    ArrayList<Student> getAllStudents();
}
