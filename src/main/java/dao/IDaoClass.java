package dao;

import model.CodecoolClass;
import model.Student;

import java.util.List;

public interface IDaoClass {
    CodecoolClass createClass(String name);

    CodecoolClass createClass(int groupId, String name, List<Student> students);

    CodecoolClass importClass(Integer classID);

    boolean exportClass(CodecoolClass codecoolClass);

    List<CodecoolClass> getAllClasses();

    void assignMentorToClass(Integer mentorId, Integer classId);

    void assignStudentToClass(Integer studentId, Integer classId);

    void updateMentorInClass(Integer mentorId, Integer classId);

    void unsignMentorFromClass(Integer mentorId);

    List<Student> getStudentsOfClass(Integer classID);

    CodecoolClass getMentorsClass(Integer mentorId);

    CodecoolClass getStudentClass(Integer studentId);
}
