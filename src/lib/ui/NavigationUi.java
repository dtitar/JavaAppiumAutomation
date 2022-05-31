package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class NavigationUi extends MainPageObject{

    private static final By MY_LISTS_LINK = By.xpath("//android.widget.FrameLayout[@content-desc='My lists']");

    public NavigationUi(AppiumDriver driver) {
        super(driver);
    }

    public void clickMyLists() {
        this.waitForElementAndClick(
                MY_LISTS_LINK,
                "Cannot find navigation button to my lists",
                5);
    }
}
