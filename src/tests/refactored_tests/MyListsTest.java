package tests.refactored_tests;

import lib.BaseTest;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUi;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class MyListsTest extends BaseTest {

    @Test
    public void testSaveFirstArticleToMyList() {
        String searchText = "Java";

        SearchPageObject searchPage = new SearchPageObject(driver);
        searchPage.initSearchInput();
        searchPage.typeSearchLine(searchText);
        searchPage.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject articlePage = new ArticlePageObject(driver);
        articlePage.waitForTitleElement();
        String articleTitle = articlePage.getArticleTitle();
        String nameOfFolder = "Learning programming";
        articlePage.addArticleToMyList(nameOfFolder);
        articlePage.closeArticle();
        NavigationUi navigation = new NavigationUi(driver);
        navigation.clickMyLists();
        MyListsPageObject myListsPage = new MyListsPageObject(driver);
        myListsPage.openFolderByName(nameOfFolder);
        myListsPage.swipeByArticleToDelete(articleTitle);
    }

    //Ex5: Тест: сохранение двух статей
    @Test
    public void testSaveTwoArticlesToMyList() {
        String searchText = "Java";

        //step 1
        SearchPageObject searchPage = new SearchPageObject(driver);
        searchPage.initSearchInput();
        searchPage.typeSearchLine(searchText);
        searchPage.clickByArticleWithSubstring("Java (programming language)");
        ArticlePageObject articlePage = new ArticlePageObject(driver);
        articlePage.waitForTitleElement();
        String firstArticleTitle = articlePage.getArticleTitle();
        String nameOfFolder = "Learning programming";
        articlePage.addArticleToMyList(nameOfFolder);
        articlePage.closeArticle();

        searchPage.initSearchInput();
        searchPage.typeSearchLine(searchText);
        searchPage.clickByArticleWithSubstring("JavaScript");
        articlePage.waitForTitleElement();
        String secondArticleTitle = articlePage.getArticleTitle();
        articlePage.addArticleToMyList(nameOfFolder);
        articlePage.closeArticle();

        //step 2
        NavigationUi navigation = new NavigationUi(driver);
        navigation.clickMyLists();
        MyListsPageObject myListsPage = new MyListsPageObject(driver);
        myListsPage.openFolderByName(nameOfFolder);
        int amountOfAddedArticlesBeforeDelete = myListsPage.getAmountOfArticles();

        assertEquals("The number of articles does not match the expected", 2, amountOfAddedArticlesBeforeDelete);

        myListsPage.swipeByArticleToDelete(firstArticleTitle);

        //step 3
        int amountOfAddedArticlesAfterDelete = myListsPage.getAmountOfArticles();

        assertEquals("The number of articles does not match the expected", 1, amountOfAddedArticlesAfterDelete);

        //step 4
        myListsPage.openArticle(secondArticleTitle);
        assertEquals("Article title not equals expected", secondArticleTitle, articlePage.getArticleTitle());
    }
}
