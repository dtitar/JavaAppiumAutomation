package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;

public class BaseTest extends TestCase {

    protected static final String APPIUM_SERVER_URL = "http://127.0.0.1:4723/wd/hub";
    protected static final String APP_NAME = "org.wikipedia";
    protected static final String APP_LOCATION = System.getProperty("user.dir")
            + File.separator
            + "apks"
            + File.separator
            + APP_NAME
            + ".apk";
    protected AppiumDriver driver;


    @Override
    protected void setUp() throws Exception {

        super.setUp();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", APP_NAME);
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", APP_LOCATION);

        driver = new AndroidDriver(new URL(APPIUM_SERVER_URL), capabilities);
        this.rotateScreenPortrait();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        driver.quit();
    }

    protected void rotateScreenPortrait() {
        driver.rotate(ScreenOrientation.PORTRAIT);
    }
    protected void rotateScreenLandscape() {
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    protected void backgroundApp(int seconds) {
        driver.runAppInBackground(seconds);
    }
}
