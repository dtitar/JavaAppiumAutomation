import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;

import static java.lang.String.format;
import static org.junit.Assert.*;
import static org.openqa.selenium.By.xpath;

public class FourthLessonTest extends BaseTest {

    private static final By SEARCH_BOX = xpath("//*[contains(@text,'Search Wikipedia')]");
    private static final By SEARCH_BOX_INPUT = By.id("org.wikipedia:id/search_src_text");
    private static final By SEARCH_CLOSE_BTN = By.id("org.wikipedia:id/search_close_btn");

    private static final By ARTICLE_TITLE = By.id("org.wikipedia:id/view_page_title_text");

    private static final String SEARCH_ITEM_XPATH = "//*[@resource-id='org.wikipedia:id/page_list_item_container']";
    private static final By SEARCH_ITEM = By.xpath(SEARCH_ITEM_XPATH);
    private static final By SEARCH_ITEM_TITLE = By.xpath(SEARCH_ITEM_XPATH + "//*[@resource-id='org.wikipedia:id/page_list_item_title']");


    @Test
    public void testSwipeArticle() {

        String searchText = "Java";
        String expectedResultText = "Object-oriented programming language";
        By expectedSearchResult = By.xpath(format("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='%s']", expectedResultText));

        actions.waitAndClick(SEARCH_BOX, "Cannot find search block", 5);
        actions.waitAndSendKeys(SEARCH_BOX_INPUT, searchText, "Cannot find Search Input", 5);
        actions.waitAndClick(expectedSearchResult, "Expected search result not displayed", 5);
        actions.swipeUp(2000);
        actions.swipeUp(2000);
        actions.swipeUp(2000);
        actions.swipeUp(2000);
        actions.swipeUp(2000);
    }

    @Test
    public void testSwipeArticleToFindElement() {

        String searchText = "Appium";
        By itemTitle = By.xpath(format("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='%s']", searchText));
        By viewPageInBrowser = By.xpath("//*[@text='View page in browser']");

        actions.waitAndClick(SEARCH_BOX, "Cannot find search block", 5);
        actions.waitAndSendKeys(SEARCH_BOX_INPUT, searchText, "Cannot find Search Input", 5);
        actions.waitAndClick(itemTitle, "Cannot find '" + searchText + "' article in search", 5);
        actions.swipeUpToFindElement(viewPageInBrowser, "Cannot find the end of the article", 20);
    }

