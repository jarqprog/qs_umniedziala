package dao;

import model.CodecoolClass;
import model.Student;

import java.util.List;

public interface IDaoClass {

    CodecoolClass createClass(String name);

    CodecoolClass importClass(Integer classID);

    List<CodecoolClass> getAllClasses();

    boolean assignMentorToClass(Integer mentorId, Integer classId);

    boolean assignStudentToClass(Integer studentId, Integer classId);

    boolean updateMentorInClass(Integer mentorId, Integer classId);

    boolean unsignMentorFromClass(Integer mentorId);

    List<Student> getStudentsOfClass(Integer classID);

    CodecoolClass getMentorsClass(Integer mentorId);

    CodecoolClass getStudentClass(Integer studentId);
}
