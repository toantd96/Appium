package steps;

import com.vladsch.flexmark.util.collection.OrderedMultiMap;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.serenitybdd.annotations.Steps;
import org.openqa.selenium.chrome.ChromeOptions;
import vn.vietinbank.efast.base.BasePage;
import vn.vietinbank.efast.base.BaseScreen;
import vn.vietinbank.efast.utils.Constant;
import vn.vietinbank.efast.utils.OTPHelper;
import vn.vietinbank.efast.utils.driver.DriverHelper;
import vn.vietinbank.efast.utils.driver.SetupDriverMobile;

public class BaseSteps {

  public static AppiumDriver appiumDriver;

  @Given("^Chuyển sang \"([^\"]*)\"$")
  public void switchDriver(String driver) {
    if (driver.equals("web")) {
      BasePage.webDriver = DriverHelper.chromeDriver();
    } else if (driver.equals("chrome")) {
      BasePage.webDriver = DriverHelper.chromeDriver();
    } else if (driver.equals("firefox")) {
      BasePage.webDriver = DriverHelper.firefoxDriver();
    } else if (driver.equals("edge")) {
      BasePage.webDriver = DriverHelper.edgeDriver();
    } else if (driver.equals("ie")) {
      BasePage.webDriver = DriverHelper.ieDriver();
    } else if (driver.equals("mobile")) {
      appiumDriver = SetupDriverMobile.newDriverMobile();
    } else if (driver.contains("mobile app ")) {
      String appName = driver.replace("mobile app ", "app_");
      appiumDriver = SetupDriverMobile.newDriverMobile(appName);
    }
  }

  //  public static AppiumDriver getAppiumDriver() {
  //    return appiumDriver;
  //  }
  @Then("^Thoát trình duyệt$")
  @Then("^Quit browser$")
  public void closeBrowser() {
    BasePage.webDriver.close();
  }

  @Then("^Quit app")
  public void closeApp() {
    SetupDriverMobile.closeApplication();
  }

  @Then("Lấy OTP gửi từ {string} đến mobile")
  public void getSmsOTPFrom(String address) {
    Constant.OTP = new OTPHelper().waitAndGetOTP(appiumDriver, address, 3 * 60);
  }
}
