package Tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class ConfigTest {

    // WebDriver instance used across tests
    private WebDriver driver;

    // Method to initialize the browser based on the configuration file
    public WebDriver initializeBrowser() throws IOException {

        // Step 1: Load the properties file that contains browser configuration
        Properties prop = new Properties();
        FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/Config.properties");
        prop.load(file);

        // Step 2: Retrieve the browser name (chrome or safari) from the properties file
        String browserName = prop.getProperty("browser").toLowerCase().trim();

        // Step 3: Initialize WebDriver based on the browser specified
        switch (browserName) {
            case "safari":
                // Set up Safari WebDriver and launch Safari browser
                WebDriverManager.safaridriver().setup();
                driver = new SafariDriver();
                break;
            case "chrome":
                // Set up Chrome WebDriver and launch Chrome browser
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            default:
                // Throw an exception if the browser name is invalid or not supported
                throw new IllegalArgumentException("Invalid browser name in Config.properties: " + browserName);
        }

        // Step 4: Maximize the browser window after initialization
        driver.manage().window().maximize();
        return driver;
    }

    // Getter method to retrieve the WebDriver instance for use in test cases
    public WebDriver getDriver() {

        // Ensure the WebDriver is initialized before it is used
        if (driver == null) {
            throw new IllegalStateException("Browser is not initialized. Call initializeBrowser() first.");
        }
        return driver;
    }

    // BeforeMethod annotation ensures that the browser is launched before each test method
    @BeforeMethod(alwaysRun = true)
    public void LaunchApplication() throws IOException, InterruptedException {
        // Step 1: Initialize the WebDriver
        driver = initializeBrowser();

        // Step 2: Open the Flipkart application in the browser
        driver.get("https://www.flipkart.com");

        // Step 3: Wait for the page to fully load before interacting
        Thread.sleep(3000);
    }

    // AfterMethod annotation ensures that the browser is closed after each test method
    @AfterMethod(alwaysRun = true)
    public void QuitBrowser() {
        // Step 1: Close the browser after the test execution is completed
        driver.quit();
    }

    // Method to read data from a JSON file and return it as a List of HashMaps
    public List<HashMap<String, String>> DataReader(String filepath) throws IOException {
        // Step 1: Read the JSON file content into a string
        String jsonContent = FileUtils.readFileToString(new File(filepath), StandardCharsets.UTF_8);

        // Step 2: Convert the JSON string into a List of HashMaps using Jackson Databind
        ObjectMapper mapper = new ObjectMapper();
        List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {});

        return data;
    }
}