    @Test
    public void testSaveFirstArticleToMyList() {
        String searchText = "Java";
        String articleName = "Object-oriented programming language";
        By searchResult = By.xpath(format("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='%s']", articleName));

        actions.waitAndClick(
                SEARCH_BOX,
                "Cannot find search block",
                5);
        actions.waitAndSendKeys(
                SEARCH_BOX_INPUT,
                searchText,
                "Cannot find Search Input",
                5);
        actions.waitAndClick(
                searchResult,
                "Cannot find article with '" + articleName + "' in search",
                5);
        actions.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15);
        actions.waitAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options",
                5);
        actions.waitAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5);
        actions.waitForElementPresent(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                5);
        actions.waitAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'OK' button",
                5);
        actions.waitAndClear(
                By.id("org.wikipedia:id/text_input"), "Cannot find input to set name of articles folder",
                5);

        String nameOfFolder = "Learning programming";

        actions.waitAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                nameOfFolder, "Cannot put text into articles folder input",
                5);
        actions.waitAndClick(
                By.id("android:id/button1"),
                "Cannot find 'OK' button",
                5);
        actions.waitAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5);
        actions.waitAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to my lists",
                5);
        actions.waitAndClick(
                By.xpath(format("//*[@text='%s']", nameOfFolder)),
                "Cannot find created folder",
                5);
        actions.swipeLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find saved article");
        actions.waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot delete saved article",
                5);
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        actions.waitAndClick(
                SEARCH_BOX,
                "Cannot find search block",
                5);

        String searchLine = "Linkin Park Disсography";
        actions.waitAndSendKeys(
                SEARCH_BOX_INPUT,
                searchLine,
                "Cannot find Search Input",
                5);

        By searchResultLocator = By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']");
        actions.waitForElementPresent(searchResultLocator, format("Cannot find anuthing by the request %s", searchLine), 15);
        int amountOfSearchResults = actions.getAmountOfElements(searchResultLocator);
        assertTrue("We found no search results", amountOfSearchResults > 0);
    }

    @Test
    public void testAmountOfEmptySearch() {
        actions.waitAndClick(
                SEARCH_BOX,
                "Cannot find search block",
                5);

        String searchLine = "dqsqdsqdd";
        actions.waitAndSendKeys(
                SEARCH_BOX_INPUT,
                searchLine,
                "Cannot find Search Input",
                5);

        By searchResultLocator = By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']");
        By emptyResultLabel = By.xpath("//*[@text='No results found']");

        actions.waitForElementPresent(emptyResultLabel, "Cannot find empty result label by the request " + searchLine, 15);
        assertElementNotPresent(searchResultLocator, "We've found some results by request " + searchLine);
    }

    @Test
    public void testChangeScreenOrientationOnSearchResults() {
        String searchText = "Java";
        String articleName = "Object-oriented programming language";
        By searchResult = By.xpath(format("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='%s']", articleName));

        actions.waitAndClick(
                SEARCH_BOX,
                "Cannot find search block",
                5);
        actions.waitAndSendKeys(
                SEARCH_BOX_INPUT,
                searchText,
                "Cannot find Search Input",
                5);
        actions.waitAndClick(
                searchResult,
                "Cannot find article with '" + articleName + "' in search",
                15);

        String titleBeforeRotation = actions.waitAndGetText(
                By.id("org.wikipedia:id/view_page_subtitle_text"),
                "cannot find title of article",
                15);

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String titleAfterRotation = actions.waitAndGetText(
                By.id("org.wikipedia:id/view_page_subtitle_text"),
                "cannot find title of article",
                15);

        assertEquals("Article title has been changed after rotation", titleBeforeRotation, titleAfterRotation);

        driver.rotate(ScreenOrientation.PORTRAIT);

        String titleAfterSecondRotation = actions.waitAndGetText(
                By.id("org.wikipedia:id/view_page_subtitle_text"),
                "cannot find title of article",
                15);

        assertEquals("Article title has been changed after rotation", titleAfterRotation, titleAfterSecondRotation);
    }

    @Test
    public void testCheckSearchArticleInBackground() {
        String searchText = "Java";
        String articleName = "Object-oriented programming language";
        By searchResult = By.xpath(format("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='%s']", articleName));

        actions.waitAndClick(
                SEARCH_BOX,
                "Cannot find search block",
                5);
        actions.waitAndSendKeys(
                SEARCH_BOX_INPUT,
                searchText,
                "Cannot find Search Input",
                5);
        actions.waitForElementPresent(
                searchResult,
                "Cannot find article with '" + articleName + "' in search",
                5);

        driver.runAppInBackground(2);

        actions.waitForElementPresent(
                searchResult,
                "Cannot find article after returning from background",
                5);
    }

    @Test
    public void testSaveTwoArticlesToMyList() {
        String searchText = "Java";
        String firstArticleName = "Java (programming language)"; //
        String secondArticleName = "JavaScript"; //
        By firstArticleLocator = By.xpath(format("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='%s']", firstArticleName));
        By secondArticleLocator = By.xpath(format("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='%s']", secondArticleName));

        actions.waitAndClick(
                SEARCH_BOX,
                "Cannot find search block",
                5);
        actions.waitAndSendKeys(
                SEARCH_BOX_INPUT,
                searchText,
                "Cannot find Search Input",
                5);
        actions.longPress(firstArticleLocator);
        actions.waitAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5);
        actions.waitForElementPresent(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                5);
        actions.waitAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'OK' button",
                5);
        actions.waitAndClear(
                By.id("org.wikipedia:id/text_input"), "Cannot find input to set name of articles folder",
                5);
        String nameOfFolder = "Learn programming";
        actions.waitAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                nameOfFolder, "Cannot put text into articles folder input",
                5);
        actions.waitAndClick(
                By.id("android:id/button1"),
                "Cannot find 'OK' button",
                5);
        actions.longPress(secondArticleLocator);
        actions.waitAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5);
        actions.waitForElementPresent(
                By.xpath("//*[@text='Save to reading list']"),
                "Cannot find Save to reading list header",
                5);
        actions.waitAndClick(
                By.xpath(format("//*[@text='%s']", nameOfFolder)),
                "Cannot find created folder " + nameOfFolder,
                5);
        actions.waitAndClick(
                By.xpath("//android.widget.ImageButton"),
                "Cannot close article, cannot find <- link",
                5);
        actions.waitAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to my lists",
                5);
        actions.waitAndClick(
                By.xpath(format("//*[@text='%s']", nameOfFolder)),
                "Cannot find created folder",
                5);
        actions.waitForElementPresent(
                By.id("org.wikipedia:id/page_list_item_container"),
                "Cannot find list of articles",
                5);
        int amountOfAddedArticlesBeforeDelete = actions.getAmountOfElements(By.id("org.wikipedia:id/page_list_item_container"));
        assertEquals("Amount of added articles not equals expected value", 2, amountOfAddedArticlesBeforeDelete);
        actions.swipeLeft(
                secondArticleLocator,
                "Cannot find saved article with description " + secondArticleName);
        actions.waitForElementNotPresent(
                By.xpath(format("//*[@text='%s']", secondArticleName)),
                "Cannot delete saved article with name " + secondArticleName,
                5);
        int amountOfAddedArticlesAfterDelete = actions.getAmountOfElements(By.id("org.wikipedia:id/page_list_item_container"));
        assertEquals("Amount of added articles not equals expected value", 1, amountOfAddedArticlesAfterDelete);
        actions.waitAndClick(
                firstArticleLocator,
                "Cannot open added article with name " + firstArticleName,
                5);
        WebElement articleTitleElement = actions.waitForElementPresent(
                ARTICLE_TITLE,
                "Title not displayed",
                15);
        String articleTitle = articleTitleElement.getText();
        assertEquals("We see unexpected title", firstArticleName, articleTitle);
    }

    @Test
    public void testArticleHasTitle() {
        String searchText = "Java";
        String articleName = "Java (programming language)";
        By searchResult = By.xpath(format("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='%s']", articleName));

        actions.waitAndClick(
                SEARCH_BOX,
                "Cannot find search block",
                5);
        actions.waitAndSendKeys(
                SEARCH_BOX_INPUT,
                searchText,
                "Cannot find Search Input",
                5);
        actions.waitAndClick(
                searchResult,
                "Cannot find article with '" + articleName + "' in search",
                15);
        assertElementPresent(By.id("org.wikipedia:id/view_page_title_text"), "Title is not present on page");
    }

    private void assertElementNotPresent(By by, String errorMessage) {
        if (actions.getAmountOfElements(by) > 0) {
            String defaultMessage = format("An element '%s' supposed to be not present", by.toString());
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }

    private void assertElementPresent(By by, String errorMessage) {
        if (actions.getAmountOfElements(by) == 0) {
            String defaultMessage = format("An element '%s' supposed to be present.", by.toString());
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }
}
