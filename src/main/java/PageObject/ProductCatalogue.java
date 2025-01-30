package PageObject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class ProductCatalogue {

    WebDriver driver;

    // Constructor to initialize the WebDriver and PageFactory elements
    public ProductCatalogue(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);  // Initialize elements with @FindBy annotations
    }

    // Locators
    @FindBy(xpath = "//div[@class='KzDlHZ']")
    List<WebElement> products;  // List of all products displayed on the catalogue page

    // **Method to get the list of all products**
    public List<WebElement> getProductList() {
        return products;  // Returns the list of products
    }

    // **Method to find a specific product by its full name**
    public WebElement getProductByName(String productName) {
        // Filters the list of products and returns the first match by exact product name
        return getProductList().stream()
                .filter(product -> product.getText().equalsIgnoreCase(productName))  // Compare product name case-insensitively
                .findFirst()
                .orElse(null);  // Return null if no product matches
    }

    // **Method to find a product by partial name**
    public WebElement getProductByPartialName(String partialProductName) {
        // Filters the list of products and returns the first match that contains the partial product name
        return getProductList().stream()
                .filter(product -> product.getText().toLowerCase().contains(partialProductName.toLowerCase()))  // Partial match case-insensitively
                .findFirst()
                .orElse(null);  // Return null if no partial match is found
    }

    // **Method to click on a product and switch to a new tab**
    public void clickOnProduct(String productName) {
        // Get the product WebElement by its exact name
        WebElement product = getProductByName(productName);

        if (product != null) {
            try {
                // Wait for the product to be visible and clickable
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.elementToBeClickable(product));

                // Click the product to navigate to its page
                product.click();

                // Switch to the new tab that opens
                switchToNewTab();
            } catch (Exception e) {
                // If the regular click fails, try using JavaScript for clicking
                System.out.println("Regular click failed, trying JavaScript click");
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", product);  // Execute JavaScript click
                switchToNewTab();  // Switch to the new tab after JavaScript click
            }
        } else {
            throw new RuntimeException("Product not found: " + productName);  // Throw exception if product is not found
        }
    }

    // **Method to switch to a new tab after clicking a product**
    private void switchToNewTab() {
        // Get all window handles (IDs) of the open tabs
        Set<String> windowHandles = driver.getWindowHandles();
        String currentWindowHandle = driver.getWindowHandle();  // Get current window handle

        // Switch to the new tab by comparing the window handles
        for (String handle : windowHandles) {
            if (!handle.equals(currentWindowHandle)) {  // Ensure it is not the current tab
                driver.switchTo().window(handle);  // Switch to the new tab
                break;
            }
        }
    }
}