package indus.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;

import indus.selenium.listener.SeleniumListener;
import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverFactory {
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	public WebDriver initializeDriver() throws IOException {
		Properties prop = new Properties();
		String path = System.getProperty("user.dir")
				+ "/src/main/resources/testdata/GlobalData.properties";

		try (FileInputStream fis = new FileInputStream(path)) {
			prop.load(fis);
		} catch (Exception ignored) {
		}

		String browserName = ConfigReader.getBrowser().toLowerCase();
		boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "false"))
				|| browserName.contains("headless");

		if (browserName.contains("chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			if (isHeadless) {
				options.addArguments("--headless=new");
				options.addArguments("--no-sandbox");
				options.addArguments("--disable-dev-shm-usage");
				options.addArguments("--disable-gpu");
				options.addArguments("--window-size=1920,1080");
			}
			driver.set(new ChromeDriver(options));
		} else if (browserName.contains("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions options = new FirefoxOptions();
			if (isHeadless) {
				options.addArguments("-headless");
				options.addArguments("--window-size=1920,1080");
			}
			driver.set(new FirefoxDriver(options));
		} else if (browserName.contains("edge")) {
			WebDriverManager.edgedriver().setup();
			EdgeOptions options = new EdgeOptions();
			if (isHeadless) {
				options.addArguments("--headless=new");
				options.addArguments("--no-sandbox");
				options.addArguments("--disable-dev-shm-usage");
				options.addArguments("--window-size=1920,1080");
			}
			driver.set(new EdgeDriver(options));
		} else {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			if (isHeadless) {
				options.addArguments("--headless=new");
				options.addArguments("--no-sandbox");
				options.addArguments("--disable-dev-shm-usage");
				options.addArguments("--disable-gpu");
				options.addArguments("--window-size=1920,1080");
			}
			driver.set(new ChromeDriver(options));
		}

		SeleniumListener listener = new SeleniumListener();
		EventFiringDecorator<WebDriver> decorator =
				new EventFiringDecorator<>(listener);

		WebDriver decoratedDriver = decorator.decorate(driver.get());
		driver.set(decoratedDriver);

		if (!isHeadless) {
			getDriver().manage().window().maximize();
		}

		getDriver().manage().timeouts()
				.implicitlyWait(Duration.ofSeconds(2));
		return decoratedDriver;
	}

	public static WebDriver getDriver() {
		return driver.get();
	}

	public static void quitDriver() {
		if (driver.get() != null) {
			driver.get().quit();
			driver.remove();
		}
	}
}
