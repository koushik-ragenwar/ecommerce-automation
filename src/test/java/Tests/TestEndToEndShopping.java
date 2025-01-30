package Tests;

import PageObject.CartPage;
import PageObject.ProductCatalogue;
import PageObject.ProductPage;
import PageObject.SearchPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.Test;

import java.util.List;

public class TestEndToEndShopping {

    @Test
    public void testShoppingFlow() throws InterruptedException {
        // Automatically set up the SafariDriver
        WebDriverManager.safaridriver().setup();

        // Create a new instance of the Safari browser
        WebDriver driver = new SafariDriver();

        // Maximize the browser window
        driver.manage().window().maximize();

        // Open Flipkart
        driver.get("https://www.flipkart.com");
        Thread.sleep(3000);  // Wait for the page to load

        // Step 1: Search for the product
        SearchPage searchPage = new SearchPage(driver);
        searchPage.SearchItems("IPHONE 16 BLACK 128GB");
        Thread.sleep(3000);  // Wait for search results to load

        // Step 2: Navigate to the product catalogue and find the product
        ProductCatalogue productCatalogue = new ProductCatalogue(driver);
        List<WebElement> products = productCatalogue.getProductList();

        // Click on the product if found
        productCatalogue.getProductByName("Apple iPhone 16 (Black, 128 GB)");
        Thread.sleep(3000);  // Wait for product page to load
        productCatalogue.clickOnProduct("Apple iPhone 16 (Black, 128 GB)");
        Thread.sleep(3000);  // Wait for product page to load

        // Step 3: Check and add the product to the cart
        ProductPage productPage = new ProductPage(driver);
        productPage.checkAndAddToCart("411052");  // Example pincode

        // Step 4: Proceed to the Cart and place the order
        CartPage cartPage = new CartPage(driver);
        cartPage.proceedWithOrder();  // Click on 'Place Order' button

        // Close the browser after the test
        driver.quit();
    }
}