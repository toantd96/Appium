package steps.mobile;

import io.cucumber.java.en.When;
import screens.mobile.HomeScreen;
import screens.mobile.LoginScreen;

import static BaseSteps.appiumDriver;

public class HomeScreenStep {
  HomeScreen homeScreen = new HomeScreen(appiumDriver);
  LoginScreen loginScreen = new LoginScreen(appiumDriver);

  @When("Click Tab giao dịch tại home screen")
  public void clicktabGiaodich() {
    loginScreen.waitLoadingLogoInvisibility();
    homeScreen.clickAccount247Transfer();
  }

  @When("Click Chuyển tiền liên NH qua TK button")
  public void clickSearchButton() {
    loginScreen.waitLoadingLogoInvisibility();
    homeScreen.clickAccount247Transfer();
  }

  @When("Click Chuyển tiền trong ngân hàng button")
  public void clickChuyenTienVietinbank() {
    loginScreen.waitLoadingLogoInvisibility();
    homeScreen.clickTransferWithinVietinbank();
  }

  @When("Click Chuyển tiền liên NH thường button")
  public void clickInterbankTransfer() {
    loginScreen.waitLoadingLogoInvisibility();
    homeScreen.clickInterbankTransfer();
  }

  @When("Click Chuyển tiền liên NH 247 qua số thẻ button")
  public void clickInterbankTransfer247ViaCard() {
    loginScreen.waitLoadingLogoInvisibility();
    homeScreen.clickInterbankTransfer247ViaCard();
  }

  @When("Click Lệnh chi button")
  public void clickPaymentOrder() {
    loginScreen.waitLoadingLogoInvisibility();
    homeScreen.clickPaymentOrder();
  }

  @When("Click Avatar button")
  public void clickAvatarButton() {
    loginScreen.waitLoadingLogoInvisibility();
    homeScreen.clickAvtButton();
  }

  @When("Logout tài khoản")
  public void clickLogoutButton() {
    loginScreen.waitLoadingLogoInvisibility();
    homeScreen.clickLogoutButton();
    homeScreen.clickYesButton();
  }

  @When("Click Xem thêm")
  public void clickMoreButton() {
    homeScreen.clickMoreButton();
  }
  @When("Click Dịch vụ chuyển tiền")
  public void clickDichVuChuyenTien() {
    homeScreen.clickDichVuChuyenTien();
  }

  @When("Click Dịch vụ thanh toán")
  public void clickPayment() {
    homeScreen.clickPayment();
  }

  @When("Click tìm kiếm")
  public void clickSearchInHomeScreen() {
    homeScreen.clickIconSearch();
  }
  @When("Click Soft OTP button trong màn hình Đăng nhập")
  @When("Click Soft OTP button in Home Screen")
  public void clickSoftOTPButton() {
    homeScreen.clickSoftOTPButton();
  }

  @When("Chọn tài khoản {string} trong màn hình OTP")
  @When("Select account {string} in OTP screen")
  public void selectAccount(String account) {
    homeScreen.selectAccount(account);
  }

  @When("Lấy mã OTP")
  @When("Get OTP")
  public void getOTP() {
    homeScreen.getOTP();
  }

  @When("Click Tiếp tục button trong màn hình OTP")
  @When("Click Continue button on OTP screen")
  public void clickContinueButton() {
    homeScreen.clickContinueButton();
  }
}
