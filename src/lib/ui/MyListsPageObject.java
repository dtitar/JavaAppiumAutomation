package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

import static java.lang.String.format;
import static org.openqa.selenium.By.xpath;

public class MyListsPageObject extends MainPageObject {

    public static final String FOLDER_BY_NAME_XPATH_TPL = "xpath://*[@text='%s']";
    public static final String ARTICLE_BY_TITLE_XPATH_TPL = "xpath://*[@text='%s']";

    private static final String ARTICLE_ITEM = "id:org.wikipedia:id/page_list_item_container";

    public MyListsPageObject(AppiumDriver driver) {
        super(driver);
    }

    private static String getArticleXpathByName(String articleName) {
        return format(ARTICLE_BY_TITLE_XPATH_TPL, articleName);
    }

    public void openFolderByName(String folderName) {
        this.waitForElementAndClick(format(FOLDER_BY_NAME_XPATH_TPL, folderName),
                "Cannot find folder with name " + folderName,
                5);
    }

    public void openArticle(String articleTitle) {
        this.waitForElementAndClick(format(ARTICLE_BY_TITLE_XPATH_TPL, articleTitle),
                "Cannot find article with title " + articleTitle,
                5);
    }

    public void swipeByArticleToDelete(String articleTitle) {
        this.waitForArticleToAppearByTitle(articleTitle);
        this.swipeLeft(getArticleXpathByName(articleTitle),
                "Cannot find saved article");
        this.waitForArticleToDisappearByTitle(articleTitle);
    }

    public void waitForArticleToDisappearByTitle(String articleTitle) {
        this.waitForElementNotPresent(getArticleXpathByName(articleTitle),
                "Saved article still present with title " + articleTitle,
                5);
    }

    public void waitForArticleToAppearByTitle(String articleTitle) {
        this.waitForElementPresent(
                getArticleXpathByName(articleTitle),
                "Cannot find saved article by title " + articleTitle,
                5);
    }

    public int getAmountOfArticles() {
        return this.getAmountOfElements(ARTICLE_ITEM);
    }
}