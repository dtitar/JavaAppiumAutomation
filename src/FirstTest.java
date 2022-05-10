import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;

import static java.lang.String.format;

public class FirstTest {

    private static final String APPIUM_SERVER_URL = "http://127.0.0.1:4723/wd/hub";
    private static final String APP_NAME = "org.wikipedia";
    private static final String APP_LOCATION = System.getProperty("user.dir")
            + File.separator
            + "apks"
            + File.separator
            + APP_NAME
            + ".apk";
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", APP_NAME);
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", APP_LOCATION);

        driver = new AndroidDriver(new URL(APPIUM_SERVER_URL), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void firstTest() {
        System.out.println("first test run");
    }
}
