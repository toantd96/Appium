package base;

import static net.serenitybdd.screenplay.GivenWhenThen.then;


import com.fasterxml.jackson.databind.node.ObjectNode;

import java.text.MessageFormat;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import net.serenitybdd.core.pages.ClearContents;
import net.serenitybdd.core.pages.ListOfWebElementFacades;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.BooleanQuestionConsequence;
import net.serenitybdd.screenplay.Consequence;
import net.serenitybdd.screenplay.Question;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.bidi.log.LogLevel;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import utils.LogHelper;
import utils.ReadWriteTXT;
import utils.ReportPortalHelper;
import utils.RobotHelper;
import utils.component.DataObject;
import utils.component.DataObject.TRANS;

public class BasePage extends PageObject {
    public static WebDriver webDriver;
    protected static final Logger logger = LogHelper.getLogger();
    public static final int defaultTimeOut = 10;

    public static final int sortTimeOut = 5;
    public static final int longTimeOut = 60;

    public BasePage() {
        super(webDriver);
    }

    String styleOption;

    public void closePopup() {
        delay(2000);
        boolean checkPopUpAds = false;
        boolean checkPopUp = false;
        boolean checkratePopUp = false;
        boolean checkNewpopUp = false;

        String xpath = "//*[@id='MarketingAnnouncements']";
        try {
            checkPopUpAds = webDriver.findElement(By.xpath(xpath)).isDisplayed();
        } catch (NoSuchElementException e) {
            logger.info(e.getMessage());
        }

        String xpathPopUp = "//div[@class='ant-modal-content']";
        try {
            checkPopUp = webDriver.findElement(By.xpath(xpathPopUp)).isDisplayed();
        } catch (NoSuchElementException e) {
            logger.info(e.getMessage());
        }

        String ratePopUp = "//div[@class='modal-content']";
        try {
            checkratePopUp = webDriver.findElement(By.xpath(ratePopUp)).isDisplayed();
        } catch (NoSuchElementException e) {
            logger.info(e.getMessage());
        }

        String newpopUp = "//div[@class='fix-mb cmsn-modal active']";
        try {
            checkNewpopUp = webDriver.findElement(By.xpath(newpopUp)).isDisplayed();
        } catch (NoSuchElementException e) {
            logger.info(e.getMessage());
        }

        if (checkNewpopUp) {
            WebElementFacade w = findElement(newpopUp);
            getJavascriptExecutorFacade().executeScript("arguments[0].setAttribute('style', 'visibility: hidden')", w);
        }

        if (checkPopUp) {
            String x = "//div[@class='ant-modal-content']//button[@class='close-button']";
            WebElementFacade btn = findElement(x);
            click(btn);
        }

        if (checkPopUpAds) {
            WebElementFacade w = findElement("//*[@id='MarketingAnnouncements']");
            getJavascriptExecutorFacade().executeScript("arguments[0].setAttribute('style', 'visibility: hidden')", w);
        }

        if (checkratePopUp) {
            WebElementFacade w = findElement("//a[@class='btn-close']");
            click(w);
        }
    }

    public void showTitleBigPage(String title) {
        setDriver(webDriver);
        try {
            System.out.println("showTitleBigPage " + title);
            String titleString = find(By.xpath("//*[@class='title-big-page']//span")).withTimeoutOf(Duration.ofSeconds(longTimeOut)).waitUntilVisible().getText();
            if (!titleString.contains(title)) {
                String errorMessage = String.format("One of the text elements in '%s' was not found in the page", title);
                throw new NoSuchElementException(errorMessage);
            }
            logger.info("Title display");
        } catch (Exception e) {
            logger.error(e.getMessage());
            Assert.fail("testcase fail");
        }
    }

    public void scrollToWebElement(WebElementFacade we) {
        try {
            String js_code = "arguments[0].scrollIntoView(false);";
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            js.executeScript(js_code, we);
            logger.info("Inputted {} to element located by {} successfully", we);
        } catch (Exception e) {
            logger.error("Cannot scroll on element located by ''{}''. Root cause: {}", we, e.getMessage());
            Assert.fail("testcase fail");
        }
    }

    public void scrollFromOrigin(WebElementFacade we, int deltaX, int deltaY) {
        new Actions(getDriver()).scrollFromOrigin(WheelInput.ScrollOrigin.fromElement(we), deltaX, deltaY).perform();
    }

    public void scrollWithinWebElement(WebElementFacade we) {
        String js_code = "arguments[0].scrollTop = arguments[0].scrollTop + 100;";
        getJavascriptExecutorFacade().executeScript(js_code, we);
    }

