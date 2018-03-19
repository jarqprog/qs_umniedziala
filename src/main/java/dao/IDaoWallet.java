package dao;

import model.Artifact;
import model.Student;
import model.Wallet;

import java.util.List;

public interface IDaoWallet {
    Wallet createWallet();

    Wallet createWallet(int allCoins, int availableCoins, List<Artifact> newArtifacts,
                        List<Artifact> usedArtifacts);

    Wallet importWallet(int userID);

    void exportWallet(Student student);

    void updateWallet(Student student);

    void exportStudentArtifact(int idArtifact, int idStudent);

    void updateStudentsArtifact(int idArtifact, int idStudent);
}
