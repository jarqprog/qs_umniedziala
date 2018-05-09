package system.dao;

import system.model.Student;
import system.model.Wallet;

public interface IDaoWallet {

    Wallet createWallet(Student student);

    Wallet importWallet(int userID);

    boolean updateWallet(Student student);

    boolean exportStudentArtifact(int idArtifact, int idStudent);

    boolean updateStudentsArtifact(int idArtifact, int idStudent);
}
