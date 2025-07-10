package steps.web.demo;

import io.cucumber.core.plugin.SerenityReporter;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.SerenityReports;
import screens.web.LoginPage;
import vn.vietinbank.efast.utils.Constant;
import vn.vietinbank.efast.utils.DataInputHelper;

import static vn.vietinbank.efast.utils.EnvironmentConfig.*;

public class LoginPageStep {

  @Steps LoginPage loginPage;

  DataInputHelper dataInputHelper;

  @When("^Open login new eFast$")
  public void goToNewEFast() {
    loginPage.gotoLoginPage();
    dataInputHelper = new DataInputHelper();
    loginPage.setLanguage(LANGUAGE);
    System.out.println("Print bien" + SavingPage.idTransaction);
  }

  @And("Kế toán viên đăng nhập")
  public void ktvLogin() {
    String username = dataInputHelper.getPropertyValue(env_var + "_username", account_ktv);
    String password = dataInputHelper.getPropertyValue(env_var + "_password", account_ktv);
    System.out.println(username);
    loginPage.inputUsername(username);
    loginPage.inputPassword(password);
  }

  @And("Chủ tài khoản đăng nhập")
  public void ctkLogin() {
    String username = dataInputHelper.getPropertyValue(env_var + "_username", account_ctk);
    String password = dataInputHelper.getPropertyValue(env_var + "_password", account_ctk);
    loginPage.inputUsername(username);
    loginPage.inputPassword(password);
  }

  @And("^Nhập thông tin tài khoản \"([^\"]*)\" và mật khẩu \"([^\"]*)\"$")
  @And("^Input username \"([^\"]*)\" and password \"([^\"]*)\"$")
  public void inputUsernameAndPassword(String username, String password) {
    loginPage.inputUsername(username);
    loginPage.inputPassword(password);
  }

  @And("^Nhập thông tin tài khoản \"([^\"]*)\" và mật khẩu \"([^\"]*)\" từ database$")
  @And("^Input username \"([^\"]*)\" and password \"([^\"]*)\" from database$$")
  public void inputUsernameAndPassword1(String username,String password) {
    loginPage.inputUsername(databaseConfig.executeQuery(String.format(Constant.QUERY_GET_DATABASE), "String", username));
    loginPage.inputPassword(databaseConfig.executeQuery(String.format(Constant.QUERY_GET_DATABASE), "String", password));
  }

  @And("Maker đăng nhập user \"([^\"]*)\" và password \"([^\"]*)\"$")
  public void makerLogin(String username, String password) {
    loginPage.inputUsername(username);
    loginPage.inputPassword(password);
  }

  @And("Checker đăng nhập user \"([^\"]*)\" và password \"([^\"]*)\"$")
  public void CheckerLogin(String username, String password) {
    loginPage.inputUsername(username);
    loginPage.inputPassword(password);
  }

  @And("^Click Đăng nhập eFast$")
  @And("^Click Login eFast$")
  public void login() {
    loginPage.login();
  }

  @And("^Set cookie Chứng Thư Số$")
  public void setCookieCTS() {
    dataInputHelper = new DataInputHelper();
    String keyVNPT = dataInputHelper.getPropertyValue("keyVNPT", cookies_config);
    String serialCTS = dataInputHelper.getPropertyValue("serialCTS", cookies_config);
    loginPage.setCookieCTS(keyVNPT, serialCTS);
  }

  @And("Checker-Deposit đăng nhập")
  public void ksvLogin() {
    String username = dataInputHelper.getPropertyValue(env_var + "_username", account_ksv);
    String password = dataInputHelper.getPropertyValue(env_var + "_password", account_ksv);
    loginPage.inputUsername(username);
    loginPage.inputPassword(password);
  }

  @And("Maker- Deposit đăng nhập")
  public void gdvLogin() {
    String username = dataInputHelper.getPropertyValue(env_var + "_username", account_gdv);
    String password = dataInputHelper.getPropertyValue(env_var + "_password", account_gdv);
    loginPage.inputUsername(username);
    loginPage.inputPassword(password);
  }

  @And("Maker- Close Loan đăng nhập")
  public void gdvlnLogin() {
    String username = dataInputHelper.getPropertyValue(env_var + "_username", account_gdvln);
    String password = dataInputHelper.getPropertyValue(env_var + "_password", account_gdvln);
    loginPage.inputUsername(username);
    loginPage.inputPassword(password);
  }

  @And("Checker- Close Loan đăng nhập")
  public void ksvlnLogin() {
    String username = dataInputHelper.getPropertyValue(env_var + "_username", account_gdvln);
    String password = dataInputHelper.getPropertyValue(env_var + "_password", account_gdvln);
    loginPage.inputUsername(username);
    loginPage.inputPassword(password);
  }

  @And("Logout hệ thống")
  public void clickLogout() {
    loginPage.logout();
  }

  @And("^Nhập username \"([^\"]*)\" and password maker trong môi trường prod$")
  public void inputUsernameAndPasswordPROD(String username) {
    loginPage.inputUsername(username);
    loginPage.inputPasswordProd(System.getProperty("password_maker"));
  }

  @And("^Nhập username \"([^\"]*)\" and password checker trong môi trường prod$")
  public void inputUsernameAndPasswordCheckerPROD(String username) {
    loginPage.inputUsername(username);
    loginPage.inputPasswordProd(System.getProperty("password_checker"));
  }

  @And("Chọn công ty {string}")
  public void selectCompany(String nameCompany) {
    loginPage.selectCompanyLogin(nameCompany);
  }
}
