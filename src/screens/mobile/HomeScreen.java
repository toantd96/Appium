package screens.mobile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.ui.ExpectedConditions;
import vn.vietinbank.efast.base.BaseScreen;
import vn.vietinbank.efast.base.ScrollDirection;

public class HomeScreen extends BaseScreen {

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeTable//XCUIElementTypeStaticText[@name=\"Chuyển tiền liên NH 24/7 qua TK\"]")
    @CacheLookup
    public WebElement chuyentienStk247Btn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"STK 24/7\"]/..")
    @CacheLookup
    public WebElement STK247Tab;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeImage[@name=\"avatar_mask\"]/following-sibling::XCUIElementTypeImage")
    @CacheLookup
    public WebElement avtBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Đăng xuất\"]/..")
    @CacheLookup
    public WebElement logoutBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Đồng ý\"]")
    @CacheLookup
    public WebElement yesBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Đóng\"]")
    @CacheLookup
    public WebElement clsoeBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"ic result home\"]")
    @CacheLookup
    public WebElement homeBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeTable//XCUIElementTypeStaticText[@name=\"Chuyển trong VietinBank\"]")
    @CacheLookup
    public WebElement transferWithinVietinbankBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeTable//XCUIElementTypeStaticText[@name='Chuyển tiền liên NH thường']")
    @CacheLookup
    public WebElement interbankTransfer;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeTable//XCUIElementTypeStaticText[@name='Chuyển tiền liên NH 24/7 qua số thẻ']")
    @CacheLookup
    public WebElement interbankTransfer247ViaCard;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeTable//XCUIElementTypeStaticText[@name='Lệnh chi']")
    @CacheLookup
    public WebElement paymentOrder;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Xem thêm\"]")
    @CacheLookup
    public WebElement moreBtn;

    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeStaticText[@name=\"Dịch vụ chuyển tiền\"])[1]")
    @CacheLookup
    public WebElement dichVuChuyenTienBtn;

    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeStaticText[@name=\"Dịch vụ thanh toán\"])[1]")
    @CacheLookup
    public WebElement paymentBtn;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"ic search app\"]")
    public WebElement iconSearch;

    @iOSXCUITFindBy(xpath = "//XCUIElementTypeImage[@name=\"ic_softOtp\"]/..")
    public WebElement softOTPButton;
    String paymentBtn_xpath = "(//XCUIElementTypeStaticText[@name=\"Dịch vụ thanh toán\"])[1]";
    String accountSoftOTP_xpath = "//XCUIElementTypeStaticText[@name='%s']";
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Mã tham chiếu\"]//following-sibling::XCUIElementTypeStaticText")
    public WebElement getOTP;
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name=\"Tiếp tục\"]")
    public WebElement continueButton;

    public HomeScreen(AppiumDriver driver) {
        super(driver);
    }

    public void clickAccount247Transfer() {
        withTimeOut(60).until(ExpectedConditions.elementToBeClickable(chuyentienStk247Btn)).click();
        withTimeOut(60).until(ExpectedConditions.elementToBeClickable(STK247Tab)).click();
    }

    public void clickTransferWithinVietinbank() {
        withTimeOut(60).until(ExpectedConditions.elementToBeClickable(transferWithinVietinbankBtn)).click();
    }

    public void clickInterbankTransfer() {
        withTimeOut(60).until(ExpectedConditions.elementToBeClickable(interbankTransfer)).click();
    }

    public void clickInterbankTransfer247ViaCard() {
        withTimeOut(60).until(ExpectedConditions.elementToBeClickable(interbankTransfer247ViaCard)).click();
    }

    public void clickPaymentOrder() {
        withTimeOut(60).until(ExpectedConditions.elementToBeClickable(paymentOrder)).click();
    }

    public void clickAvtButton() {
        withTimeOut(10).until(ExpectedConditions.elementToBeClickable(avtBtn)).click();
    }

    public void clickLogoutButton() {
        withTimeOut(10).until(ExpectedConditions.elementToBeClickable(logoutBtn)).click();
    }

    public void clickYesButton() {
        withTimeOut(10).until(ExpectedConditions.elementToBeClickable(yesBtn)).click();
    }

    public void clickCloseButton() {
        withTimeOut(10).until(ExpectedConditions.elementToBeClickable(clsoeBtn)).click();
    }

    public void clickHomeButton() {
        withTimeOut(10).until(ExpectedConditions.elementToBeClickable(homeBtn)).click();
    }

    public void clickMoreButton() {
        withTimeOut(60).until(ExpectedConditions.elementToBeClickable(moreBtn)).click();
    }

    public void clickDichVuChuyenTien() {
        withTimeOut(20).until(ExpectedConditions.elementToBeClickable(dichVuChuyenTienBtn)).click();
    }

    public void clickPayment() {
        withTimeOut(20).until(ExpectedConditions.elementToBeClickable(paymentBtn)).click();
    }

    public void clickIconSearch() {
        withTimeOut(10).until(ExpectedConditions.elementToBeClickable(iconSearch)).click();
    }

    public void clickSoftOTPButton() {
        withTimeOut(10).until(ExpectedConditions.elementToBeClickable(softOTPButton)).click();
    }

    public void selectAccount(String account) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        withTimeOut(10).until(ExpectedConditions.elementToBeClickable(mobileDriver.findElement(By.xpath(String.format(accountSoftOTP_xpath, account))))).click();
    }

    public void getOTP() {
        withTimeOut(10).until(ExpectedConditions.elementToBeClickable(getOTP)).getText();
    }

    public void clickContinueButton() {
        withTimeOut(10).until(ExpectedConditions.elementToBeClickable(continueButton)).click();
    }
}
