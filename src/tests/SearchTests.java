package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class SearchTests extends CoreTestCase {

    @Test
    public void testSearch() {
        SearchPageObject searchPage = new SearchPageObject(driver);
        searchPage.initSearchInput();
        searchPage.typeSearchLine("Java");
        searchPage.waitForSearchResult("Java (programming language)");
    }

    @Test
    public void testCancelSearch() {
        SearchPageObject searchPage = new SearchPageObject(driver);
        searchPage.initSearchInput();
        searchPage.waitForCancelButtonToAppear();
        searchPage.clickCancelSearch();
        searchPage.waitForCancelButtonToDisappear();
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        String searchText = "Linkin Park Disсography";

        SearchPageObject searchPage = new SearchPageObject(driver);
        searchPage.initSearchInput();
        searchPage.typeSearchLine(searchText);
        int amountOfSearchResults = searchPage.getAmountOfFoundArticles();

        assertTrue("We found no search results", amountOfSearchResults > 0);
    }

    @Test
    public void testAmountOfEmptySearch() {
        String searchLine = "dqsqdsqdd";

        SearchPageObject searchPage = new SearchPageObject(driver);
        searchPage.initSearchInput();
        searchPage.typeSearchLine(searchLine);
        searchPage.waitForEmptyResultsLabel();
        searchPage.assertThereIsNoResultOfSearch();
    }

    @Test
    public void testEachSearchResultContainsSearchText() {
        String searchLine = "Java";

        SearchPageObject searchPage = new SearchPageObject(driver);
        searchPage.initSearchInput();
        searchPage.typeSearchLine(searchLine);
        searchPage.waitForSearchResults();

        searchPage.assertEachArticleTitleContainsText(searchLine);
    }

    @Test
    public void testClearSearch() {
        String searchLine = "Java";

        SearchPageObject searchPage = new SearchPageObject(driver);
        searchPage.initSearchInput();
        searchPage.typeSearchLine(searchLine);
        searchPage.clearSearch();
        searchPage.clickCancelSearch();
        searchPage.assertThereIsNoResultOfSearch();
    }

    @Test
    public void testSearchFieldHasText() {
        String expectedText = "Search Wikipedia";

        SearchPageObject searchPage = new SearchPageObject(driver);
        searchPage.assertSearchInputFieldHasText(expectedText);
    }

    //Ex3: Тест: отмена поиска
    @Test
    public void testCancelSearchResults() {
        SearchPageObject searchPage = new SearchPageObject(driver);
        searchPage.initSearchInput();
        searchPage.typeSearchLine("Canada");
        searchPage.waitForSearchResults();
        assertTrue("Search results size less than 2", searchPage.getAmountOfFoundArticles() > 1);
        searchPage.clickCancelSearch();
        searchPage.assertThereIsNoResultOfSearch();
    }

    //Ex9*: Рефакторинг темплейта
    @Test
    public void testSearchResultsByTitleAndDescription() {
        String searchLine = "Java";

        SearchPageObject searchPage = new SearchPageObject(driver);
        searchPage.initSearchInput();
        searchPage.typeSearchLine(searchLine);
        searchPage.waitForSearchResults();

        searchPage.assertSearchResultWithTitleAndDescriptionDisplayed("Java", "Island of Indonesia, Southeast Asia");
        searchPage.assertSearchResultWithTitleAndDescriptionDisplayed("JavaScript", "High-level programming language");
        searchPage.assertSearchResultWithTitleAndDescriptionDisplayed("Java (programming language)", "Object-oriented programming language");
    }
}