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

        // Step 1: Initialize SearchPage and search for the product
        SearchPage searchPage = new SearchPage(getDriver());  // Fetch the initialized WebDriver
        searchPage.SearchItems("IPHONE 16 BLACK 128GB");
        Thread.sleep(3000);  // Wait for search results to load

        // Step 2: Navigate to the product catalogue and retrieve the list of products
        ProductCatalogue productCatalogue = new ProductCatalogue(getDriver());
        List<WebElement> products = productCatalogue.getProductList();

        // Step 3: Find the specific product and click on it
        productCatalogue.getProductByName("Apple iPhone 16 (Black, 128 GB)");
        Thread.sleep(3000);
        productCatalogue.clickOnProduct("Apple iPhone 16 (Black, 128 GB)");
        Thread.sleep(3000);

        // Step 4: Initialize ProductPage, check pincode availability, and add product to the cart
        ProductPage productPage = new ProductPage(getDriver());
        productPage.checkAndAddToCart("411052");  // Example pincode

        // Step 5: Proceed to the cart and place the order
        CartPage cartPage = new CartPage(getDriver());
        cartPage.proceedWithOrder();  // Click on 'Place Order' button
    }
}