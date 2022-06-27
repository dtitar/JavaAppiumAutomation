package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;

public class AndroidMyListsPageObject extends MyListsPageObject {
    static {
        FOLDER_BY_NAME_TPL = "xpath://*[@text='{FOLDER_NAME}']";
        ARTICLE_BY_TITLE_TPL = "xpath://*[@text='{TITLE}']";
        MY_LISTS_ELEMENT = "xpath://android.widget.FrameLayout[@content-desc='My lists']";
        FOLDER_LISTS_ELEMENTS = "xpath://*[@resource-id='org.wikipedia:id/reading_list_list']/*[@class='android.widget.FrameLayout']";
    }

    public AndroidMyListsPageObject(AppiumDriver driver) {
        super(driver);
    }
}