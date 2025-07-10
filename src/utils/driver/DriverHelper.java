package utils.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.useDriver;
import static utils.EnvironmentConfig.*;
import static utils.ExcelHelper.sheetPathDownload;

public class DriverHelper {

    public static String getOperatingSystem() {
        return System.getProperty("os.name");
    }

    public static WebDriver chromeDriver() {
        System.out.println(System.getProperty("os.name"));
        System.out.println("Setting up chromedriver ");
        if (getOperatingSystem().contains("Windows")) {
            System.setProperty("webdriver.chrome.driver", WEB_DRIVER_PATH + "windows/" + "chromedriver.exe");
        } else {
            System.setProperty("webdriver.chrome.driver", WEB_DRIVER_PATH + "mac/" + "chromedriver");
        }

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-print-preview");
        options.addArguments("--start-maximized");

        Map<String, Object> prefs = new HashMap<String, Object>();
        String filePath = Paths.get(System.getProperty("user.dir")) + "\\" + sheetPathDownload("").replace("/", "\\");
        options.addArguments("--disable-print-preview");
        options.addArguments("--start-maximized");
        options.addArguments("--unsafely-treat-insecure-origin-as-secure=http://10.6.130.132:6168");
        prefs.put("download.default_directory", filePath);
        options.setExperimentalOption("prefs", prefs);


        WebDriver driver = new ChromeDriver(options);
        useDriver(driver);
        return driver;
    }

    public static WebDriver ieDriver() {
        System.out.println("Setting up IEdriver ");
        if (getOperatingSystem().contains("Windows")) {
          System.setProperty("webdriver.ie.driver", WEB_DRIVER_PATH + "windows/"+ "IEDriverServer.exe");
        }else {
          System.out.println("IEdriver không hỗ trợ hệ điều hành này");
        }

        InternetExplorerDriverService ieService = InternetExplorerDriverService.createDefaultService();

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setAcceptInsecureCerts(false);
        caps.setBrowserName("internet explorer");
//        caps.setCapability("ie.edgechromium", true);
//        caps.setCapability("ie.edgepath", IE_EXE_PATH);

        InternetExplorerOptions options = new InternetExplorerOptions();
        options.setCapability("ignoreZoomSetting", false);
        options.setCapability("ignoreProtectedModeSettings", true);
        options.attachToEdgeChrome();
        options.withEdgeExecutablePath(EDGE_EXE_PATH);
        WebDriver driver = new InternetExplorerDriver(ieService, options.merge(caps));

        driver.manage().window().maximize();
        useDriver(driver);
        return driver;
    }

    public static WebDriver firefoxDriver() {
        //    WebDriverManager.firefoxdriver().setup();
        WebDriver driver = new FirefoxDriver();
        useDriver(driver);
        return driver;
    }

    public static WebDriver edgeDriver() {
        System.out.println("Setting up Edgedriver ");
        if (getOperatingSystem().contains("Windows")) {
          System.setProperty("webdriver.edge.driver", WEB_DRIVER_PATH + "msedgedriver.exe");
        }
        else {
          System.out.println("IEdriver không hỗ trợ hệ điều hành này");
        }

        WebDriver driver = new EdgeDriver();
        useDriver(driver);
        return driver;
    }
}
