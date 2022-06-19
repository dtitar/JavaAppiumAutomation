package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ArticleTests extends CoreTestCase {

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

    //Ex6: Тест: assert title
    @Test
    public void testArticleHasTitle() {
        String searchText = "Java";
        String expectedTitle = "Java (programming language)";

        SearchPageObject searchPage = new SearchPageObject(driver);
        searchPage.initSearchInput();
        searchPage.typeSearchLine(searchText);
        searchPage.clickByArticleWithSubstring(expectedTitle);

        ArticlePageObject articlePage = new ArticlePageObject(driver);
        articlePage.assertArticleHasTitle();
    }
}
