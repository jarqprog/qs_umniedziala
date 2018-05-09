package dao;

import model.Student;
import model.Wallet;

public interface IDaoWallet {

    Wallet createWallet();

    Wallet importWallet(int userID);

    boolean exportWallet(Student student);

    boolean updateWallet(Student student);

    boolean exportStudentArtifact(int idArtifact, int idStudent);

    boolean updateStudentsArtifact(int idArtifact, int idStudent);
}
