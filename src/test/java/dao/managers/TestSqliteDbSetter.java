package dao.managers;

import dao.DbFilePath;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class TestSqliteDbSetter implements TestDbSetter {

    private Connection connection;
    private DbFilePath testDbFilePath;
    private DbFilePath dbSetupScript;

    public static TestDbSetter getInstance(Connection connection, DbFilePath testDbFilePath, DbFilePath dbSetupScript) {
        return new TestSqliteDbSetter(connection, testDbFilePath, dbSetupScript);
    }

    private TestSqliteDbSetter(Connection connection, DbFilePath testDbFilePath, DbFilePath dbSetupScript) {
        this.connection = connection;
        this.testDbFilePath = testDbFilePath;
        this.dbSetupScript = dbSetupScript;
    }


    @Override
    public void setDatabase() throws IOException {
        prepareTestDatabaseFile();
        updateDatabaseWithSqlFile();
    }


    private Boolean checkIfTestDatabaseExists(){
        return new File(testDbFilePath.getPath()).exists();
    }

    private void prepareTestDatabaseFile() throws IOException {
        if(! checkIfTestDatabaseExists()){
                File f = new File(testDbFilePath.getPath());
                f.createNewFile();
        }
        clearFile();
    }

    private void clearFile() throws FileNotFoundException {
        new FileOutputStream(testDbFilePath.getPath());
    }

    private void updateDatabaseWithSqlFile() throws FileNotFoundException {
        String delimiter = ";";
        Scanner scanner;
        File sqlFile = new File(dbSetupScript.getPath());
        scanner = new Scanner(sqlFile).useDelimiter(delimiter);
        try (Statement currentStatement = connection.createStatement()) {
            connection.setAutoCommit(false);
            while(scanner.hasNext()) {
                // build transaction
                String rawStatement = scanner.next().trim();  // trim() to avoid trash data
                if(rawStatement.length() > 2) {
                    currentStatement.addBatch(rawStatement);
                }
            }
            if(currentStatement != null) {
                // finalize transaction
                currentStatement.executeBatch();
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        scanner.close();
    }
}
