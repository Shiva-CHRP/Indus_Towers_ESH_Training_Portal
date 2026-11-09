package indus.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtils {

	public static String getScreenshot(WebDriver driver, String testName) {

		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String sanitizedName = testName != null ? testName.replaceAll("[^a-zA-Z0-9_.-]", "_") : "screenshot";
		if (sanitizedName.length() > 60) {
			sanitizedName = sanitizedName.substring(0, 60);
		}

		String path = System.getProperty("user.dir") + "/reports/screenshots/" + sanitizedName + "_" + timestamp + ".png";

		try {
			WebDriver activeDriver = driver != null ? driver : WebDriverFactory.getDriver();
			if (activeDriver instanceof TakesScreenshot) {
				File src = ((TakesScreenshot) activeDriver).getScreenshotAs(OutputType.FILE);
				File dest = new File(path);
				FileUtils.copyFile(src, dest);
			}
		} catch (Exception e) {
			// Best-effort screenshot capture
		}

		return path;
	}
}
