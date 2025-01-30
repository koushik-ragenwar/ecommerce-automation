package Tests;

import PageObject.CartPage;
import PageObject.ProductCatalogue;
import PageObject.ProductPage;
import PageObject.SearchPage;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class TestEndToEndShopping extends ConfigTest {

    @Test
    public void testShoppingFlow() throws InterruptedException, IOException {
        // Step 1: Launch the Flipkart application
        LaunchApplication();

        // Step 2: Search for the product
        SearchPage searchPage = new SearchPage(getDriver());  // Use getDriver() to fetch the initialized WebDriver
        searchPage.SearchItems("IPHONE 16 BLACK 128GB");
        Thread.sleep(3000);  // Wait for search results to load

        // Step 3: Navigate to the product catalogue and find the product
        ProductCatalogue productCatalogue = new ProductCatalogue(getDriver());
        List<WebElement> products = productCatalogue.getProductList();

        // Click on the product if found
        productCatalogue.getProductByName("Apple iPhone 16 (Black, 128 GB)");
        Thread.sleep(3000);
        productCatalogue.clickOnProduct("Apple iPhone 16 (Black, 128 GB)");
        Thread.sleep(3000);

        // Step 4: Check and add the product to the cart
        ProductPage productPage = new ProductPage(getDriver());
        productPage.checkAndAddToCart("411052");  // Example pincode

        // Step 5: Proceed to the Cart and place the order
        CartPage cartPage = new CartPage(getDriver());
        cartPage.proceedWithOrder();  // Click on 'Place Order' button

        // Step 6: Close the browser after the test
        getDriver().quit();
    }
}