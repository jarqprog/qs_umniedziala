package enums.dbEnums;

public enum DbUrl {

    DATABASE_MAIN_URL("jdbc:sqlite:src/main/resources/dbStruct.db"),
    DATABASE_TEST_URL("jdbc:sqlite:src/main/resources/test.db");

    private String url;

    DbUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
