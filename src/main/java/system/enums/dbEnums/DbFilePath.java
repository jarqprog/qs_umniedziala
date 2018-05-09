package system.enums.dbEnums;

public enum DbFilePath {

    DB_SETUP_SCRIPT("src/main/resources/db.sql"),
    SQLITE_MAIN_DATABASE("src/main/resources/dbStruct.db"),
    SQLITE_TEST_DATABASE("src/main/resources/test.db"),
    POSTGRES_MAIN_DATABASE("src/main/resources/pgQS");

    private String filePath;

    DbFilePath(String filePath){
        this.filePath = filePath;
    }

    public String getPath(){
        return filePath;
    }
}
