import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;

import static org.openqa.selenium.By.xpath;

public class BaseTest {

    protected static final String APPIUM_SERVER_URL = "http://127.0.0.1:4723/wd/hub";
    protected static final String APP_NAME = "org.wikipedia";
    protected static final String APP_LOCATION = System.getProperty("user.dir")
            + File.separator
            + "apks"
            + File.separator
            + APP_NAME
            + ".apk";
    protected AppiumDriver driver;
    protected Actions actions;

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
        actions = new Actions(driver);
    }

    @After
    public void tearDown() {
        driver.quit();
    }


    protected static final By SEARCH_BOX = xpath("//*[contains(@text,'Search Wikipedia')]");
    protected static final By SEARCH_BOX_INPUT = By.id("org.wikipedia:id/search_src_text");
    protected static final By SEARCH_CLOSE_BTN = By.id("org.wikipedia:id/search_close_btn");

    protected static final By ARTICLE_TITLE = By.id("org.wikipedia:id/view_page_title_text");

    protected static final String SEARCH_ITEM_XPATH = "//*[@resource-id='org.wikipedia:id/page_list_item_container']";
    protected static final By SEARCH_ITEM = By.xpath(SEARCH_ITEM_XPATH);
    protected static final By SEARCH_ITEM_TITLE = By.xpath(SEARCH_ITEM_XPATH + "//*[@resource-id='org.wikipedia:id/page_list_item_title']");

}
