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

	/**
	 * Helper to resolve value with fallback hierarchy:
	 * 1. System property (-DsysProp or -DenvVar)
	 * 2. Environment variable (from GitHub Repository Secret)
	 * 3. GlobalData.properties / config.properties lookup
	 * 4. Hardcoded fallback default
	 */
	private static String resolve(String envVar, String sysProp, String[] propKeys, String fallback) {
		// 1. Check System Properties (-DADMIN_USERNAME=... or -Dadmin.username=...)
		if (sysProp != null) {
			String val = System.getProperty(sysProp);
			if (val != null && !val.trim().isEmpty()) {
				return val.trim();
			}
		}
		if (envVar != null) {
			String val = System.getProperty(envVar);
			if (val != null && !val.trim().isEmpty()) {
				return val.trim();
			}
		}

		// 2. Check Environment Variable (populated from GitHub Repository Secret)
		if (envVar != null) {
			String envVal = System.getenv(envVar);
			if (envVal != null && !envVal.trim().isEmpty()) {
				return envVal.trim();
			}
		}

		// 3. Check GlobalData.properties / config.properties
		if (propKeys != null) {
			for (String key : propKeys) {
				String propVal = prop.getProperty(key);
				if (propVal != null && !propVal.trim().isEmpty()) {
					return propVal.trim();
				}
			}
		}

		// 4. Hardcoded fallback default
		return fallback;
	}

	// Get property by key with system property override
	public static String get(String key) {
		String sysVal = System.getProperty(key);
		if (sysVal != null && !sysVal.trim().isEmpty()) {
			return sysVal.trim();
		}
		return prop.getProperty(key);
	}

	// Environment resolution (staging | dev | local | prod)
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

		// 2. Direct environment variable override (URL / TARGET_URL)
		String envUrlDirect = System.getenv("URL");
		if (envUrlDirect != null && !envUrlDirect.trim().isEmpty()) {
			return envUrlDirect.trim();
		}

		// 3. Resolve environment-specific URL
		String env = getEnvironment();
		String envUrlKey = "url." + env;
		String envUrl = prop.getProperty(envUrlKey);
		if (envUrl != null && !envUrl.trim().isEmpty()) {
			return envUrl.trim();
		}

		// 4. Fallback to default url property
		return prop.getProperty("url", "https://industower-ui-staging.xrdashboard.com/login");
	}

	// ── Admin Credentials ────────────────────────────────────────────────
	public static String getUsername() {
		return resolve("ADMIN_USERNAME", "admin.username", 
				new String[]{"ADMIN_USERNAME", "email", "useremail"}, "admin@industowers.com");
	}

	public static String getAdminEmail() {
		return getUsername();
	}

	public static String getPassword() {
		return resolve("ADMIN_PASSWORD", "admin.password", 
				new String[]{"ADMIN_PASSWORD", "password"}, "Admin@123");
	}

	public static String getAdminPassword() {
		return getPassword();
	}

	// ── Super Admin Credentials & 2FA ────────────────────────────────────
	public static String getSuperAdminEmail() {
		return resolve("SUPER_ADMIN_USERNAME", "superadmin.email", 
				new String[]{"SUPER_ADMIN_USERNAME", "superadmin.email"}, "superadmin@industowers.com");
	}

	public static String getSuperAdminPassword() {
		return resolve("SUPER_ADMIN_PASSWORD", "superadmin.password", 
				new String[]{"SUPER_ADMIN_PASSWORD", "superadmin.password"}, "SuperAdmin@123");
	}

	public static String getStaticOtp() {
		return resolve("SUPER_ADMIN_OTP", "otp", 
				new String[]{"superadmin.otp", "otp"}, "123456");
	}

	// ── Manager Credentials ──────────────────────────────────────────────
	public static String getManagerEmail() {
		return resolve("MANAGER_USERNAME", "manager.email", 
				new String[]{"MANAGER_USERNAME", "manager.email"}, "manager@industowers.com");
	}

	public static String getManagerPassword() {
		return resolve("MANAGER_PASSWORD", "manager.password", 
				new String[]{"MANAGER_PASSWORD", "manager.password"}, "Manager@123");
	}

	// ── Learner Credentials ──────────────────────────────────────────────
	public static String getLearnerEmail() {
		return resolve("LEARNER_USERNAME", "learner.email", 
				new String[]{"LEARNER_USERNAME", "learner.email"}, "learner@industowers.com");
	}

	public static String getLearnerPassword() {
		return resolve("LEARNER_PASSWORD", "learner.password", 
				new String[]{"LEARNER_PASSWORD", "learner.password"}, "Learner@123");
	}

	public static String getLearnerPhone() {
		return resolve("LEARNER_PHONE", "learner.phone", 
				new String[]{"learner.phone"}, "9876543210");
	}
}
