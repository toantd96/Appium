package utils;

import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;

public class EnvironmentConfig {
    public static DatabaseConfig databaseConfig=null;
    public static EnvironmentVariables env = SystemEnvironmentVariables.createEnvironmentVariables();

    public static final String PLATFORM_NAME =
            EnvironmentSpecificConfiguration.from(env).getProperty("platformName");
    public static final String URL_VCOMS = EnvironmentSpecificConfiguration.from(env).getProperty("url_vcoms");



    public static final String WEB_DRIVER_PATH = "src/test/resources/webdriver/";

    public static final String DATA_PATH = "src/test/resources/data/";
    public static final String APP_PATH = "src/test/resources/app/";
    public static final String EDGE_EXE_PATH =
            "C:/Program Files (x86)/Microsoft/Edge/Application/msedge.exe";
    public static final String IE_EXE_PATH = "C:/Program Files/Internet Explorer/iexplore.exe";
    public static final String env_var =
            EnvironmentSpecificConfiguration.from(env).getProperty("env_var");
    public static final String LANGUAGE =
            EnvironmentSpecificConfiguration.from(env).getProperty("language");
    
    public static final String VN = "src/test/resources/data/language/VN.properties";
    public static final String EN = "src/test/resources/data/language/EN.properties";
    public static final String urlDatabase =
            EnvironmentSpecificConfiguration.from(env).getProperty("url_database");
    public static final String userNameDatabase =
            EnvironmentSpecificConfiguration.from(env).getProperty("username_database");
    public static final String passwordDatabase =
            EnvironmentSpecificConfiguration.from(env).getProperty("password_database");
    public static final String classDriverNameDatabase =
            EnvironmentSpecificConfiguration.from(env).getProperty("classDriver_database");

    public static String getLanguage(String key) {
        String value = "";
        if (LANGUAGE.equals("VN")) {
            value = new DataInputHelper().getPropertyValue(key, VN);
        } else if (LANGUAGE.equals("EN")) {
            value = new DataInputHelper().getPropertyValue(key, EN);
        }
        return value;
    }

    public static EnvironmentVariables ENV = SystemEnvironmentVariables.createEnvironmentVariables();

    public static String getProperty(String property) {
        String value = "";
        try {
            value = EnvironmentSpecificConfiguration.from(ENV).getProperty(property);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String getUDIDFromEnvironment() {
        String udid_mobile = "";
        try {
            if (System.getProperty("udid") == null || System.getProperty("udid").isEmpty()) {
                udid_mobile = EnvironmentSpecificConfiguration.from(env).getProperty("udid");
            } else {
                udid_mobile = System.getProperty("udid");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return udid_mobile;
    }
}
