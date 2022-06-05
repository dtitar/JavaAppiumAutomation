package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static java.lang.String.format;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.By.xpath;

public class SearchPageObject extends MainPageObject {

    private static final By SEARCH_INIT_ELEMENT = xpath("//*[contains(@text,'Search Wikipedia')]");
    private static final By SEARCH_INPUT = By.id("org.wikipedia:id/search_src_text");

    private static final By SEARCH_CANCEL_BTN = By.id("org.wikipedia:id/search_close_btn");
    private static final String SEARCH_RESULT_XPATH_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='%s']";

    private static final By SEARCH_RESULT_ELEMENT = By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']");

    private static final By SEARCH_EMPTY_RESULT_ELEMENT = By.xpath("//*[@text='No results found']");

    private static final String ARTICLE_BY_TITLE_AND_DESCRIPTION_XPATH_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='%s']/following-sibling::*[@resource-id='org.wikipedia:id/page_list_item_description'][@text='%s']";

    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }


    //TEMPLATES METHODS
    private By getArticleLocatorByTitleAndDescription(String title, String description) {
        return xpath(format(ARTICLE_BY_TITLE_AND_DESCRIPTION_XPATH_TPL, title, description));
    }
    //

    public WebElement waitForElementByTitleAndDescription(String title, String description) {
        return waitForElementPresent(getArticleLocatorByTitleAndDescription(title, description), format("Cannot find element with title '%s' and description '%s'", title, description));
    }

    public void assertSearchResultWithTitleAndDescriptionDisplayed(String title, String description) {
        if (getAmountOfElements(getArticleLocatorByTitleAndDescription(title, description)) == 0) {
            String defaultMessage = format("An element with title '%s' and description '%s' is not present", title, description);
            throw new AssertionError(defaultMessage);
        }
    }

    public void initSearchInput() {
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click Search init element", 5);
        this.waitForElementPresent(SEARCH_INIT_ELEMENT, "Cannot find search input after clicking search init element");
    }

    public void typeSearchLine(String searchLine) {
        this.waitForElementAndSendKeys(SEARCH_INPUT, searchLine, "Cannot find and type into search input", 5);
    }

    public void waitForSearchResult(String resultText) {
        this.waitForElementPresent(By.xpath(format(SEARCH_RESULT_XPATH_TPL, resultText)), "Cannot find search result with text - " + resultText);
    }

    public void waitForSearchResults() {
        this.waitForElements(SEARCH_RESULT_ELEMENT, "Cannot find search results", 15);
    }

    public void clickByArticleWithSubstring(String substring) {
        this.waitForElementAndClick(By.xpath(format(SEARCH_RESULT_XPATH_TPL, substring)), "Cannot find and click search result with substring - " + substring, 10);
    }


    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(SEARCH_CANCEL_BTN, "Cannot find search cancel button", 5);
    }

    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(SEARCH_CANCEL_BTN, "Search cancel button is still present", 5);
    }

    public void clickCancelSearch() {
        this.waitForElementAndClick(SEARCH_CANCEL_BTN, "Cannot find and click search cancel button", 5);
    }

    public int getAmountOfFoundArticles() {
        this.waitForElementPresent(SEARCH_RESULT_ELEMENT, format("Cannot find anuthing by the request"), 15);
        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public void waitForEmptyResultsLabel() {
        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT, "Cannot find empty result element", 15);
    }

    public void assertThereIsNoResultOfSearch() {
        this.assertElementNotPresent(SEARCH_RESULT_ELEMENT, "We supposed not to find any results");
    }

    public List<String> getArticlesTitles() {
        return getElementsText(getElementsBy(SEARCH_RESULT_ELEMENT));
    }

    public void assertEachArticleTitleContainsText(String text) {
        List<String> articlesTitles = this.getArticlesTitles();
        articlesTitles.forEach(e -> assertTrue(format("Search result %s doesn't contain text %s", e, text), e.contains(text)));
    }

    public void clearSearch() {
        this.waitForElementAndClear(SEARCH_INPUT, "Cannot clear input line", 5);
    }

    public void assertSearchInputFieldHasText(String expectedText) {
        this.assertElementHasText(SEARCH_INPUT, expectedText, "Text in search field not equals expected");
    }
}
