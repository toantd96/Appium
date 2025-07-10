package screens.mobile;

import io.appium.java_client.AppiumDriver;
import net.thucydides.core.annotations.findby.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import vn.vietinbank.efast.base.BaseScreen;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class LoginScreen extends BaseScreen {

  @AndroidFindBy(id = "flipboard.app:id/first_launch_cover_continue")
  @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Đăng nhập\"]")
  public WebElement loginBtn;

  @iOSXCUITFindBy(xpath = "//XCUIElementTypeTextField[@value=\"Tên đăng nhập\"]")
  public WebElement uesname_txt;

  @iOSXCUITFindBy(xpath = "//XCUIElementTypeSecureTextField[@value=\"Mật khẩu\"]")
  public WebElement password_txt;

  @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Done\"]")
  public WebElement doneBtn_keyboard;

  @iOSXCUITFindBy(
      xpath = "//XCUIElementTypeStaticText[@name=\"Bạn muốn đăng nhập dưới tên khác?\"]/..")
  public WebElement loginWithDiffAccount_txt;

  @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Có\"]")
  public WebElement yesBtn;

  @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Không\"]")
  public WebElement noBtn;

  @iOSXCUITFindBy(xpath = "//XCUIElementTypeImage[@name=\"avatar_mask_login\"]")
  public WebElement mask_avatar_login;

  @iOSXCUITFindBy(xpath = "//XCUIElementTypeImage[@name=\"avatar_mask\"]")
  public WebElement mask_avatar;

  public LoginScreen(AppiumDriver driver) {
    super(driver);
  }

  public void clickLoginButton() {
    withTimeOut(60).until(ExpectedConditions.visibilityOf(loginBtn));
    loginBtn.click();
  }

  public void inputPassword(String password) {
    withTimeOut(5).until(ExpectedConditions.visibilityOf(password_txt));
    password_txt.sendKeys(password);
  }

  public void inputUsername(String username) {
    withTimeOut(5).until(ExpectedConditions.visibilityOf(uesname_txt));
    uesname_txt.sendKeys(username);
  }

  public void clickDoneButton() {
    doneBtn_keyboard.click();
  }

  public void waitLoadingLogoInvisibility() {
      try {
          Thread.sleep(1000);
      } catch (InterruptedException e) {
          throw new RuntimeException(e);
      }
      waitUntilInvisible(By.xpath("//XCUIElementTypeApplication[@name=\"VietinBank eFAST\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther"),
        120);
  }

  public void clickLoginWithDiffAccount() {
    withTimeOut(5).until(ExpectedConditions.visibilityOf(loginWithDiffAccount_txt));
    loginWithDiffAccount_txt.click();
  }

  public void clickYesBtn() {
    withTimeOut(5).until(ExpectedConditions.visibilityOf(yesBtn));
    yesBtn.click();
  }

  public void clickNoBtn() {
    withTimeOut(5).until(ExpectedConditions.visibilityOf(noBtn));
    noBtn.click();
  }

  public boolean isHaveAccount() {
    return checkElementDisplay(mask_avatar_login) || checkElementDisplay(mask_avatar);
  }
}
