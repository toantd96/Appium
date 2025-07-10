package base;

import com.google.common.collect.ImmutableList;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.joda.time.DateTime;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import static net.serenitybdd.screenplay.GivenWhenThen.then;


import java.time.Duration;

public class BaseScreen {
    private static final Duration SCROLL_DUR = Duration.ofMillis(1000);
    private static final double SCROLL_RATIO = 0.6;
    private static final int ANDROID_SCROLL_DIVISOR = 3;
    private static final int defaultTimeOut = 5;
    private static final Logger log = LoggerFactory.getLogger(BaseScreen.class);
    public static AppiumDriver mobileDriver;
    protected WebDriverWait wait;

    public BaseScreen(AppiumDriver driver) {
        mobileDriver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(5)), this);
    }

    protected WebDriverWait withTimeOut(int time) {
        wait = new WebDriverWait(mobileDriver, Duration.ofSeconds(time));
        return wait;
    }

    protected void waitUntilInvisible(By element, int seconds) {
        withTimeOut(seconds).until(ExpectedConditions.invisibilityOfElementLocated(element));
    }

    protected boolean checkElementDisplay(WebElement element) {
        try {
            element.isDisplayed();
            return true;
        } catch (Exception e) {
            return false;
        }
    }



    public void delay(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
           // logger.error("Cannot delay in {}. Root cause: {}", time, e.getMessage());
        }
    }

    public void swipe(Point start, Point end, Duration duration) {
        boolean isAndroid = mobileDriver instanceof AndroidDriver;
        PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence swipe = new Sequence(input, 0);
        swipe.addAction(input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), start.x, start.y));
        swipe.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        if (isAndroid) {
            duration = duration.dividedBy(ANDROID_SCROLL_DIVISOR);
        } else {
            swipe.addAction(new Pause(input, duration));
            duration = Duration.ZERO;
        }
        swipe.addAction(input.createPointerMove(duration, PointerInput.Origin.viewport(), end.x, end.y));
        swipe.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        (mobileDriver).perform(ImmutableList.of(swipe));
    }

    public void scrollToElement(String locator, ScrollDirection scrollDirection, int numberOfTimes) {
        boolean found = false;
        WebElement we;
        Dimension size = mobileDriver.manage().window().getSize();
        Point midPoint = new Point((int) (size.width * 0.5), (int) (size.height * 0.5));
        int top = midPoint.y - (int) ((size.height * SCROLL_RATIO) * 0.5);
        int bottom = midPoint.y + (int) ((size.height * SCROLL_RATIO) * 0.5);
        int count = 0;
        do {
            delay(1000);
            if (scrollDirection == ScrollDirection.DOWN) {
                swipe(new Point(midPoint.x, bottom), new Point(midPoint.x, top), SCROLL_DUR);
            } else if (scrollDirection == ScrollDirection.UP) {
                swipe(new Point(midPoint.x, top), new Point(midPoint.x, bottom), SCROLL_DUR);
            }
            By by = By.xpath(locator);
            try {
                we = mobileDriver.findElement(by);
                if (we != null) {
                    found = true;
//                    logger.info("Scrolled to mobile element located by {} successfully", locator);
                    System.out.println(String.format("Scrolled to mobile element located by {} successfully", locator));
                    break;
                }
            } catch (NoSuchElementException ignored) {

            }
            count++;
        } while (count == numberOfTimes);
        // logger.error(
        // "Cannot scroll to mobile element located by {}. Root cause: the vn.vietinbank.screens.web element not fount",
        //    locator);
    }

    public void writeToFile(String filePath, String content) {
        ReadWriteTXT.SaveReferenceNumberOrderToFile(filePath, content);
    }

    public String readFromFile(String filePath, String status) {
        return ReadWriteTXT.getrefNumber(filePath, status);
    }

    public void updateToFile(String filePath, String content, String contentUpdate) {
        ReadWriteTXT.updateDataInFile(filePath, content, contentUpdate);
    }


    protected void clickElementMultipleTimes(WebElement element, int times) {
        for (int i = 0; i < times; i++) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (checkElementDisplay(element)) {
                element.click();
            }
            if (!checkElementDisplay(element)) {
                break;
            }
        }
    }

    public WebElement findElement(String locator,int secondTimeout){
        WebElement element = null;
        try{
            WebDriverWait wait = new WebDriverWait(mobileDriver, Duration.ofSeconds(secondTimeout));
            element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
            log.info("Found 1 mobile element {}",locator);
        }catch (Exception e){
            log.error("Cannot find mobile element {}. Root cause: {}",locator,e.getMessage());
        }
        return  element;
    }
    public void shouldBeVisible(WebElement btnWe) {
        withTimeOut(60).until(ExpectedConditions.visibilityOf(btnWe)).isDisplayed();
    }
    public void click(WebElement btnWe){
        withTimeOut(defaultTimeOut).until(ExpectedConditions.elementToBeClickable(btnWe)).click();
    }
    public void sendKeys(WebElement txtWe,String text){
        withTimeOut(defaultTimeOut).until(ExpectedConditions.elementToBeClickable(txtWe)).sendKeys(text);
    }
    public void chooseOptionInlist(WebElement cboElement,String option){
        chooseOptionInlist(cboElement,option,defaultTimeOut);
    }
    public void chooseOptionInlist(WebElement cboElement,String option, int secondsTimeOut){
        withTimeOut(secondsTimeOut).until(ExpectedConditions.elementToBeClickable(cboElement)).click();
        String xpathOption = "";
        if(EnvironmentConfig.PLATFORM_NAME.contentEquals("iOS")){
            xpathOption = "//XCUIElementTypeStaticText[@name=\"%s\"]";
        }
        if(EnvironmentConfig.PLATFORM_NAME.contentEquals("Android")){
            xpathOption = "//XCUIElementTypeStaticText[@name=\"%s\"]";
        }
        findElement(String.format(xpathOption,option),secondsTimeOut).click();
    }
    public void clickDoneOnKeyboard(){
        String ios_Done = "//XCUIElementTypeButton[@name=\"Done\" or @name=\"Xong\"]";
        findElement(ios_Done,defaultTimeOut).click();
    }
    public void tap(WebElement we){
        try {
            Rectangle elRect = we.getRect();
            Point point = new Point(elRect.x + (int) (elRect.getWidth() / 2.0),
                    elRect.y + (int) (elRect.getHeight() / 2.0)
            );
            tapAtPoint(point);
            log.info("Tapped on mobile element {} successfully",we);
        }catch(Exception e){
            log.error("cannot not tap on mobile element ''{}''. Root cause: {}",we,e.getMessage());
        }
    }
    public void tapAtPoint(Point point){
        PointerInput input = new PointerInput(PointerInput.Kind.TOUCH,"finger1");
        Sequence tap = new Sequence(input,0);
        tap.addAction(input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(),point.x,point.y));
        tap.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(new Pause(input, Duration.ofMillis(200)));
        tap.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        mobileDriver.perform(ImmutableList.of(tap));
    }
}
