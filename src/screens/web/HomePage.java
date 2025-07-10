package screens.web;

import net.serenitybdd.core.pages.ListOfWebElementFacades;
import org.junit.Assert;
import vn.vietinbank.efast.base.BasePage;

import java.time.Duration;
import java.util.List;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.ClickStrategy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;

public class HomePage extends BasePage {

  String byParentMenu = "//nav//li[@class='sub-1 menu-has-children']/a/p";
  String byChildMenu = "//ul[@class='submenu']/li/a/p";
  String leftMenu_txt = "//aside[@class='main-nav']//p[text()='%s']";
  String tab_txt = "//a[contains(@class,'nav-link')]//span[text()='%s']";

  @FindBy(xpath = "//a[@class='icon-btn icon-logout']/img")
  WebElementFacade logout;

  @FindBy(xpath = "//div[@class='home-account']//div[@class='f-header']//p[@class='uppercase']")
  private WebElementFacade nameCompany_text;

  public static String nameCompany;

  @FindBy(xpath = "//div[@class='logo-loading']")
  WebElementFacade loadingIcon;

  @FindBy(xpath = "//div[@class='home-paymentAccount']")
  WebElementFacade paymentAccount;

  @FindBy(xpath = "//div[@class='home-balance']")
  WebElementFacade homeBalance;

  @FindBy(xpath = "//div[@class='home-mrt account-boxtable']")
  WebElementFacade accountBoxtable;

  @Step("Chọn menu trái efast")
  public void ChooseLeftMenu(String menuPath) {
    String[] menus = menuPath.split(">");
    String titleParentMenu = menus[0];
    String titleChildMenu = menus[1];
    waitForRenderedElementsToBePresent(By.xpath(byParentMenu));

    ListOfWebElementFacades parentMenus = findAll(byParentMenu);
    for (WebElementFacade menu : parentMenus) {
      if (normalize(menu.getText()).contentEquals(normalize(titleParentMenu))) {
        // menu.click();
        click(menu);
        scrollFromOrigin(menu,0,100);
        logger.info("Click parent menu " + menu.getText());
        break;
      }
    }

    waitForRenderedElementsToBePresent(By.xpath(byChildMenu));

    ListOfWebElementFacades childMenus = findAll(byChildMenu);
    for (WebElementFacade menu : childMenus) {
      if (normalize(menu.getText()).contentEquals(normalize(titleChildMenu))) {
        // menu.click();
        click(menu);
        logger.info("Click child menu " + menu.getText());
        break;
      }
    }
  }

  public void selectMenu(String menuPath) {
    System.out.println("selectMenu " + menuPath);
    WebElementFacade elementFacade = find(By.xpath(String.format(leftMenu_txt, menuPath)));
    // elementFacade.waitUntilVisible().click();
    elementFacade.waitUntilVisible();
    click(elementFacade);
    System.out.println("click " + By.xpath(String.format(leftMenu_txt, menuPath)));
    waitLogoLoadingUntilNotVisible();
  }

  public void selectTab(String menuPath) {
    waitLogoLoadingUntilNotVisible();
    WebElementFacade elementFacade = find(By.xpath(String.format(tab_txt, menuPath)));
    waitABit(2000);
    elementFacade.waitUntilVisible().click();
  }

  public void getNameCompany() {
    nameCompany = nameCompany_text.waitUntilVisible().getText();
  }

  public void closePopupMarketingAds() {
    if (find(By.xpath("//*[@id='MarketingAnnouncements']")).isVisible()) {
      getJavascriptExecutorFacade()
          .executeScript(
              "arguments[0].setAttribute('style', 'visibility: hidden')",
              find(By.xpath("//*[@id='MarketingAnnouncements']")));
    }
  }

  public void verifyDashBoardDisplaySuccess() {
    Assert.assertTrue(paymentAccount.isVisible());
    Assert.assertTrue(homeBalance.isVisible());
    Assert.assertTrue(accountBoxtable.isVisible());
  }

  public void selectTabScrollUp(String menuPath) {
    waitLogoLoadingUntilNotVisible();
    WebElementFacade elementFacade = find(By.xpath(String.format(tab_txt, menuPath)));
    waitABit(2000);
    for (int i = 0; i < 100; i++) {
      if (!elementFacade.isVisible()) {
        scrollUpWithinWebElement(elementFacade);
      } else {
        break;
      }
    }
    scrollUpWithinWebElement(elementFacade, "200");
    elementFacade.waitUntilVisible().click();
  }
}
