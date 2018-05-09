package enums.dbEnums;

public enum DbDriver {

    SQLITE("org.sqlite.JDBC"),
    POSTGRES("org.postgresql.Driver");

    private String driver;

    DbDriver(String driver) {
        this.driver = driver;
    }

    public String getDriver() {
        return driver;
    }
}
