package indus.selenium.listener;

import java.time.LocalTime;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import indus.reports.ExtentTestManager;
import indus.utils.ScreenshotUtils;
import indus.utils.StepNameUtil;
import indus.utils.WaitUtils;

public class SeleniumListener implements WebDriverListener {
	WebDriver driver;

	public SeleniumListener() {
		// this.driver = driver;
	}

	private void log(String stepName) {
		/*
		 * try { WaitUtils.waitForPageLoad(driver); // String stepName =
		 * StepNameUtil.getStepName(); ExtentTestManager.getTest().pass(stepName,
		 * MediaEntityBuilder
		 * .createScreenCaptureFromPath(ScreenshotUtils.getScreenshot(driver,
		 * stepName)).build()); } catch (Exception e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); }
		 */
		try {

			ExtentTest test = ExtentTestManager.getTest();

			if (test == null) {
				System.out.println("ExtentTest is NULL → " + stepName);
				return;
			}

			if (driver == null) {
				System.out.println("Driver is NULL → " + stepName);
				return;
			}

			WaitUtils.waitForPageLoad(driver);

			String path = ScreenshotUtils.getScreenshot(driver, stepName);

			test.pass("<b>"+stepName+"</b><br>"+"Time : "+LocalTime.now(), 
					MediaEntityBuilder.createScreenCaptureFromPath(path).build());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void afterClick(WebElement element) {
		log(StepNameUtil.getStepName());
	}

	@Override
	public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
		log(StepNameUtil.getStepName());
	}

	@Override
	public void afterGet(WebDriver driver, String url) {
		this.driver = driver;
		//WaitUtils.waitForPageLoad(driver);
		//log(StepNameUtil.getStepName());
	}
}
