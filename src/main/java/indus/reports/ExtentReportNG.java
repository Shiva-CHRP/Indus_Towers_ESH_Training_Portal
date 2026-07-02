package indus.reports;

import org.openqa.selenium.BuildInfo;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import indus.utils.ConfigReader;

public class ExtentReportNG {

	private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter("test-output/AutomationReport1.html");
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setReportName("ESH Portal Automation Report");
            spark.config().setDocumentTitle("Automation Report");
            spark.config().setTimelineEnabled(true);
            spark.config().setEncoding("UTF-8");
            spark.config().setJs("document.querySelector('.navbar-brand').innerHTML="+"'"+"<img src=\"logo.png\" width=\"180\">"+"'");
            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Project",ConfigReader.get("project"));
            spark.config().setCss(
            	    ".logo { display:block; margin-bottom:10px; }"
            	);
            extent.setSystemInfo("Release",
                    ConfigReader.get("release"));
            extent.setSystemInfo("Environment",
                    ConfigReader.get("environment"));
            
           
            extent.setSystemInfo("OS",
                    System.getProperty("os.name"));
            extent.setSystemInfo("Java",
                    System.getProperty("java.version"));
            BuildInfo buildInfo = new BuildInfo();
            extent.setSystemInfo("Selenium Version", buildInfo.getReleaseLabel());
            extent.setSystemInfo("Framework",
                    ConfigReader.get("framework"));
            extent.setSystemInfo("Executed By",
                    ConfigReader.get("tester"));
            
        }
        return extent;
    }
}
