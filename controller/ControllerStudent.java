package controller;

import model.Student;
import view.ViewStudent;

public class ControllerStudent{

    private ViewStudent viewStudent;
    private Student student;

    public ControllerStudent(Student student) {
        this.viewStudent = new ViewStudent();
        this.student = student;
    }

}
