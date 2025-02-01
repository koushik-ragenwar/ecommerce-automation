package Tests;

import PageObject.CartPage;
import PageObject.ProductCatalogue;
import PageObject.ProductPage;
import PageObject.SearchPage;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class TestEndToEndShopping extends ConfigTest {

    // DataProvider to fetch test data from a JSON file
    @DataProvider
    public Object[][] getData() throws IOException
    {
        // Read product details from a JSON file
        List<HashMap<String,String>> data = DataReader(System.getProperty("user.dir")+"/src/TestData/ProductDetails.json");
        // Return the first entry in the data as the test case input
        return new Object[][]  {{data.getFirst()}};
    }

    @Test(dataProvider="getData",groups = {"Sanity"})
    public void testShoppingFlow(HashMap<String,String> input) throws InterruptedException, IOException {

        // Step 1: Initialize SearchPage and perform a product search using provided product name
        SearchPage searchPage = new SearchPage(getDriver());  // Fetch the initialized WebDriver
        searchPage.SearchItems(input.get("Product"));
        Thread.sleep(3000);  // Wait for search results to load

        // Step 2: Navigate to the ProductCatalogue page and retrieve the list of products
        ProductCatalogue productCatalogue = new ProductCatalogue(getDriver());
        List<WebElement> products = productCatalogue.getProductList();

        // Step 3: Locate the specific product by name from the list and click on it
        productCatalogue.getProductByName(input.get("SearchedData"));
        Thread.sleep(3000);
        productCatalogue.clickOnProduct(input.get("SearchedData"));
        Thread.sleep(3000);

        // Step 4: Initialize ProductPage, check pincode availability, and add the product to the cart
        ProductPage productPage = new ProductPage(getDriver());
        productPage.checkAndAddToCart(input.get("PinCode"));  // Example pincode

        // Step 5: Navigate to the CartPage and place the order
        CartPage cartPage = new CartPage(getDriver());
        cartPage.proceedWithOrder();  // Click on 'Place Order' button
    }

    @Test(dataProvider = "getData", dependsOnMethods = {"testShoppingFlow"}, groups = {"Regression"})
    public void TestValidations(HashMap<String,String> input) throws InterruptedException {
        // Step 1: Initialize SearchPage and perform a product search using provided product name
        SearchPage searchPage = new SearchPage(getDriver());  // Fetch the initialized WebDriver
        searchPage.SearchItems(input.get("Product"));
        Thread.sleep(3000);  // Wait for search results to load

        // Step 2: Initialize ProductCatalogue and get the list of products
        ProductCatalogue productCatalogue = new ProductCatalogue(getDriver());
        List<WebElement> products = productCatalogue.getProductList();

        // Step 3: Locate and click on the specific product by name
        productCatalogue.getProductByName(input.get("SearchedData"));
        Thread.sleep(3000);
        productCatalogue.clickOnProduct(input.get("SearchedData"));
        Thread.sleep(3000);

        // Step 4: Initialize ProductPage and check pincode availability with an invalid pincode
        ProductPage productPage = new ProductPage(getDriver());
        productPage.checkPincodeAvailability(input.get("InvalidPinCode"));  // Example of entering an invalid pincode

        // Step 5: Validate that the error message for the incorrect pincode is displayed
        Assert.assertEquals(productPage.ErrorMessage(), "Not a valid pincode");
    }
}