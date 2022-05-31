package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {

    private static final By ARTICLE_TITLE = By.id("org.wikipedia:id/view_page_title_text");
    private static final By FOOTER = By.xpath("//*[@text='View page in browser']");
    private static final By OPTIONS = By.xpath("//android.widget.ImageView[@content-desc='More options']");
    private static final By OPTIONS_ADD_TO_MY_LIST_BUTTON = By.xpath("//*[@text='Add to reading list']");
    private static final By ADD_TO_MY_LIST_OVERLAY = By.id("org.wikipedia:id/onboarding_button");
    private static final By MY_LIST_NAME_INPUT = By.id("org.wikipedia:id/text_input");
    private static final By MY_LIST_OK_BUTTON = By.id("android:id/button1");
    private static final By CLOSE_ARTICLE_BUTTON = By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']");


    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(ARTICLE_TITLE, "Title not displayed", 15);
    }

    public String getArticleTitle() {
        return waitForTitleElement().getText();
    }

    public void swipeToFooter() {
        this.swipeUpToFindElement(FOOTER, "Cannot find the end of article", 20);
    }

    public void addArticleToMyList(String folderName) {
        this.waitForElementAndClick(
                OPTIONS,
                "Cannot find button to open article options",
                5);
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5);
        this.waitForElementPresent(
                ADD_TO_MY_LIST_OVERLAY,
                "Cannot find 'Got it' tip overlay",
                5);
        this.waitForElementAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "Cannot find 'OK' button",
                5);
        this.waitForElementAndClear(
                MY_LIST_NAME_INPUT, "Cannot find input to set name of articles folder",
                5);

        String nameOfFolder = "Learning programming";

        this.waitForElementAndSendKeys(
                MY_LIST_NAME_INPUT,
                nameOfFolder, "Cannot put text into articles folder input",
                5);
        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Cannot find 'OK' button",
                5);
    }

    public void closeArticle() {
        this.waitForElementAndClick(
                CLOSE_ARTICLE_BUTTON,
                "Cannot close article, cannot find X link",
                5);
    }
}
