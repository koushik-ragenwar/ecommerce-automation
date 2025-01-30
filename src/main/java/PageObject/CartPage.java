package PageObject;

import Common.Timers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage extends Timers {

    WebDriver driver;

    // Constructor to initialize WebDriver and PageFactory
    public CartPage(WebDriver driver) {
        super(driver);  // Calls the parent class constructor to initialize any timer-related functionality
        this.driver = driver;
        PageFactory.initElements(driver, this);  // Initializes the web elements annotated with @FindBy
    }

    // **Locators**

    // Locator for "Place Order" button using XPath
    @FindBy(xpath = "//button/span[text()='Place Order']")  // XPath to locate the "Place Order" button
            WebElement placeOrderButton;

    // Locator for "Place Order" button as a By object
    By placeOrderButtonBy = By.xpath("//span[contains(text(),'Place Order')]");

    // **Method to click the "Place Order" button**
    // This method waits for the "Place Order" button to appear on the page and clicks it
    public void clickPlaceOrder() {
        // Wait for the "Place Order" button to become visible before clicking
        waitForElementToAppear(placeOrderButtonBy);

        // Click the "Place Order" button
        placeOrderButton.click();

        // Log a message to indicate that the order has been placed
        System.out.println("Order Placed.");
    }

    // **Main flow to proceed with placing the order**
    // This method combines the process of clicking the "Place Order" button to complete the order placement
    public void proceedWithOrder() {
        // Call the clickPlaceOrder method to place the order
        clickPlaceOrder();
    }
}