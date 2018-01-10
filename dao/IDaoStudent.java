package dao;

import model.*;
import java.util.ArrayList;

public interface IDaoStudent{

    public ArrayList<Student> getStudents();
    public Student getStudentById(int id);
    public Student createStudent(String name, String password, String email, int classId);
    public void importData(Student student);
    public void exportData();

}
