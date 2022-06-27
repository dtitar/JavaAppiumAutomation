package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;

abstract public class MyListsPageObject extends MainPageObject {

    protected static String
            FOLDER_BY_NAME_TPL,
            ARTICLE_BY_TITLE_TPL,
            MY_LISTS_ELEMENT,
            FOLDER_LISTS_ELEMENTS,
            FIRST_SAVED_ARTICLE_PATH,
            IS_ARTICLE_SAVED_ELEMENT;

    private static String getFolderXpathByName(String name_of_folder) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getSavedArticleXpathByTitle(String article_title) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }

    public MyListsPageObject (AppiumDriver driver) {
        super(driver);
    }

    public void swipeByArticleToDelete(String article_title) {
        this.waitForArticleToAppearByTitle(article_title);
        String article_xpath = getSavedArticleXpathByTitle(article_title);

        this.swipeElementToLeft(article_xpath, "Cannot find saved article");

        if (Platform.getInstance().isIOS()) {
            this.clickElementToTheRightUpperCorner(article_xpath, "Cannot find saved article");
        }
        this.waitForArticleToDisappearByTitle(article_title);
    }

    public void waitForArticleToAppearByTitle(String article_title) {
        String article_xpath = getSavedArticleXpathByTitle(article_title);

        this.waitForElementPresent(article_xpath, "Cannot find saved article by title " + article_title, 15);
    }

    public void waitForArticleToDisappearByTitle(String article_title) {
        String article_xpath = getSavedArticleXpathByTitle(article_title);

        this.waitForElementNotPresent(article_xpath, "Saved article still present with title " + article_title, 15);
    }

    public void selectFolderByName(String name_of_folder) {
        String folder_name_xpath = getFolderXpathByName(name_of_folder);

        this.waitForElementAndClick(folder_name_xpath, "Cannot find folder by name " + name_of_folder, 5);
    }

    public void openMyLists (int count_of_folders) {
        this.waitForElementAndClick(
                MY_LISTS_ELEMENT,
                "Cannot find navigation button 'My lists'",
                5
        );

        // Ждем загрузку списка папок
        this.waitForElementFullyLoaded(
                FOLDER_LISTS_ELEMENTS,
                count_of_folders,
                "The folder list was not loaded fully",
                15
        );
    }

    public void openFirstSavedArticleFromList() {
        this.waitForElementAndClick(
                FIRST_SAVED_ARTICLE_PATH,
                "Cannot find article to open in saved list",
                5
        );
    }

    public void checkOpenedArticleIsSaved() {
        this.waitForElementPresent(
                IS_ARTICLE_SAVED_ELEMENT,
                "The article is not saved",
                5
        );
    }
}