package system.dao;

import system.model.Student;

import java.util.List;

public interface IDaoStudent {

    Student createStudent(String name, String password, String email);

    Student importStudent(int studentId);

    Student importNewStudent(String userEmail);

    boolean updateStudent(Student student);

    List<Student> getAllStudents();
}
