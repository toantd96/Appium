package base;


import io.cucumber.java.After;
import io.cucumber.java.Before;


public class BaseTest {


    @After()
    public void tearDown() {
        if (BasePage.webDriver != null) {
            BasePage.webDriver.quit();
        }
        if (appiumDriver != null) {
            try {
                SetupDriverMobile.closeApplication();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
