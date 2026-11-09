package indus.utils;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtils {

	private WebDriver driver;
	private WebDriverWait wait;

	public WaitUtils(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	// COMPOSITE / DYNAMIC WAIT
	public <T> T waitUntil(Function<? super WebDriver, T> isTrue) {
		return wait.until(isTrue);
	}

	// BACKDROP DISAPPEARANCE
	public void waitForBackdropToDisappear() {
		try {
			By backdrop = By.xpath("//div[contains(@class,'bg-black/60') or contains(@class,'backdropFadeIn')]");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(backdrop));
		} catch (Exception ignored) {
		}
	}

	// VISIBILITY
	public void waitForVisibility(WebElement element) {
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public WebElement waitForVisibility(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public void waitForVisibility(List<WebElement> element) {
		wait.until(ExpectedConditions.visibilityOfAllElements(element));
	}

	// CLICKABLE
	public void waitForClickable(WebElement element) {
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public void waitForClickable(By locator) {
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	// RESILIENT CLICKING (HANDLES INTERCEPTIONS & LINGERING BACKDROPS)
	public void clickElement(WebElement element) {
		try {
			waitForBackdropToDisappear();
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
		} catch (org.openqa.selenium.ElementClickInterceptedException e) {
			waitForBackdropToDisappear();
			try {
				element.click();
			} catch (Exception ex) {
				clickUsingJS(element);
			}
		} catch (Exception e) {
			clickUsingJS(element);
		}
	}

	public void clickElement(By locator) {
		try {
			waitForBackdropToDisappear();
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
			element.click();
		} catch (org.openqa.selenium.ElementClickInterceptedException e) {
			waitForBackdropToDisappear();
			try {
				driver.findElement(locator).click();
			} catch (Exception ex) {
				WebElement element = driver.findElement(locator);
				clickUsingJS(element);
			}
		} catch (Exception e) {
			WebElement element = driver.findElement(locator);
			clickUsingJS(element);
		}
	}

	// INVISIBILITY
	public void waitForInvisibility(WebElement element) {
		wait.until(ExpectedConditions.invisibilityOf(element));
	}

	public boolean waitForInvisibility(By locator) {
		return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	// ATTRIBUTE
	public void waitForAttributeToBe(WebElement element, String attribute, String value) {
		wait.until(ExpectedConditions.attributeToBe(element, attribute, value));
	}

	// ALERT
	public void waitForAlert() {
		wait.until(ExpectedConditions.alertIsPresent());
	}

	// TITLE
	public void waitForTitleContains(String title) {
		wait.until(ExpectedConditions.titleContains(title));
	}

	// URL
	public void waitForUrlContains(String url) {
		wait.until(ExpectedConditions.urlContains(url));
	}

	// SCROLL
	public void scrollIntoView(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	// JS CLICK
	public void clickUsingJS(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}

	public static void waitForPageLoad(WebDriver driver) {
		if (driver == null) {
			return;
		}
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(webDriver ->
			((JavascriptExecutor) webDriver)
				.executeScript("return document.readyState")
				.equals("complete")
		);
	}

}
