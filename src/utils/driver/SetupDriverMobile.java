package utils.driver;

import static io.appium.java_client.remote.MobilePlatform.IOS;
import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;
import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.useDriver;
import static vn.vietinbank.efast.utils.EnvironmentConfig.*;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import java.io.File;
import java.net.URL;

import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.Constant;
import utils.DataInputHelper;
import utils.EnvironmentConfig;

public class SetupDriverMobile {

  private static AppiumDriver appiumDriver;
  static String ADDRESS = "127.0.0.1";
  static AppiumDriverLocalService service;
  static Logger logger = LogManager.getLogger(SetupDriverMobile.class);
  static String appiumConfigPath = "src/test/resources/configs/"+Constant.OS + "/appium_config.properties";

  public SetupDriverMobile() {}

  public static void stopAppiumServer() {
    service.stop();
  }

  private static void startAppiumServer() {
    logger.info("Starting appium server");
    try {
      service =
          AppiumDriverLocalService.buildService(
              new AppiumServiceBuilder()
                  .withIPAddress(ADDRESS)
                  .usingAnyFreePort()
                  .usingDriverExecutable(new File(getNodePath()))
                  .withAppiumJS(new File(getAppiumPath())));
      service.start();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static URL getUrl() {
    return service.getUrl();
  }

  public static AppiumDriver newDriverMobile() {
    startAppiumServer();
    logger.info("Starting appium driver {}", PLATFORM_NAME);
    AppiumDriver driver;
    try {
      DesiredCapabilities dc = new DesiredCapabilities();
      dc.setCapability("platformName", PLATFORM_NAME);
      //            dc.setCapability("appium:platformVersion", platformVersion);
      dc.setCapability("appium:udid", getUDIDFromEnvironment());
//      dc.setCapability("appium:deviceName", DEVICE_NAME);
      dc.setCapability("noReset", "false");
      dc.setCapability("appium:usePrebuiltWDA", "true");
      dc.setCapability("appium:newCommandTimeout",3600);
      if (PLATFORM_NAME.equals(IOS)) {
        String BUNDLE_ID = EnvironmentSpecificConfiguration.from(env).getProperty("bundleId");
        dc.setCapability("appium:bundleId", BUNDLE_ID);
        dc.setCapability("appium:automationName", "XCUITest");
        driver = new IOSDriver(getUrl(), dc);
      } else {
        String APP_ACTIVITY = EnvironmentSpecificConfiguration.from(env).getProperty("appActivity");
        String APP_PACKAGE = EnvironmentSpecificConfiguration.from(env).getProperty("appPackage");
        dc.setCapability("appium:appPackage", APP_PACKAGE);
        dc.setCapability("appium:appActivity", APP_ACTIVITY);
        dc.setCapability("appium:automationName", "UIAutomator2");
        driver = new AndroidDriver(getUrl(), dc);
      }
      appiumDriver = driver;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    logger.info("Driver {}: {}", PLATFORM_NAME, driver);
    useDriver(driver);
    return driver;
  }

  public static AppiumDriver newDriverMobile(String appName) {
    startAppiumServer();
    logger.info("Starting appium driver {}", PLATFORM_NAME);
    AppiumDriver driver;
    try {
      DesiredCapabilities dc = getDesiredCapabilities(appName);

      if (PLATFORM_NAME.equals(IOS)) {
        driver = new IOSDriver(getUrl(), dc);
      } else {
        driver = new AndroidDriver(getUrl(), dc);
      }
      appiumDriver = driver;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    logger.info("Driver {}: {}", PLATFORM_NAME, driver);
    useDriver(driver);
    return driver;
  }

  public static DesiredCapabilities getDesiredCapabilities(String appName){
    DesiredCapabilities dc = new DesiredCapabilities();
    dc.setCapability("platformName", PLATFORM_NAME);
    //            dc.setCapability("appium:platformVersion", platformVersion);
    dc.setCapability("appium:udid", getUDIDFromEnvironment());
//    dc.setCapability("appium:deviceName", DEVICE_NAME);
    dc.setCapability("noReset", "false");
    dc.setCapability("appium:usePrebuiltWDA", "true");
    dc.setCapability("appium:newCommandTimeout",3600);
    if (PLATFORM_NAME.equals(IOS)) {
      String configBUNDLE_ID = appName.isEmpty()?"bundleId":"bundleId_"+appName;
      String BUNDLE_ID = EnvironmentSpecificConfiguration.from(env).getProperty(configBUNDLE_ID);
      dc.setCapability("appium:bundleId", BUNDLE_ID);
      dc.setCapability("appium:automationName", "XCUITest");
    } else {
      String configAPP_PACKAGE = appName.isEmpty()?"appPackage":"appPackage_"+appName;
      String configAPP_ACTIVITY = appName.isEmpty()?"appActivity":"appActivity_"+appName;
      String APP_ACTIVITY = EnvironmentSpecificConfiguration.from(env).getProperty(configAPP_ACTIVITY);
      String APP_PACKAGE = EnvironmentSpecificConfiguration.from(env).getProperty(configAPP_PACKAGE);
      dc.setCapability("appium:appPackage", APP_PACKAGE);
      dc.setCapability("appium:appActivity", APP_ACTIVITY);
      dc.setCapability("appium:automationName", "UIAutomator2");
    }
    return dc;
  }

  public static void closeApplication() {
    String appID;
    if (PLATFORM_NAME.equals(IOS)) {
      appID = (String) appiumDriver.getCapabilities().getCapability("appium:bundleId");
    } else {
      appID = (String) appiumDriver.getCapabilities().getCapability("appium:appPackage");
    }
    if (appID != null) ((InteractsWithApps) appiumDriver).terminateApp(appID);
    appiumDriver.quit();
    stopAppiumServer();
  }

  public static String getNodePath(){
    //String appiumConfigPath = "src/test/resources/configs/"+Constant.OS + "/appium_config.properties";

    DataInputHelper dataInputHelper = new DataInputHelper();
    String nodePath = dataInputHelper.getPropertyValue("nodePath", appiumConfigPath);

    return nodePath;
  }
  public static String getAppiumPath(){
    //String appiumConfigPath = "src/test/resources/configs/"+Constant.OS + "/appium_config.properties";

    DataInputHelper dataInputHelper = new DataInputHelper();
    String appiumPath = dataInputHelper.getPropertyValue("appiumPath",appiumConfigPath);
    if(Constant.OS.contentEquals(Constant.WINDOW)){
      appiumPath = appiumPath.replace("user.name",System.getProperty("user.name"));
    }
    return appiumPath;
  }
}
