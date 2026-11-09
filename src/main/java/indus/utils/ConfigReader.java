package indus.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

	private static Properties prop = new Properties();

	static {
		String basePath = System.getProperty("user.dir");
		String globalPath = basePath + "/src/main/resources/testdata/GlobalData.properties";
		String configPath = basePath + "/src/main/resources/testdata/config.properties";

		try (FileInputStream fis = new FileInputStream(globalPath)) {
			prop.load(fis);
		} catch (IOException e) {
			System.err.println("Warning: Could not load GlobalData.properties: " + e.getMessage());
		}

		try (FileInputStream fis = new FileInputStream(configPath)) {
			prop.load(fis);
		} catch (Exception e) {
			System.err.println("Warning: Could not load config.properties: " + e.getMessage());
		}
	}

	// Get property by key with system property override
	public static String get(String key) {
		String sysVal = System.getProperty(key);
		if (sysVal != null && !sysVal.trim().isEmpty()) {
			return sysVal.trim();
		}
		return prop.getProperty(key);
	}

	// Environment resolution (staging | dev | local)
	public static String getEnvironment() {
		String env = System.getProperty("env");
		if (env == null || env.trim().isEmpty()) {
			env = System.getenv("ENV");
		}
		if (env == null || env.trim().isEmpty()) {
			env = prop.getProperty("default_env", "staging");
		}
		return env.trim().toLowerCase();
	}

	// Browser resolution
	public static String getBrowser() {
		String browser = System.getProperty("browser");
		if (browser != null && !browser.trim().isEmpty()) {
			return browser.trim();
		}
		return prop.getProperty("browser", "chrome");
	}

	// URL resolution with dynamic environment switching
	public static String getUrl() {
		// 1. Direct system property override (-Durl=...)
		String directUrl = System.getProperty("url");
		if (directUrl != null && !directUrl.trim().isEmpty()) {
			return directUrl.trim();
		}

		// 2. Resolve environment-specific URL
		String env = getEnvironment();
		String envUrlKey = "url." + env;
		String envUrl = prop.getProperty(envUrlKey);
		if (envUrl != null && !envUrl.trim().isEmpty()) {
			return envUrl.trim();
		}

		// 3. Fallback to default url property
		return prop.getProperty("url", "https://industower-ui-staging.xrdashboard.com/login");
	}

	public static String getUsername() {
		String email = prop.getProperty("email");
		if (email != null && !email.trim().isEmpty()) {
			return email;
		}
		return prop.getProperty("useremail");
	}

	public static String getAdminEmail() {
		return getUsername();
	}

	public static String getPassword() {
		return prop.getProperty("password");
	}

	public static String getAdminPassword() {
		return getPassword();
	}

	// OTP resolution
	public static String getStaticOtp() {
		String sysOtp = System.getProperty("otp");
		if (sysOtp != null && !sysOtp.trim().isEmpty()) {
			return sysOtp.trim();
		}
		return prop.getProperty("superadmin.otp", "123456");
	}

	// Super Admin credentials
	public static String getSuperAdminEmail() {
		return prop.getProperty("superadmin.email", "indus.superadmin@yopmail.com");
	}

	public static String getSuperAdminPassword() {
		return prop.getProperty("superadmin.password", "Smoke@12345");
	}

	// Manager credentials
	public static String getManagerEmail() {
		return prop.getProperty("manager.email", "manager@industowers.com");
	}

	public static String getManagerPassword() {
		return prop.getProperty("manager.password", "Manager@123");
	}

	// Learner credentials
	public static String getLearnerEmail() {
		return prop.getProperty("learner.email", "learner@industowers.com");
	}

	public static String getLearnerPassword() {
		return prop.getProperty("learner.password", "Learner@123");
	}

	public static String getLearnerPhone() {
		return prop.getProperty("learner.phone", "9876543210");
	}
}
