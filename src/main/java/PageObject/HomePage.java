package PageObject;

import Common.Timers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends Timers {

    // Locator for the "Home" button
    @FindBy(xpath = "//i[@class='icon-chevron-left']")
    WebElement HomeButton;

    // WebDriver instance to interact with the browser
    WebDriver driver;

    // Constructor initializes the WebDriver and the PageFactory elements
    public HomePage(WebDriver driver) {
        super(driver);  // Calling the parent constructor for any timer-related functionality
        this.driver = driver;
        PageFactory.initElements(driver, this);  // Initializes the web elements annotated with @FindBy
    }

    // Method to click on the "Home" button
    public void ClickHomePage(){
        // Wait for the "Home" button to appear on the page before clicking
        waitForElementToAppear((By) HomeButton);

        // Click the "Home" button to navigate to the home page
        HomeButton.click();
    }
}