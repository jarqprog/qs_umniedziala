package system.dao;

import system.model.CodecoolClass;
import system.model.Student;

import java.util.List;

public interface IDaoClass {

    CodecoolClass createClass(String name);

    CodecoolClass importClass(int classID);

    List<CodecoolClass> getAllClasses();

    boolean assignMentorToClass(int mentorId, int classId);

    boolean assignStudentToClass(int studentId, int classId);

    boolean updateMentorInClass(int mentorId, int classId);

    boolean unsignMentorFromClass(int mentorId);

    List<Student> getStudentsOfClass(int classID);

    CodecoolClass getMentorsClass(int mentorId);

    CodecoolClass getStudentClass(int studentId);
}
