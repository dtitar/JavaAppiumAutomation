package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {

    private static final String ARTICLE_TITLE = "id:org.wikipedia:id/view_page_title_text";
    private static final String FOOTER = "xpath://*[@text='View page in browser']";
    private static final String OPTIONS = "xpath://android.widget.ImageView[@content-desc='More options']";
    private static final String OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://*[@text='Add to reading list']";
    private static final String ADD_TO_MY_LIST_OVERLAY = "id:org.wikipedia:id/onboarding_button";
    private static final String MY_LIST_NAME_INPUT = "id:org.wikipedia:id/text_input";
    private static final String MY_LIST_OK_BUTTON = "id:android:id/button1";
    private static final String CLOSE_ARTICLE_BUTTON = "xpath://android.widget.ImageButton[@content-desc='Navigate up']";


    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(ARTICLE_TITLE, "Title not displayed", 15);
    }

    public void assertArticleHasTitle() {
        this.assertElementPresent(ARTICLE_TITLE, "Cannot find article title");
    }

    public String getArticleTitle() {
        return waitForTitleElement().getText();
    }

    public void swipeToFooter() {
        this.swipeUpToFindElement(FOOTER, "Cannot find the end of article", 20);
    }

    private boolean isReadingListContainFolderWithName(String folderName) {
        return this.getAmountOfElements(getElementLocatorByText(folderName)) == 1;
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
        if (isReadingListContainFolderWithName(folderName)) {
            addArticleToExistingFolder(folderName);
        } else {
            addArticleToNewFolder(folderName);
        }
    }


    private void addArticleToExistingFolder(String folderName) {
        this.chooseFolder(folderName);
    }

    private void chooseFolder(String folderName) {
        this.waitForElementAndClick(getElementLocatorByText(folderName), "Cannot find folder with name " + folderName, 5);
    }

    private void addArticleToNewFolder(String folderName) {
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

        this.waitForElementAndSendKeys(
                MY_LIST_NAME_INPUT,
                folderName, "Cannot put text into articles folder input",
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
