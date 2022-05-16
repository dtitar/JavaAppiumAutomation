import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.URL;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.By.xpath;

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


    private static final By SEARCH_BOX = xpath("//*[contains(@text,'Search Wikipedia')]");
    private static final By SEARCH_BOX_INPUT = By.id("org.wikipedia:id/search_src_text");
    private static final By SEARCH_CLOSE_BTN = By.id("org.wikipedia:id/search_close_btn");
    private static final By EXPECTED_SEARCH_RESULT = By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']");
    private static final By ARTICLE_TITLE = By.id("org.wikipedia:id/view_page_title_text");

    @Test
    public void firstTest() {
        waitAndClick(SEARCH_BOX, "Cannot find search block", 5);
        waitAndSendKeys(SEARCH_BOX_INPUT, "Java", "Cannot find Search Input", 5);
        waitForElementPresent(EXPECTED_SEARCH_RESULT,
                "Cannot find expected search result",
                15);
    }

    @Test
    public void testCancelSearch() {
        waitAndClick(SEARCH_BOX, "Cannot find search block", 5);
        waitForElementPresent(SEARCH_BOX_INPUT, "Search input not displayed");
        waitAndClick(SEARCH_CLOSE_BTN, "Cannot find X to close search", 5);
        waitForElementNotPresent(EXPECTED_SEARCH_RESULT, "Search close button still displayed", 5);
    }

    @Test
    public void testClearSearch() {
        waitAndClick(SEARCH_BOX, "Cannot find search block", 5);
        waitAndSendKeys(SEARCH_BOX_INPUT, "Java", "Cannot find Search Input", 10);
        waitAndClear(SEARCH_BOX_INPUT, "Cannot clear search text", 5);
        waitAndClick(SEARCH_CLOSE_BTN, "Cannot find X to close search", 5);
        waitForElementNotPresent(EXPECTED_SEARCH_RESULT, "Search close button still displayed", 5);
    }

    @Test
    public void testCompareArticleTitle() {
        String expectedTitle = "Java (programming language)";

        waitAndClick(SEARCH_BOX, "Cannot find search block", 5);
        waitAndSendKeys(SEARCH_BOX_INPUT, "Java", "Cannot find Search Input", 5);
        waitAndClick(EXPECTED_SEARCH_RESULT, "Expected search result not displayed", 5);
        WebElement articleTitleElement = waitForElementPresent(ARTICLE_TITLE, "Title not displayed", 15);

        String articleTitle = articleTitleElement.getAttribute("text");
        assertEquals("We see unexpected title", expectedTitle, articleTitle);
    }

    @Test
    public void testSearchFieldHasText() {
        String expectedText = "Search Wikipedia";
        assertElementHasText(SEARCH_BOX, expectedText, "Text in search field not equals expected");
    }

    private void assertElementHasText(By locator, String expectedText, String errorMessage) {
        WebElement element = waitForElementPresent(locator, "Can't find element with locator " + locator);
        assertEquals(errorMessage, expectedText, element.getText());
    }

    private WebElement waitAndClick(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitAndSendKeys(By by, String text, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.sendKeys(text);
        return element;
    }

    private WebElement waitAndClear(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.clear();
        return element;
    }

    private WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage);
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }


    private WebElement waitForElementPresent(By by, String errorMessage) {
        return waitForElementPresent(by, errorMessage, 5);
    }

    private boolean waitForElementNotPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }
}

