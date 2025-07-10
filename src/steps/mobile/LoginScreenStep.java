package steps.mobile;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import vn.vietinbank.efast.screens.mobile.HomeScreen;
import vn.vietinbank.efast.screens.mobile.LoginScreen;
import vn.vietinbank.efast.screens.mobile.deposit.DepositMakerScreen;
import vn.vietinbank.efast.steps.BaseSteps;

import static vn.vietinbank.efast.steps.BaseSteps.appiumDriver;

public class LoginScreenStep {
  LoginScreen loginScreen = new LoginScreen(appiumDriver);
  DepositMakerScreen depositMakerScreen= new DepositMakerScreen(appiumDriver);

  @When("Click Login button")
  public void clickLoginButton() {
      try {
          Thread.sleep(2000);
      } catch (InterruptedException e) {
          throw new RuntimeException(e);
      }
      loginScreen.clickLoginButton();
  }

  @And("Input password {string}")
  public void inputPassword(String password) {
    loginScreen.inputPassword(password);
    loginScreen.clickDoneButton();
  }

  @And("Input username {string}")
  public void inputUsername(String username) {
    loginScreen.inputUsername(username);
  }

  @And("Click Đăng nhập với tài khoản khác")
  public void loginWithDiffAccount() {
      if (loginScreen.isHaveAccount()) {
          loginScreen.clickLoginButton();
          loginScreen.clickLoginWithDiffAccount();
          loginScreen.clickYesBtn();
      }
  }

}
