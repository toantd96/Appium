package screens.web;

import net.serenitybdd.annotations.Step;
import vn.vietinbank.efast.base.BasePage;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.ClickStrategy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Keys;

import static vn.vietinbank.efast.utils.EnvironmentConfig.URL_NEW_E_FAST;
import static vn.vietinbank.efast.utils.EnvironmentConfig.cookies_config;

public class LoginPage extends BasePage {

  @FindBy(xpath = "//input[@placeholder='Tên đăng nhập']")
  public WebElementFacade txtUsername;

  @FindBy(xpath = "//input[@type='password']")
  public WebElementFacade txtPassword;

  @FindBy(xpath = "//button[@type='submit']")
  public WebElementFacade btnLogin;

  @FindBy(xpath = "//a[@href='#selectLanguage']")
  public WebElementFacade language_btn;

  @FindBy(xpath = "//a[@class='icon-btn icon-logout']/img")
  WebElementFacade btnlogout;

  @FindBy(xpath = "//div[contains(@class,'select__control')]")
  WebElementFacade selectCompany;

  String langList = "//div[@class='lang-list collapse show']//a[text()='%s']";

  String byLinkWithText = "//a/span[text()='%s']/..";
  String nameCompany_xpath = "//b[text()='%s']";

  @Step("Go to page")
  public void gotoLoginPage() {
//    openURL(URL_NEW_E_FAST);
    setDriver(webDriver);
//    getDriver().get(URL_NEW_E_FAST);
    openURL(URL_NEW_E_FAST);
//    currentWindowHandle = webDriver.getWindowHandle();
//    System.out.println(currentWindowHandle);
  }

  @Step("link to Page")
  public void linkToPageHasText(String textLink) {
    WebElementFacade weLink = find(By.xpath(String.format(byLinkWithText, textLink)));
    String urlLink = weLink.getAttribute("href");
    getDriver().get(urlLink);
  }

  @Step("Nhập username")
  public void inputUsername(String userName) {
    sendKeys(txtUsername, userName);
  }

  @Step("Nhập password")
  public void inputPassword(String passWord) {
    sendKeys(txtPassword, passWord);
  }

  @Step("Nhập password prod")
  public void inputPasswordProd(String passWord) {
    txtPassword.waitUntilVisible().sendKeys(passWord);
  }

  @Step("Hiển thị thông báo")
  public void shouldBeMessage(String message) {
    shouldBeVisible(By.xpath(String.format("//*[text()='%s']", message)));
  }

  String currentWindowHandle;

  @Step("Click login")
  public void login() {
    click(btnLogin,120);
    //    btnLogin.sendKeys(Keys.ENTER);
    closePopup();
  }

  @Step("Click logout")
  public void logout() {
    delay(2000);
    click(btnlogout,120);
    //    btnLogin.sendKeys(Keys.ENTER);
  }

  @Step("Set cookie chứng thư số")
  public void setCookieCTS(String keyVNPT, String serialCTS) {
    System.out.println(serialCTS);
    System.out.println(keyVNPT);
    getJavascriptExecutorFacade()
        .executeScript(
            String.format("window.sessionStorage.setItem('%s','%s');", "keyVNPT", keyVNPT));
    getJavascriptExecutorFacade()
        .executeScript(
            String.format("window.sessionStorage.setItem('%s','%s');", "serialCTS", serialCTS));
  }

  public void setLanguage(String language) {
    if (!language_btn.waitUntilVisible().getText().equals(language)) {
      language_btn.click();
      WebElementFacade elementFacade = find(By.xpath(String.format(langList, language)));
      elementFacade.waitUntilVisible().click();
    }
  }

  public void selectCompanyLogin(String nameCompany) {
    selectCompany.waitUntilVisible().click();
    findElement(String.format(nameCompany_xpath, nameCompany)).waitUntilVisible().click();
  }
}
