package dao;

import model.*;
import java.util.ArrayList;

public interface IDaoStudent extends IDao{

    public ArrayList<Student> getStudents();
    public Student getStudentById();
    public Student createStudent();

}
