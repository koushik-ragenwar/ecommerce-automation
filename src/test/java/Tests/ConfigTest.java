package Tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigTest {

    // WebDriver instance
    private WebDriver driver;

    // Method to initialize the browser based on the configuration file
    public WebDriver initializeBrowser() throws IOException {

        // Step 1: Load the properties file to fetch configuration values
        Properties prop = new Properties();
        FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/Config.properties");
        prop.load(file);

        // Step 2: Retrieve the browser name from the properties file
        String browserName = prop.getProperty("browser").toLowerCase().trim();

        // Step 3: Initialize the WebDriver based on the specified browser
        switch (browserName) {
            case "safari":
                // Set up SafariDriver
                WebDriverManager.safaridriver().setup();
                // Launch Safari browser
                driver = new SafariDriver();
                break;
            case "chrome":
                // Set up ChromeDriver
                WebDriverManager.chromedriver().setup();
                // Launch Chrome browser
                driver = new ChromeDriver();
                break;
            default:
                // Handle incorrect browser names
                throw new IllegalArgumentException("Invalid browser name in Config.properties: " + browserName);
        }

        // Step 4: Maximize the browser window after initialization
        driver.manage().window().maximize();
        return driver;
    }

    // Getter method to retrieve the WebDriver instance
    public WebDriver getDriver() {

        // Ensure the browser is initialized before accessing
        if (driver == null) {
            throw new IllegalStateException("Browser is not initialized. Call initializeBrowser() first.");
        }
        return driver;
    }

    public void LaunchApplication() throws IOException, InterruptedException {
        driver = initializeBrowser();
        // Open Flipkart
        driver.get("https://www.flipkart.com");
        // Wait for the page to load
        Thread.sleep(3000);
    }
}