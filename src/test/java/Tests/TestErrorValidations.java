package Tests;

import PageObject.ProductCatalogue;
import PageObject.ProductPage;
import PageObject.SearchPage;
import Utilities.ConfigTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class TestErrorValidations extends ConfigTest {

    // DataProvider to fetch test data from a JSON file
    @DataProvider
    public Object[][] getData() throws IOException {
        // Step 1: Read product details from a JSON file
        List<HashMap<String, String>> data = DataReader(System.getProperty("user.dir") + "/src/TestData/ProductDetails.json");

        // Step 2: Return the first entry in the data as the test case input
        return new Object[][]{{data.get(0)}};
    }

    // Test to validate error messages on the ProductPage (e.g., invalid pincode)
    @Test(dataProvider = "getData", groups = {"Regression"})
    public void TestErrorMessages(HashMap<String, String> input) throws InterruptedException {

        // Step 1: Initialize SearchPage and perform a product search using the provided product name
        SearchPage searchPage = new SearchPage(getDriver());  // Fetch the initialized WebDriver
        searchPage.SearchItems(input.get("Product"));
        Thread.sleep(3000);  // Wait for search results to load

        // Step 2: Initialize ProductCatalogue and retrieve the list of products
        ProductCatalogue productCatalogue = new ProductCatalogue(getDriver());
        List<WebElement> products = productCatalogue.getProductList();  // Get the list of product elements

        // Step 3: Locate and click on the specific product by its name
        productCatalogue.getProductByName(input.get("SearchedData"));
        Thread.sleep(3000);  // Wait for the product details page to load
        productCatalogue.clickOnProduct(input.get("SearchedData"));
        Thread.sleep(3000);  // Wait for the product page to load

        // Step 4: Initialize ProductPage and check pincode availability with an invalid pincode
        ProductPage productPage = new ProductPage(getDriver());
        productPage.checkPincodeAvailability(input.get("InvalidPinCode"));  // Example of entering an invalid pincode

        // Step 5: Validate that the error message for the incorrect pincode is displayed
        Assert.assertEquals(productPage.ErrorMessage(), "Not a valid pincode");
    }
}