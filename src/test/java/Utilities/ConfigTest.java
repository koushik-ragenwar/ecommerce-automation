package Utilities;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class ConfigTest {
    public WebDriver driver;

    public WebDriver initializeBrowser() throws IOException {
        Properties prop = new Properties();
        FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/Config.properties");

        prop.load(file);
        String browserName =System.getProperty("browser")!=null ? System.getProperty("browser") : prop.getProperty("browser").toLowerCase().trim();

        switch (browserName) {
            case "safari":
                driver = new SafariDriver();  // No need for WebDriverManager
                break;
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            default:
                throw new IllegalArgumentException("Invalid browser name in Config.properties: " + browserName);
        }

        driver.manage().window().maximize();
        return driver;
    }

    public WebDriver getDriver() {
        if (driver == null) {
            throw new IllegalStateException("Browser is not initialized. Call initializeBrowser() first.");
        }
        return driver;
    }

    @BeforeMethod(alwaysRun = true)
    public void LaunchApplication() throws IOException {
        driver = initializeBrowser();
        driver.get("https://www.flipkart.com");

        // Replace Thread.sleep(3000) with WebDriverWait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("flipkart.com"));
    }

    @AfterMethod(alwaysRun = true)
    public void QuitBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

    public List<HashMap<String, String>> DataReader(String filepath) throws IOException {
        String jsonContent = FileUtils.readFileToString(new File(filepath), StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {});
    }

    @DataProvider(name = "TestData")
    public Object[][] getData() throws IOException {
        List<HashMap<String, String>> data = DataReader(System.getProperty("user.dir") + "/src/test/resources/TestData.json");
        return new Object[][]{{data}};
    }

    public String getScreenShot(String testCaseName, WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File file = new File(System.getProperty("user.dir") + "/Reports/" + testCaseName + ".png");
        FileUtils.copyFile(source, file);
        return System.getProperty("user.dir") + "/Reports/" + testCaseName + ".png"; // Fixed "usr.dir" typo
    }
}