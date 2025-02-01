package Tests;

import PageObject.ProductCatalogue;
import PageObject.ProductPage;
import PageObject.SearchPage;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class TestErrorValidations extends ConfigTest {

    @Test
    public void TestErrorMessages() throws InterruptedException {
        // Step 1: Initialize SearchPage and perform a product search
        SearchPage searchPage = new SearchPage(getDriver());  // Fetch the initialized WebDriver
        searchPage.SearchItems("IPHONE 16 BLACK 128GB");
        Thread.sleep(3000);  // Wait for search results to load

        // Step 2: Initialize ProductCatalogue and get the list of products
        ProductCatalogue productCatalogue = new ProductCatalogue(getDriver());
        List<WebElement> products = productCatalogue.getProductList();

        // Step 3: Find and click on the specific product
        productCatalogue.getProductByName("Apple iPhone 16 (Black, 128 GB)");
        Thread.sleep(3000);
        productCatalogue.clickOnProduct("Apple iPhone 16 (Black, 128 GB)");
        Thread.sleep(3000);

        // Step 4: Initialize ProductPage and check pincode availability
        ProductPage productPage = new ProductPage(getDriver());
        productPage.checkPincodeAvailability("000000");  // Enter an invalid pincode

        // Step 5: Validate the error message for incorrect pincode
        Assert.assertEquals(productPage.ErrorMessage(), "Not a valid pincode");
    }
}