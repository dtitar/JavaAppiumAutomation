package tests.refactored_tests;

import lib.BaseTest;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ArticleTests extends BaseTest {

    @Test
    public void testCompareArticleTitle() {
        String expectedTitle = "Java (programming language)";

        SearchPageObject searchPage = new SearchPageObject(driver);
        searchPage.initSearchInput();
        searchPage.typeSearchLine("Java");
        searchPage.clickByArticleWithSubstring(expectedTitle);

        ArticlePageObject articlePage = new ArticlePageObject(driver);
        articlePage.waitForTitleElement();
        assertEquals("We see unexpected title", expectedTitle, articlePage.getArticleTitle());
    }

    @Test
    public void testSwipeArticle() {
        String searchLine = "Appium";

        SearchPageObject searchPage = new SearchPageObject(driver);
        searchPage.initSearchInput();
        searchPage.typeSearchLine(searchLine);
        searchPage.clickByArticleWithSubstring(searchLine);

        ArticlePageObject articlePage = new ArticlePageObject(driver);
        articlePage.waitForTitleElement();
        articlePage.swipeToFooter();
    }
}
