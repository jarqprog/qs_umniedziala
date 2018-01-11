package dao;

import model.*;
import java.util.ArrayList;

public interface IDaoStudent{

    public Student getStudentById(int id);
    public void createStudent(String name, String password, String email, int classId);
    public ArrayList <Student> importData();
    public void exportData(ArrayList <Student> updatedStudents);

}
