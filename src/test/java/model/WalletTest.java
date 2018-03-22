package model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class WalletTest {

   private Student student;
   private Artifact artifact;

    @Before
    public void setup(){
        student = new Student("Piotr Gryzlo", "123", "pg@.cc.com");
        artifact = new Artifact(1, "Stick of truth", 666,"Point only a truth", "Individual");
        student.getWallet().addNewArtifact(artifact);
    }

    @Test
    public void setAllCoins() {
        student.getWallet().setAllCoins(50);
        assertEquals(50, student.getWallet().getAllCoins());

    }

    @Test
    public void getAllCoins() {
        assertEquals(0, student.getWallet().getAllCoins()) ;
    }

    @Test
    public void setAvailableCoins() {
        student.getWallet().setAvailableCoins(50);
        assertEquals(50, student.getWallet().getAvailableCoins());
    }

    @Test
    public void getAvailableCoins() {
        student.getWallet().setAvailableCoins(-5456);
       assertEquals(-5456, student.getWallet().getAvailableCoins());

    }

    @Test
    public void addCoins() {
        student.getWallet().addCoins(1);
        assertEquals(1, student.getWallet().getAllCoins());
    }

    @Test
    public void getNewArtifacts() {
        List<Artifact> tempy = new ArrayList<>();
        tempy.add(artifact);
        assertEquals(tempy, student.getAllNewArtifacts());

    }

    @Test
    public void getUsedArtifacts() {
        List<Artifact> tempy = new ArrayList<>();
        tempy.add(artifact);
        student.getWallet().addUsedArtifact(artifact);
        assertEquals(tempy,student.getWallet().getUsedArtifacts());


    }

    @Test
    public void addNewArtifact() {
        artifact = new Artifact (2, "Blind glasses",123,
                                "when are worn you are blind", "Individual");
        student.getWallet().addNewArtifact(artifact);
        assertEquals(2, student.getWallet().getNewArtifacts().size());

    }

    @Test
    public void removeNewArtifact() {
        student.getWallet().removeNewArtifact(1);
        assertEquals(0, student.getWallet().getNewArtifacts().size());

    }

    @Test
    public void hasEnoughCoins() {
        student.getWallet().setAvailableCoins(-3);
        assertFalse(student.getWallet().hasEnoughCoins(50));
    }

    @Test
    public void subtractCoins() {
        student.getWallet().setAvailableCoins(50);
        student.getWallet().subtractCoins(50);
        assertEquals(0, student.getWallet().getAvailableCoins());

    }

    @Test
    public void addUsedArtifact() {
        student.getWallet().addUsedArtifact(artifact);
        assertEquals(1, student.getWallet().getUsedArtifacts().size());
    }


}