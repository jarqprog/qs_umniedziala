package dao;

import model.Artifact;
import model.Student;
import model.Wallet;

import java.util.ArrayList;

public interface IDaoWallet {
    Wallet createWallet();

    Wallet createWallet(int allCoins, int availableCoins, ArrayList<Artifact> newArtifacts,
                        ArrayList<Artifact> usedArtifacts);

    Wallet importWallet(int userID);

    void exportWallet(Student student);

    void updateWallet(Student student);

    void exportStudentArtifact(int idArtifact, int idStudent);

    void updateStudentsArtifact(int idArtifact, int idStudent);
}
