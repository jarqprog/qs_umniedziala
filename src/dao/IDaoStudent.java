package dao;

import model.Student;

import java.util.ArrayList;

public interface IDaoStudent {
    Student createInstance(String name, String password, String email);

    Student createInstance(int userId, String name, String password, String email);

    Student importInstance(int studentId);

    Student importNewStudent(String userEmail);

    boolean exportInstance(Student student);

    boolean updateInstance(Student student);

    int getRoleID(String roleName);

    ArrayList<Student> getAllStudents();
}
