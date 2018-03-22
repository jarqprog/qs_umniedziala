package dao;

import model.Wallet;
import org.junit.Before;
import org.junit.Test;

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
    public void TestIf_WalletNoParametersConstructorIsNotNull() {
        Wallet wallet = dao.createWallet();
        assertNotNull(wallet);
    }

    @Test
    public void TestIf_WalletConstructorWithParametersWorks() {

        Wallet wallet = new Wallet(0, 0, )
    }

    @Test
    public void importWallet() {
    }


}