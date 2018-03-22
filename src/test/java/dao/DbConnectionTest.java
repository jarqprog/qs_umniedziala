package dao;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class DbConnectionTest {

    @Test
    public void getConnection() {
        assertNotNull(DbConnection.getConnection());
    }
}