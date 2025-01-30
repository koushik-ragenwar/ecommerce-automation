package PageObject;

import Common.Timers;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchPage extends Timers {

    // **Locator for the search bar input field**
    @FindBy(xpath = "//input[@class='Pke_EE' and @type='text' and @name='q' and @title='Search for Products, Brands and More']")
    WebElement searchBar;

    WebDriver driver;

    // Constructor to initialize WebDriver and PageFactory elements
    public SearchPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);  // Initialize elements using @FindBy annotations
    }

    // **Method to perform a search for items**
    public void SearchItems(String items) {
        // Wait for the search bar to be visible and clickable using a direct By locator
        waitForElementToAppear(By.xpath("//input[@class='Pke_EE' and @type='text' and @name='q' and @title='Search for Products, Brands and More']"));

        // Click on the search bar to focus
        searchBar.click();

        // Enter the items to search for
        searchBar.sendKeys(items);

        // Simulate pressing the ENTER key to submit the search query
        searchBar.sendKeys(Keys.ENTER);
    }
}