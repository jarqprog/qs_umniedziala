package dao;

import model.CodecoolClass;
import model.Student;

import java.util.ArrayList;

public interface IDaoClass {
    CodecoolClass createClass(String name);

    CodecoolClass createClass(int groupId, String name, ArrayList<Student> students);

    CodecoolClass importClass(Integer classID);

    boolean exportClass(CodecoolClass codecoolClass);

    ArrayList<CodecoolClass> getAllClasses();

    void assignMentorToClass(Integer mentorId, Integer classId);

    void assignStudentToClass(Integer studentId, Integer classId);

    void updateMentorInClass(Integer mentorId, Integer classId);

    void unsignMentorFromClass(Integer mentorId);

    ArrayList<Student> getStudentsOfClass(Integer classID);

    CodecoolClass getMentorsClass(Integer mentorId);
}
