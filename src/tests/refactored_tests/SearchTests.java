package tests.refactored_tests;

import lib.BaseTest;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class SearchTests extends BaseTest {

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
}
