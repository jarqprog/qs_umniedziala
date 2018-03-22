package dao;

import model.Artifact;
import model.Wallet;
import org.junit.Test;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DaoWalletTest extends DaoTest{

    private DaoWallet dao;

    public void setUp() {
        dao = new DaoWallet();
    }

    public void tearDown() {
        dao = null;
    }

    @Test
    public void TestIf_createWalletIsNotNull() {
        Wallet wallet = dao.createWallet();
        assertNotNull(wallet);
    }

    @Test
    public void TestIf_createWalletWithParametersWorks() {
        List<Artifact> artifacts = new ArrayList<>();
        List<Artifact> usedArtifacts = new ArrayList<>();
        int counter = 5;
        while(counter > 0){
            artifacts.add(mock(Artifact.class));
            usedArtifacts.add(mock(Artifact.class));
            counter--;
        }
       Wallet wallet =  dao.createWallet(0, 0, artifacts, usedArtifacts);
       assertNotNull(wallet);
       assertEquals(5, wallet.getNewArtifacts().size());
    }

    @Test
    public void TestIf_importWallet_returnCorrectObjectFromDB() {
        Wallet wallet = dao.importWallet(6);
        assertNotNull(wallet);
        assertEquals(1, wallet.getUsedArtifacts().size());
        assertEquals("Item id: 3, name: Time Travel, value: 750, " +
                                "description: extend SI week assignment deadline by one day, " +
                                "type: individual",
                        wallet.getNewArtifacts().get(0).toString());

    }




}