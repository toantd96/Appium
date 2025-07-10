package utils;

import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;

public class EnvironmentConfig {
    public static DatabaseConfig databaseConfig=null;
    public static EnvironmentVariables env = SystemEnvironmentVariables.createEnvironmentVariables();
    public static final String URL_TOUCH_POINT =
            EnvironmentSpecificConfiguration.from(env).getProperty("url_touch_point");

    public static final String URL_NEW_E_FAST =
            EnvironmentSpecificConfiguration.from(env).getProperty("url_new_e_fast");
    public static final String URL_E_OFFICE =
            EnvironmentSpecificConfiguration.from(env).getProperty("url_e_office");
    public static final String URL_E_FAST_REGISTER_ADMIN =
            EnvironmentSpecificConfiguration.from(env).getProperty("url_e_fast_register_admin");
    public static final String URL_E_FAST_REGISTER =
            EnvironmentSpecificConfiguration.from(env).getProperty("url_e_fast_register");
    public static final String URL_BANK_ADMIN = "https://webuat.vietinbank.vn/bankadmin";
    public static final String PLATFORM_NAME =
            EnvironmentSpecificConfiguration.from(env).getProperty("platformName");
    public static final String URL_VCOMS = EnvironmentSpecificConfiguration.from(env).getProperty("url_vcoms");

    //public static final String AUTOMATION_NAME =
     //       EnvironmentSpecificConfiguration.from(env).getProperty("automationName");
    //  public static final String DEVICE_NAME =Z
//      EnvironmentSpecificConfiguration.from(env).getProperty("deviceName");
//  public static String UDID = EnvironmentSpecificConfiguration.from(env).getProperty("udid");
    public static final String URL_PLUS_MONEY =
            EnvironmentSpecificConfiguration.from(env).getProperty("url_plus_money");
    public static final String URL_BASE_EKYC = "http://10.6.130.132:9189/";

//    public static final String NODE_PATH =
//            EnvironmentSpecificConfiguration.from(env).getProperty("notePath");
//    public static final String APPIUM_PATH =
//            EnvironmentSpecificConfiguration.from(env).getProperty("appiumPath");

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
    public static  final String folderDataEncryptor = "src/test/resources/dataEncryptor";
    public static  final Boolean clearDataDecrypt = true;
    public static final String account_ktv = "src/test/resources/dataEncryptor/acc_ktv.properties";
    public static final String account_ctk = "src/test/resources/dataEncryptor/acc_ctk.properties";
    public static final String account_gdv = "src/test/resources/dataEncryptor/acc_gdv.properties";
    public static final String account_ksv = "src/test/resources/dataEncryptor/acc_ksv.properties";
    public static final String account_gdvln = "src/test/resources/data/acc_gdvln.properties";
    public static final String account_ksvln = "src/test/resources/data/acc_ksvln.properties";
    public static final String account_rm_blol = "src/test/resources/dataEncryptor/acc_rm_blol.properties";
    public static final String account_tp_rm_blol =
            "src/test/resources/dataEncryptor/acc_tp_rm_blol.properties";
    public static final String cookies_config = "src/test/resources/data/cookies.properties";
    public static final String key_database = "src/test/resources/dataEncryptor/key_database.properties";
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