    public void scrollWithinWebElement(WebElementFacade we, String pixel) {
        String js_code = "arguments[0].scrollTop = arguments[0].scrollTop + %s;";
        getJavascriptExecutorFacade().executeScript(String.format(js_code, pixel), we);
    }

    public void scrollUpWithinWebElement(WebElementFacade we) {
        String js_code = "window.scrollBy(0,-100);";
        getJavascriptExecutorFacade().executeScript(js_code, we);
    }

    public void scrollUpWithinWebElement(WebElementFacade we, String pixel) {
        String js_code = "window.scrollBy(0,-%s);";
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript(String.format(js_code, pixel), we);
    }

    public void scrollUpUntilFindElement(WebElementFacade elementToFind, int countScrollMax) {
        for (int i = 0; i < countScrollMax; i++) {
            if (!elementToFind.isVisible()) {
                scrollUpWithinWebElement(elementToFind, "1000");
            } else {
                elementToFind.click();
                break;
            }
        }
    }

    public void scrollUpTopPage() {
        String js_code = "window.scrollTo(0,0);";
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript(js_code);
    }

    public void scrollWithinWebElementUntilFindElement(WebElementFacade elementScroll, WebElementFacade elementToFind, int countScrollMax) {
        for (int i = 0; i < countScrollMax; i++) {
            if (!elementToFind.isDisplayed()) {
                scrollWithinWebElement(elementScroll);
            } else {
                elementToFind.click();
                break;
            }
        }
    }

    public void scrollDownWithinWebElementUntilFindElement(WebElementFacade elementScroll, WebElementFacade elementToFind, int countScrollMax) {
        for (int i = 0; i < countScrollMax; i++) {
            if (!elementToFind.isDisplayed()) {
                scrollWithinWebElement(elementScroll);
            } else {
                elementToFind.click();
                break;
            }
        }
    }

    public void chooseOptionInList(WebElementFacade we, String text) {
        WebElementFacade option = getOptionList(we, text, 0).get(0);
        // kiem tra neu da co the click thi click luon ma khong scroll nua
        if (!option.isClickable()) {
            scrollToWebElement(option);
        }
        click(option);
    }

    public void chooseOptionInList(WebElementFacade we, String text, int timeout) {
        WebElementFacade option = getOptionList(we, text, timeout).get(0);
        // kiem tra neu da co the click thi click luon ma khong scroll nua
        if (!option.isClickable()) {
            scrollToWebElement(option);
        }
        click(option);
    }

    public void chooseOptionInListWithLastFind(WebElementFacade we, String text) {
        ListOfWebElementFacades optionList = getOptionList(we, text, 0);
        WebElementFacade option = optionList.get(optionList.size() - 1);
        scrollToWebElement(option);
        option.click();
    }

    public void chooseOptionInListWithLastFind(WebElementFacade we, String text, int timeout) {
        ListOfWebElementFacades optionList = getOptionList(we, text, timeout);
        WebElementFacade option = optionList.get(optionList.size() - 1);
        scrollToWebElement(option);
        option.click();
    }

    public ListOfWebElementFacades getOptionList(WebElementFacade we, String text, int timeout) {
        logger.info("getOptionList:" + text);
        if (!we.isClickable()) {
            scrollToWebElement(we);
        }
        click(we);
        String xpathContainsText = String.format("//*[contains(text(),'%s')]", text);
        if (timeout > 0) {
            try {
                waitForTextToAppear(text, timeout);

            } catch (Exception e) {
                logger.info("waitForTextToAppear not found " + text);
            }
        }
        ListOfWebElementFacades listOption = findAll(By.xpath(xpathContainsText));
        logger.info("listOption size:" + listOption.size());
        if (listOption.size() == 0) {
            // su dung cho truong hop khac font chu tim kiem
            logger.info("styleOption:" + styleOption);
            ListOfWebElementFacades listFindStyleOption = findAll(By.xpath(String.format("//*[@style='%s']", styleOption)));

            logger.info("listFindStyleOption size:" + listFindStyleOption.size());
            for (WebElementFacade option : listFindStyleOption) {
                String txtOption = normalize(option.getText());
                String searchOption = normalize(text);
                if (txtOption.contains(searchOption)) {
                    listOption.add(option);
                }
            }
        } else {
            styleOption = listOption.get(0).getAttribute("style");
        }
        return listOption;
    }

