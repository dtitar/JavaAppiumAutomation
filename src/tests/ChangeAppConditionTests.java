package tests;

import lib.BaseTest;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ChangeAppConditionTests extends BaseTest {

    @Test
    public void testChangeScreenOrientationOnSearchResults() {
        String searchText = "Java";

        SearchPageObject searchPage = new SearchPageObject(driver);
        searchPage.initSearchInput();
        searchPage.typeSearchLine(searchText);
        searchPage.clickByArticleWithSubstring("Object-oriented programming language");
        ArticlePageObject articlePage = new ArticlePageObject(driver);
        String titleBeforeRotation = articlePage.getArticleTitle();
        this.rotateScreenLandscape();
        String titleAfterRotation = articlePage.getArticleTitle();

        assertEquals("Article title has been changed after rotation", titleBeforeRotation, titleAfterRotation);

        this.rotateScreenPortrait();
        String titleAfterSecondRotation = articlePage.getArticleTitle();

        assertEquals("Article title has been changed after rotation", titleAfterRotation, titleAfterSecondRotation);
    }

    @Test
    public void testCheckSearchArticleInBackground() {
        String searchText = "Java";
        String searchResultText = "Object-oriented programming language";

        SearchPageObject searchPage = new SearchPageObject(driver);
        searchPage.initSearchInput();
        searchPage.typeSearchLine(searchText);
        searchPage.waitForSearchResult(searchResultText);
        this.backgroundApp(2);
        searchPage.waitForSearchResult(searchResultText);
    }
}
