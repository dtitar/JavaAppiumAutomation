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
}
