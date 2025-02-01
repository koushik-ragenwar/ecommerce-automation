package PageObject;

import Common.Timers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductPage extends Timers {

    WebDriver driver;

    // Constructor to initialize the WebDriver and PageFactory elements
    public ProductPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);  // Initialize elements with @FindBy annotations
    }

    // **Locators**
    @FindBy(xpath = "//input[@placeholder='Enter Delivery Pincode' and @id='pincodeInputId']")
    WebElement pincodeInput;  // Locator for the pincode input field

    @FindBy(xpath = "//span[contains(text(),'Check')]")
    WebElement checkPincodeButton;  // Locator for the 'Check' button to validate the pincode

    @FindBy(xpath = "//div[@class='hVvnXm']")  // Locator for the availability message
    WebElement availabilityMessage;

    @FindBy(xpath = "//button[text()='Add to cart' or @class='QqFHMw vslbG+ In9uk2 JTo6b7']")
    WebElement addToCartButton;  // Locator for the 'Add to Cart' button

    @FindBy(xpath = "//div[@class='nyRpc8']")
    WebElement errorMessage;  // Locator for the 'Add to Cart' button

    By availabilityMessageBy = By.xpath("//div[text()='Faster delivery by' or contains(text(), 'Delivery by') or contains(text(), 'Delivery in')]");  // XPath to identify the availability message element

    By availabilityErrorMessageBy = By.xpath("(//div[@class='nyRpc8'])[1]");  // XPath to identify the Invalid PinCode Error Message

    // **Method to Check Pincode Availability**
    public boolean checkPincodeAvailability(String pincode) throws InterruptedException {
        // Clear the existing pincode if any
        pincodeInput.clear();

        // Enter the new pincode value into the input field
        pincodeInput.sendKeys(pincode);
        Thread.sleep(3000);  // Wait for 3 seconds (ideally, use an explicit wait here)

        // Click the 'Check' button to check if the pincode is valid for delivery
        checkPincodeButton.click();

        // Wait for the availability message to appear
        try {
            Thread.sleep(2000);
            waitForElementToAppear(availabilityMessageBy);
        } catch (Exception e) {
            Thread.sleep(2000);
            waitForElementToAppear(availabilityErrorMessageBy);
        }


        // Return true if the availability message is displayed (indicating that the product is available for delivery)
        try {
            Thread.sleep(2000);
            return availabilityMessage.isDisplayed();
        } catch (Exception e) {
            Thread.sleep(2000);
            return driver.findElement(availabilityErrorMessageBy).isDisplayed();
        }

    }

    // **Method to Add Product to Cart**
    public void addToCart() throws InterruptedException {
        // Click on the 'Add to Cart' button to add the product to the shopping cart
        Thread.sleep(2000);
        addToCartButton.click();
        System.out.println("Product added to cart successfully!");  // Log the action
    }

    // **Main flow to check availability and add the product to cart**
    public void checkAndAddToCart(String pincode) throws InterruptedException {
        // Check if the product is available for the entered pincode
        if (checkPincodeAvailability(pincode)) {
            addToCart();  // If the product is available, add it to the cart
        } else {
            System.out.println("Product is not available for this pincode.");  // Log if the product is unavailable for the pincode
        }
    }

    public String ErrorMessage() throws InterruptedException {
        waitForElementToAppear(availabilityErrorMessageBy);
        return errorMessage.getText();
    }
}