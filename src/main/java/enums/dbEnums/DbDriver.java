package enums.dbEnums;

public enum DbDriver {

    SQLITE("org.sqlite.JDBC");

    private String driver;

    DbDriver(String driver) {
        this.driver = driver;
    }

    public String getDriver() {
        return driver;
    }
}
