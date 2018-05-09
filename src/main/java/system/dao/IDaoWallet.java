package dao;

import model.Student;
import model.Wallet;

public interface IDaoWallet {

    Wallet createWallet(Student student);

    Wallet importWallet(int userID);

    boolean updateWallet(Student student);

    boolean exportStudentArtifact(int idArtifact, int idStudent);

    boolean updateStudentsArtifact(int idArtifact, int idStudent);
}
