package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchTests extends CoreTestCase {

    @Test
    public void testSearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);;

        SearchPageObject.initSearchInput();
        String search_line = "Linkin Park Diskography";
        SearchPageObject.typeSearchLine(search_line);

        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();

        assertTrue(
                "We found too few results",
                amount_of_search_results > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);;

        SearchPageObject.initSearchInput();
        String search_line = "zxcvasdfqwer";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    public void testCancelSearchResults() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);;

        SearchPageObject.initSearchInput();
        String search_line = "Android";
        SearchPageObject.typeSearchLine(search_line);

        int elementsCountOnPage = SearchPageObject.getAmountOfFoundArticles();
        assertTrue(
                "We found too few results",
                elementsCountOnPage > 0
        );

        // Отменяем результат поиска - Х
        SearchPageObject.clickCancelSearch();

        elementsCountOnPage = SearchPageObject.getAmountOfArticles();
        // Количество должно быть 0
        assertTrue("The search result is not cleared", elementsCountOnPage == 0);
    }

    @Test
    public void testCheckSearchResultStrings() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);;

        SearchPageObject.initSearchInput();
        String search_line = "Java";
        SearchPageObject.typeSearchLine(search_line);

        // Вычисляем количество строк результата поиска
        int elementsCountOnPage = SearchPageObject.getAmountOfFoundArticles();
        assertTrue(
                "The search result is empty",
                elementsCountOnPage > 0
        );

        // Проходим по каждому элементу результата поиска и проверяем наличие заданного слова
        List<WebElement> element_list;

        if (Platform.getInstance().isAndroid()) {
            element_list = driver.findElements(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"));
        } else {
            element_list = driver.findElements(By.xpath("//XCUIElementTypeLink[@type='XCUIElementTypeLink']"));
        }

        String str;
        search_line = search_line.toLowerCase();

        for (int i = 0; i < elementsCountOnPage; i++) {
            if (Platform.getInstance().isAndroid()) {
                str = element_list.get(i).getAttribute("text").toLowerCase();
            } else {
                str = element_list.get(i).getAttribute("name").toLowerCase();
            }
            assertTrue("The search result string does not contain search word " + search_line, str.indexOf(search_line) > -1);
        }
    }

    @Test
    public void testForTextElementInSearchField() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);;

        SearchPageObject.assertSearchElementHasText("Search Wikipedia");
    }
}
