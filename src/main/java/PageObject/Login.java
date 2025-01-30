package PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Login {
    WebDriver driver;

    public Login(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Locators
    @FindBy(xpath="//a[@class='login' and @title='Log in to your customer account' and contains(text(), 'Sign in')]")
    WebElement signInLink;

    @FindBy(xpath="//input[@class='is_required validate account_input form-control' and @type='text' and @id='email' and @name='email']")
    WebElement emailAddress;

    @FindBy(xpath="//input[@class='is_required validate account_input form-control' and @type='password' and @id='passwd' and @name='passwd']")
    WebElement passwordField;

    @FindBy(xpath="//button[@type='submit']//i[@class='icon-lock left']")
    WebElement signInButton;

    // Methods
    public void openSignInPage() {
        signInLink.click();
    }

    public void LoginApplication(String email, String password) {
        emailAddress.sendKeys(email);
        passwordField.sendKeys(password);
        signInButton.click();
    }

    public void Url(String url) {
        // Navigate to the URL
        driver.get(url);
    }
}