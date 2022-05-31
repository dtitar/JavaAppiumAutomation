package tests.simple_android_tests;

import lib.BaseTest;
import lib.ui.MainPageObject;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.By.xpath;

public class SimpleAndroidTest extends BaseTest {

    private MainPageObject mainPageObject;

    protected void setUp() throws Exception {
        super.setUp();
        mainPageObject = new MainPageObject(driver);
    }

    private static final By EXPECTED_SEARCH_RESULT = By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']");
    private static final By SEARCH_BOX = xpath("//*[contains(@text,'Search Wikipedia')]");
    private static final By SEARCH_BOX_INPUT = By.id("org.wikipedia:id/search_src_text");
    private static final By SEARCH_CLOSE_BTN = By.id("org.wikipedia:id/search_close_btn");
    private static final By ARTICLE_TITLE = By.id("org.wikipedia:id/view_page_title_text");
    private static final String SEARCH_ITEM_XPATH = "//*[@resource-id='org.wikipedia:id/page_list_item_container']";
    private static final By SEARCH_ITEM = By.xpath(SEARCH_ITEM_XPATH);
    private static final By SEARCH_ITEM_TITLE = By.xpath(SEARCH_ITEM_XPATH + "//*[@resource-id='org.wikipedia:id/page_list_item_title']");


    @Test
    public void testSearch() {
        mainPageObject.waitForElementAndClick(SEARCH_BOX, "Cannot find search block", 5);
        mainPageObject.waitForElementAndSendKeys(SEARCH_BOX_INPUT, "Java", "Cannot find Search Input", 5);
        mainPageObject.waitForElementPresent(EXPECTED_SEARCH_RESULT,
                "Cannot find expected search result",
                15);
    }

    @Test
    public void testCancelSearch() {
        mainPageObject.waitForElementAndClick(SEARCH_BOX, "Cannot find search block", 5);
        mainPageObject.waitForElementPresent(SEARCH_BOX_INPUT, "Search input not displayed");
        mainPageObject.waitForElementAndClick(SEARCH_CLOSE_BTN, "Cannot find X to close search", 5);
        mainPageObject.waitForElementNotPresent(EXPECTED_SEARCH_RESULT, "Search close button still displayed", 5);
    }

    @Test
    public void testClearSearch() {
        mainPageObject.waitForElementAndClick(SEARCH_BOX, "Cannot find search block", 5);
        mainPageObject.waitForElementAndSendKeys(SEARCH_BOX_INPUT, "Java", "Cannot find Search Input", 10);
        mainPageObject.waitForElementAndClear(SEARCH_BOX_INPUT, "Cannot clear search text", 5);
        mainPageObject.waitForElementAndClick(SEARCH_CLOSE_BTN, "Cannot find X to close search", 5);
        mainPageObject.waitForElementNotPresent(EXPECTED_SEARCH_RESULT, "Search close button still displayed", 5);
    }

    @Test
    public void testCompareArticleTitle() {
        String expectedTitle = "Java (programming language)";

        mainPageObject.waitForElementAndClick(SEARCH_BOX, "Cannot find search block", 5);
        mainPageObject.waitForElementAndSendKeys(SEARCH_BOX_INPUT, "Java", "Cannot find Search Input", 5);
        mainPageObject.waitForElementAndClick(EXPECTED_SEARCH_RESULT, "Expected search result not displayed", 5);
        WebElement articleTitleElement = mainPageObject.waitForElementPresent(ARTICLE_TITLE, "Title not displayed", 15);

        String articleTitle = articleTitleElement.getAttribute("text");
        assertEquals("We see unexpected title", expectedTitle, articleTitle);
    }

    @Test
    public void testSearchFieldHasText() {
        String expectedText = "Search Wikipedia";
        mainPageObject.assertElementHasText(SEARCH_BOX, expectedText, "Text in search field not equals expected");
    }

    @Test
    public void testCancelSearchResults() {
        mainPageObject.waitForElementAndClick(SEARCH_BOX, "Cannot find search block", 5);
        mainPageObject.waitForElementAndSendKeys(SEARCH_BOX_INPUT, "Canada", "Cannot find Search Input", 5);
        assertTrue("Search results size less than 2", mainPageObject.getElementsSize(SEARCH_ITEM) > 1);
        mainPageObject.waitForElementAndClick(SEARCH_CLOSE_BTN, "Cannot find X to close search", 5);
        mainPageObject.waitForElementNotPresent(SEARCH_ITEM, "Search results still displayed", 5);
    }

    @Test
    public void testEachSearchResultContainsSearchWord() {
        String searchWord = "Java";
        mainPageObject.waitForElementAndClick(SEARCH_BOX, "Cannot find search block", 5);
        mainPageObject.waitForElementAndSendKeys(SEARCH_BOX_INPUT, searchWord, "Cannot find Search Input", 5);
        List<String> searchResultsText = mainPageObject.getElementsText(mainPageObject.getElementsBy(SEARCH_ITEM_TITLE));
        searchResultsText.forEach(e -> assertTrue(format("Search result %s doesn't contain word %s", e, searchWord), e.contains(searchWord)));
    }
}

