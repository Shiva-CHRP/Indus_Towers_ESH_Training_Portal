package indus.utils;

import java.time.Duration;
import java.util.List;

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
	private By toastLocator = By.xpath(
			"//li[@data-sonner-toast] | " +
			"//div[@aria-live='polite']/div | " +
			"//div[@aria-live='polite']//p | " +
			"//div[contains(@class,'fixed bottom-4')]//div[contains(@class,'shadow-lg')] | " +
			"//div[@role='alert'] | " +
			"//*[@role='status']");

	public ToastUtils(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(8));
	}

	public ToastResponse captureToast() {
		try {
			WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(toastLocator));
			String message = el.getText().trim();

			if (message.isEmpty()) {
				List<WebElement> texts = el.findElements(By.xpath(".//p | .//span"));
				StringBuilder sb = new StringBuilder();
				for (WebElement t : texts) {
					String txt = t.getText().trim();
					if (!txt.isEmpty()) {
						if (sb.length() > 0) sb.append(" ");
						sb.append(txt);
					}
				}
				message = sb.toString();
			}

			String type;
			String lower = message.toLowerCase();
			if (lower.contains("added") || lower.contains("success") || lower.contains("created")
					|| lower.contains("updated") || lower.contains("assigned") || lower.contains("activated")
					|| lower.contains("removed") || lower.contains("saved") || lower.contains("sent")) {
				type = "SUCCESS";
			} else if (lower.contains("error") || lower.contains("failed") || lower.contains("already exists")
					|| lower.contains("could not") || lower.contains("invalid") || lower.contains("not supported")
					|| lower.contains("required") || lower.contains("cannot")) {
				type = "ERROR";
			} else {
				type = "INFO";
			}
			return new ToastResponse(type, message);
		} catch (Exception e) {
			return new ToastResponse("UNKNOWN", "");
		}
	}

	public void waitForToastToDisappear() {
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(toastLocator));
		} catch (Exception ignored) {
		}
	}

	public void verifyToast(SoftAssert softAssert, String expectedSuccess, String expectedError) {
		ToastResponse toast = captureToast();
		verifyToast(softAssert, toast, expectedSuccess, expectedError);
	}

	public void verifyToast(SoftAssert softAssert, ToastResponse toast, String expectedSuccess, String expectedError) {
		if (toast == null || toast.getMessage().isEmpty()) {
			softAssert.fail("Toast not displayed or disappeared before capture");
			return;
		}

		String lowerMsg = toast.getMessage().toLowerCase();
		if ("success".equalsIgnoreCase(toast.getType())) {
			softAssert.assertTrue(lowerMsg.contains(expectedSuccess.toLowerCase()),
					"Success toast mismatch. Expected to contain: [" + expectedSuccess + "] but got: [" + toast.getMessage() + "]");
		} else if ("error".equalsIgnoreCase(toast.getType())) {
			softAssert.assertTrue(lowerMsg.contains(expectedError.toLowerCase()),
					"Error toast mismatch. Expected to contain: [" + expectedError + "] but got: [" + toast.getMessage() + "]");
		} else {
			softAssert.fail("Toast type unknown or not matching UI. Type: " + toast.getType() + ", Message: " + toast.getMessage());
		}

		waitForToastToDisappear();
	}
}
