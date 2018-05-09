package manager.database;

import java.util.Properties;

public interface DatabaseConfiguration {

    String getUrl();
    String getDriver();
    String getFilepath();
    Properties getProperties();
}