    public void actionSendKeys(CharSequence text) {
        new Actions(getDriver()).sendKeys(text).perform();
        delay(500);
    }

    public void actionDragAndDrop(WebElement From, WebElement To) {
        Actions act = new Actions(getDriver());
        act.dragAndDrop(From, To).build().perform();
    }

    public void actionDragAndDrop(WebElement From, int xDestinationlocator, int yDestinationlocator) {
        Actions act = new Actions(getDriver());
        act.dragAndDropBy(From, xDestinationlocator, yDestinationlocator).build().perform();
    }

    public void robotSendKeys(CharSequence text) {
        new RobotHelper().sendKeys(text);
    }

    public void robotClear() {
        RobotHelper robotHelper = new RobotHelper();
        robotHelper.ctrl_A();
    }

    public void delay(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            logger.error("Cannot delay in {}. Root cause: {}", time, e.getMessage());
        }
    }

    public WebElementFacade findElement(String locator) {
        WebElementFacade element = null;
        logger.info("Finding element {}", locator);
        try {
            element = find(By.xpath(locator)).withTimeoutOf(Duration.ofSeconds(defaultTimeOut)).waitUntilVisible();
            logger.info("Found 1 element {}", locator);
        } catch (Exception e) {
            logger.error("Cannot found element {}. Root cause: {}", locator, e.getMessage());
            Assert.fail("Cannot found element");
        }
        return element;
    }

    public WebElementFacade findElement(String locator, int timeOut) {
        WebElementFacade element = null;
        logger.info("Finding element {}", locator);
        try {
            element = find(By.xpath(locator)).withTimeoutOf(Duration.ofSeconds(timeOut)).waitUntilVisible();
            logger.info("Found 1 element {}", locator);
        } catch (Exception e) {
            logger.error("Cannot found element {}. Root cause: {}", locator, e.getMessage());
            Assert.fail("Cannot found element");
        }
        return element;
    }

    public List<WebElementFacade> findElements(String locator) {
        List<WebElementFacade> elements = List.of();
        logger.info("Finding elements located by {}", locator);
        try {
            find(By.xpath(locator)).waitUntilVisible();
            elements = findAll(By.xpath(locator));
            logger.info("Found {} element(s) located by {}", elements.size(), locator);
        } catch (Exception e) {
            logger.error("Cannot find elements located by {}. Root cause: {}", locator, e.getMessage());
        }
        return elements;
    }

    public void click(String locator) {
        try {
            logger.info("Click on element located by {}", locator);
            WebElementFacade we = findElement(locator);
            we.click();
            logger.info("Clicked on element located by {} successfully", locator);
        } catch (Exception e) {
            logger.error("Cannot click on element located by ''{}''. Root cause: {}", locator, e.getMessage());
            Assert.fail("Cannot click on element");
        }
    }

    public void click(WebElementFacade we) {
        try {
            logger.info("Click on element located by {}", we);
            if (we.isCurrentlyVisible()) {
                we.click();
                logger.info("Clicked on element located by {} successfully", we);
            } else {
                scrollToWebElement(we);
                logger.info("scrollToWebElement to element located by {} successfully", we);
                we.waitUntilVisible().click();
                logger.info("Clicked on element located by {} successfully", we);
            }

        } catch (Exception e) {
            logger.error("Cannot click on element located by ''{}''. Root cause: {}", we, e.getMessage());

            Assert.fail("Cannot click on element");
        }
    }

    public void click(String locator, int timeOut) {
        try {
            logger.info("Click on element located by {}", locator);
            WebElementFacade we = findElement(locator, timeOut);
            we.click();
            logger.info("Clicked on element located by {} successfully", locator);
        } catch (Exception e) {
            logger.error("Cannot click on element located by ''{}''. Root cause: {}", locator, e.getMessage());
            Assert.fail();
        }
    }

    public void clear(String locator) {
        try {
            logger.info("Clear text on element located by {}", locator);
            WebElementFacade we = findElement(locator);
            we.clear();
            logger.info("Cleared text element located by {} successfully", locator);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    public void clear(String locator, int timeOut) {
        try {
            logger.info("Clear text on element located by {}", locator);
            WebElementFacade we = findElement(locator, timeOut);
            we.clear();
            logger.info("Cleared text element located by {} successfully", locator);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    public void maximizeWindow() {
        getDriver().manage().window().maximize();
    }

    public void switchToNewWindow() {
        String currentWindowHandle = getDriver().getWindowHandle();
        for (int i = 0; i < sortTimeOut; i++) {
            Set<String> windowHandles = getDriver().getWindowHandles();
            if (windowHandles.size() > 1) {
                for (String window : windowHandles) {
                    if (!currentWindowHandle.contentEquals(window)) {
                        getDriver().switchTo().window(window);
                    }
                }
                break;
            } else {
                waitABit(1000);
                System.out.println("wait new window...");
            }
        }
    }

    public void writeToFile(String filePath, String content) {
        if (content != null && !content.startsWith("null")) {
            ReadWriteTXT.SaveReferenceNumberOrderToFile(filePath, content);
        }
    }

    public void writeToFileOverWrite(String filePath, String content) {
        ReadWriteTXT.saveReferenceNumberOrderToFileOverWrite(filePath, content);
    }

    public String readFromFile(String filePath, String status) {
        return ReadWriteTXT.getrefNumber(filePath, status);
    }

    public void updateToFile(String filePath, String content, String contentUpdate) {
        if (contentUpdate != null && !contentUpdate.startsWith("null")) {
            ReadWriteTXT.updateDataInFile(filePath, content, contentUpdate);
        }
    }

    //  public void sendKeys(WebElementFacade we, String text) {
    //    we.clear();
    //    if (we.getText().contentEquals("")) {
    //      logger.info("sendKeys is success!");
    //    } else {
    //
    //    }
    //    we.sendKeys(text);
    //
    //    if (we.getText().contentEquals(text)) {
    //      logger.info("sendKeys is success!");
    //    } else {
    //      logger.info("sendKeys is not success!");
    //      logger.info("execute sendKeys 5 time!");
    //      for (int i = 0; i < 5; i++) {
    //        waitABit(1000);
    //        System.out.println(we.getText());
    //        we.clear();
    //        we.sendKeys(text);
    //        if (we.getText().contentEquals(text)) {
    //          logger.info("sendKeys is success!");
    //          break;
    //        }
    //      }
    //    }
    //  }

    public void verifyInfoClear(List<String> listAllInfoInput) {
        Actor softAsert = new Actor("softAsert");
        List<BooleanQuestionConsequence> listOfAssert = new ArrayList<BooleanQuestionConsequence>() {
        };
        for (String inputInfo : listAllInfoInput) {
            if (!inputInfo.contentEquals("")) {
                listOfAssert.add(new BooleanQuestionConsequence(inputInfo + " is clear", Question.about("").answeredBy(actor -> !getDriver().getPageSource().contains(inputInfo))));
            }
        }

        then(softAsert).should((Consequence[]) listOfAssert.toArray(new Consequence[0]));
    }

    // Chờ Logo load xong
    public void waitLogoLoadingUntilNotVisible() {
        find(By.xpath("//div[@class='logo-loading']")).withTimeoutOf(Duration.ofSeconds(120)).waitUntilNotVisible();
    }

    // Chờ dấu 3 chấm load xong
    public void waitEllipsisLoadingUntilNotVisible() {
        find(By.xpath("//div[@class='lds-ellipsis']")).withTimeoutOf(Duration.ofSeconds(120)).waitUntilNotVisible();
    }

    public String normalize(String unicodeText) {
        return Normalizer.normalize(unicodeText, Form.NFD).replaceAll("\\p{M}", "");
    }

    public ObjectNode findObjectNodeHasStatus(String filePath, String status) {
        return DataObject.findObjectNodeHasFieldEqualValue(filePath, TRANS.status.name(), status);
    }

    public ObjectNode putDataStore(String fieldName, String value) {
        DataObject.getDataStore().put(fieldName, value);
        return DataObject.getDataStore();
    }

    public ObjectNode getDataStore() {
        return DataObject.getDataStore();
    }

    /*  tim transaction co status trong file va thuc hien update status moi
     * */
    public void updateDataStoreTransactionStatusToFile(String filePath, String transactionNumber, String statusOld, String statusUpdate) {
        if (transactionNumber != null) {
            ObjectNode dataOld = DataObject.findObjectNodeHasFieldEqualValue(filePath, TRANS.transactioNumber.name(), transactionNumber, TRANS.status.name(), statusOld);
            ObjectNode dataNew = dataOld.deepCopy();
            dataNew.put(TRANS.status.name(), statusUpdate);
            ReadWriteTXT.updateDataInFile(filePath, dataOld.toString(), dataNew.toString());
        } else {
            logger.info("transactionNumber is null, not updateToFileTransactionStatus");
        }
    }

    public void sendKeys(WebElementFacade we, String text) {
        try {
            logger.info("Input {} to element located by {}", maskData(text), we);
            we.waitUntilVisible().sendKeys(text);
            logger.info("Inputted {} to element located by {} successfully", maskData(text), we);

        } catch (Exception e) {
            logger.error("Cannot sendkey on element located by ''{}''. Root cause: {}", we, e.getMessage());
            Assert.fail();
        }
    }

    public void click(WebElementFacade we, int timeOut) {
        try {
            logger.info("Click on element located by {}", we);
            we.withTimeoutOf(Duration.ofSeconds(timeOut)).waitUntilVisible().click();
            logger.info("Clicked on element located by {} successfully", we);
        } catch (Exception e) {
            logger.error("Cannot click on element located by ''{}''. Root cause: {}", we, e.getMessage());
            Assert.fail();
        }
    }

    public void doubleClick(WebElementFacade we) {
        try {
            logger.info("Click on element located by {}", we);
            Actions actions = new Actions(getDriver());
            we.waitUntilVisible();
            actions.doubleClick(we).build().perform();
            logger.info("Clicked on element located by {} successfully", we);
        } catch (Exception e) {
            logger.error("Cannot click on element located by ''{}''. Root cause: {}", we, e.getMessage());
            Assert.fail();
        }
    }

    public void shouldBeVisible(WebElementFacade we, int secondsTimeout) {
        try {
            logger.info("shouldBeVisible element on located by {}", we);
            we.withTimeoutOf(Duration.ofSeconds(secondsTimeout)).waitUntilVisible();
            shouldBeVisible(we);
            logger.info("shouldBeVisible on element located by {} successfully", we);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Cannot shouldBeVisible on element located by ''{}''. Root cause: {}", we, e.getMessage());
            Assert.fail();
        }
    }

    public WebDriver switchToFrame(String frame) {
        try {
            return getDriver().switchTo().frame(frame);
        } catch (Exception e) {
            logger.error("Cannot switch To Frame by ''{}''. Root cause: {}", frame, e.getMessage());
            Assert.fail();
        }
        return getDriver();
    }

    public void openURL(String url) {
        try {
            getDriver().get(url);
        } catch (Exception e) {
            logger.error("Cannot open url ''{}''. Root cause: {}", url, e.getMessage());
            Assert.fail();
        }
    }

    public void acceptAlert(String message) {
        String messageText = "";
        Alert alert = null;
        for (int i = 0; i < defaultTimeOut; i++) {
            waitABit(1000);
            try {
                alert = getDriver().switchTo().alert();
            } catch (Exception e) {
                waitABit(1000);
                System.out.println("waiting... alert is show");
            }
        }
        if (alert != null) {
            messageText = alert.getText();
        }
        if (messageText.contains(message)) {
            alert.accept();

        } else if (messageText.contentEquals("")) {

        } else {

            Assert.fail();
        }
    }


    public void clear(WebElementFacade we) {
        try {
            ClearContents.ofElement(we);
        } catch (Exception e) {
            logger.error("Cannot clear contents ''{}''. Root cause: {}", we, e.getMessage());

            Assert.fail();
        }
    }

    public String maskData(String text) {
        if (text == null || text.length() < 4) {
            return text;
        }
        int maskLength = text.length() - 3;
        StringBuilder masked = new StringBuilder();

        for (int i = 0; i < maskLength; i++) {
            masked.append("*");
        }
        // giu 3 ky tu cuoi
        masked.append(text.substring(text.length() - 3));
        return masked.toString();
    }

    public String getText(String locator) {
        String text = null;
        try {
            logger.info("Get text element located by {}", locator);
            WebElementFacade we = findElement(locator);
            text = we.getText();
            logger.info("Get text element located by {} successfully", locator);

        } catch (Exception e) {
            logger.error("Cannot Get text element located by ''{}''. Root cause: {}", locator, e.getMessage());
            Assert.fail("Cannot Get text element");
        }
        return text;
    }

    public String getText(WebElementFacade we) {
        String text = null;
        try {
            logger.info("Get text element located by {}", we);
            if (we.isCurrentlyVisible()) {
                text = we.getText();
                logger.info("Get text element located by {} successfully", we);
            } else {
                scrollToWebElement(we);
                logger.info("scrollToWebElement to element located by {} successfully", we);
                we.waitUntilVisible().click();
                logger.info("Get text element located by {} successfully", we);
            }
        } catch (Exception e) {
            logger.error("Cannot Get text element located by ''{}''. Root cause: {}", we, e.getMessage());
            Assert.fail("Cannot Get text element");
        }
        return text;
    }
}
