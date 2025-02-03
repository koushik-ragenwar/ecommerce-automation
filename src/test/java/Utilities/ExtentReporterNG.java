package Utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;


public class ExtentReporterNG {

    public static ExtentReports getReportObject(){
        String path = System.getProperty("user.dir")+"/Reports/index.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("Flipkart Automation Results");
        reporter.config().setDocumentTitle("Test Results");
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("SDET", "Kaushik Ragenwar");
        extent.createTest(path);
        return extent;
    }
}
