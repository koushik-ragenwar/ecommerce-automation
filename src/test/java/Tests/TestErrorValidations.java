package Tests;

import PageObject.ProductCatalogue;
import PageObject.ProductPage;
import PageObject.SearchPage;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import java.util.List;

public class TestErrorValidations extends ConfigTest{

    @Test
    public void TestErrorMessages() throws InterruptedException {
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
        productPage.checkPincodeAvailability("000000");

        // Step 5: Validate Error Message
        Assert.assertEquals(productPage.ErrorMessage(), "Not a valid pincode");
    }
}
