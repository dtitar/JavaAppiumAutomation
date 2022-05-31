package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

import static org.openqa.selenium.By.xpath;

public class SearchPageObject extends MainPageObject {

    private static final By SEARCH_INIT_ELEMENT = xpath("//*[contains(@text,'Search Wikipedia')]");
    private static final By SEARCH_INPUT = By.id("org.wikipedia:id/search_src_text");

    private static final By SEARCH_CANCEL_BTN = By.id("org.wikipedia:id/search_close_btn");
    private static final String SEARCH_RESULT_XPATH_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='%s']";

    private static final By SEARCH_RESULT_ELEMENT = By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']");

    private static final By SEARCH_EMPTY_RESULT_ELEMENT = By.xpath("//*[@text='No results found']");

    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    public void initSearchInput() {
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click Search init element", 5);
        this.waitForElementPresent(SEARCH_INIT_ELEMENT, "Cannot find search input after clicking search init element");
    }

    public void typeSearchLine(String searchLine) {
        this.waitForElementAndSendKeys(SEARCH_INPUT, searchLine, "Cannot find and type into search input", 5);
    }

    public void waitForSearchResult(String resultText) {
        this.waitForElementPresent(By.xpath(String.format(SEARCH_RESULT_XPATH_TPL, resultText)), "Cannot find search result with text - " + resultText);
    }

    public void clickByArticleWithSubstring(String substring) {
        this.waitForElementAndClick(By.xpath(String.format(SEARCH_RESULT_XPATH_TPL, substring)), "Cannot find and click search result with substring - " + substring, 10);
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
        this.waitForElementPresent(SEARCH_RESULT_ELEMENT, String.format("Cannot find anuthing by the request"), 15);
        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public void waitForEmptyResultsLabel() {
        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT, "Cannot find empty result element", 15);
    }

    public void assertThereIsNoResultOfSearch() {
        this.assertElementNotPresent(SEARCH_RESULT_ELEMENT, "We supposed not to find any results");
    }
}
