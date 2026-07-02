package indus.utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class ToastUtils {
	WebDriver driver;
	WebDriverWait wait;
	private WebElement toastElement;
	private By toastLocator = By.xpath("//div[@role='alert']");

	public ToastUtils(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public ToastResponse captureToast() {

		// By toast = By.xpath("//div[@role='alert' and (contains(.,'error') or
		// contains(.,'failed') or contains(.,'already exists'))]");

		WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(toastLocator));
		String message = el.getText().trim();

		String type;
		if (message.toLowerCase().contains("added") || message.toLowerCase().contains("success")) {
			type = "SUCCESS";
		} else if (message.toLowerCase().contains("error") || message.toLowerCase().contains("failed")
				|| message.toLowerCase().contains("already exists")) {
			type = "ERROR";
		} else {
			type = "INFO";
		}
		return new ToastResponse(type, message);

	}

	public void waitForToastToDisappear() {

		// By toast = By.xpath("//div[@role='alert' and (contains(.,'error') or
		// contains(.,'failed') or contains(.,'already exists'))]");

		wait.until(ExpectedConditions.invisibilityOfElementLocated(toastLocator));
	}

	public void verifyToast(SoftAssert softAssert, String expectedSuccess, String expectedError) {

		ToastResponse toast = captureToast();

		if ("success".equalsIgnoreCase(toast.getType())) {

			softAssert.assertEquals(toast.getMessage(), expectedSuccess, "Success toast mismatch");

		} else if ("error".equalsIgnoreCase(toast.getType())) {

			softAssert.assertEquals(toast.getMessage(), expectedError, "Error toast mismatch");

		} else {

			Assert.fail("Toast not displayed or locator not matching UI");
		}

		waitForToastToDisappear();
	}
}
