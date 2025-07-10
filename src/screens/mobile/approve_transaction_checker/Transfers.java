package screens.mobile.approve_transaction_checker;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.ui.ExpectedConditions;
import vn.vietinbank.efast.base.BasePage;
import vn.vietinbank.efast.base.BaseScreen;
import vn.vietinbank.efast.utils.component.ApproveTransaction;
import vn.vietinbank.efast.utils.component.TransferMoney;

import java.util.List;

public class Transfers extends BaseScreen {
  public Transfers(AppiumDriver driver) {
    super(driver);
  }

  @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Điện chuyển tiền\"]")
  @CacheLookup
  private WebElement transferMoney_txt;

  @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Từ chối\"]")
  @CacheLookup
  private WebElement denyBtn;

  @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Duyệt\"]")
  @CacheLookup
  private WebElement approveBtn;

  @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Xác nhận\"]")
  @CacheLookup
  private WebElement confirmBtn;

  @iOSXCUITFindBy(xpath = "//XCUIElementTypeSecureTextField")
  @CacheLookup
  private WebElement pinCode;

  @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Tiếp tục\"]")
  @CacheLookup
  private WebElement continueBtn;

  @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Bỏ qua\"]")
  @CacheLookup
  private WebElement skipBtn;

  String transaction_xpath =
          "//XCUIElementTypeStaticText[@name=\"%s\"]/ancestor::XCUIElementTypeCell";
  String pinCode_xpath =
          "//XCUIElementTypeSecureTextField";
  String confirmBtn_xpath = "//XCUIElementTypeButton[@name=\"Xác nhận\"]";
  String transferMoney_xpath = "//XCUIElementTypeStaticText[@name=\"Điện chuyển tiền\"]";
  String approveBtn_xpath = "//XCUIElementTypeButton[@name=\"Duyệt\"]";

  public void clickTransferMoney() {
    withTimeOut(60)
            .until(ExpectedConditions.elementToBeClickable(By.xpath(transferMoney_xpath))).click();
  }

  public void selectTransaction(String transactionId) {
    withTimeOut(20).until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(transaction_xpath, transactionId)))).click();

//    mobileDriver.findElement(By.xpath(String.format(transaction_xpath, transactionId))).click();
  }

  public void clickApproveButton() {
    withTimeOut(30).until(ExpectedConditions.elementToBeClickable(By.xpath(approveBtn_xpath))).click();
  }

  public void clickDenyButton() {
    withTimeOut(10).until(ExpectedConditions.elementToBeClickable(denyBtn)).click();
  }

  public void clickConfirmButton() {
    withTimeOut(60)
            .until(ExpectedConditions.elementToBeClickable(By.xpath(confirmBtn_xpath))).click();
  }

  public void inputPIN(String otp) {
    withTimeOut(20).until(ExpectedConditions.elementToBeClickable(By.xpath(pinCode_xpath)));
    List<WebElement> pinNumbers = mobileDriver.findElements(By.xpath(pinCode_xpath));
    int index = 0;
    for (String number : otp.split("")) {
      pinNumbers.get(index).sendKeys(number);
      index++;
    }
  }

  public void clickContinueButton() {
    withTimeOut(10).until(ExpectedConditions.elementToBeClickable(continueBtn)).click();
  }

  public void clickSkipButton() {
    try {
      Thread.sleep(1000);
//      if (skipBtn.isDisplayed()) {
//        withTimeOut(10).until(ExpectedConditions.elementToBeClickable(skipBtn)).click();
//      }
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
