package indus.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import indus.utils.ConfigReader;

public class ConfigReaderFallbackTest {

	@Test(description = "Verify ConfigReader fallback to GlobalData.properties when no GitHub Secrets/Env are set")
	public void testConfigReaderFallbackToGlobalData() {
		// When no environment variables or system properties are set,
		// it should cleanly load from GlobalData.properties
		String adminUser = ConfigReader.getUsername();
		Assert.assertEquals(adminUser, "admin@industowers.com", "Admin username should fall back to GlobalData.properties");

		String adminPass = ConfigReader.getPassword();
		Assert.assertEquals(adminPass, "Admin@123", "Admin password should fall back to GlobalData.properties");

		String superAdminUser = ConfigReader.getSuperAdminEmail();
		Assert.assertEquals(superAdminUser, "superadmin@industowers.com", "Super Admin email should fall back to GlobalData.properties");

		String superAdminPass = ConfigReader.getSuperAdminPassword();
		Assert.assertEquals(superAdminPass, "SuperAdmin@123", "Super Admin password should fall back to GlobalData.properties");

		String managerUser = ConfigReader.getManagerEmail();
		Assert.assertEquals(managerUser, "manager@industowers.com", "Manager email should fall back to GlobalData.properties");

		String managerPass = ConfigReader.getManagerPassword();
		Assert.assertEquals(managerPass, "Manager@123", "Manager password should fall back to GlobalData.properties");

		String learnerUser = ConfigReader.getLearnerEmail();
		Assert.assertEquals(learnerUser, "learner@industowers.com", "Learner email should fall back to GlobalData.properties");

		String learnerPass = ConfigReader.getLearnerPassword();
		Assert.assertEquals(learnerPass, "Learner@123", "Learner password should fall back to GlobalData.properties");

		System.out.println(">>> ConfigReader Fallback to GlobalData.properties verified successfully! <<<");
	}

	@Test(description = "Verify ConfigReader priority when System Property / Secret override is supplied")
	public void testConfigReaderOverridePriority() {
		System.setProperty("ADMIN_USERNAME", "secret-admin@industowers.com");
		try {
			Assert.assertEquals(ConfigReader.getUsername(), "secret-admin@industowers.com", 
					"Should prioritize Secret / System property over GlobalData.properties");
		} finally {
			System.clearProperty("ADMIN_USERNAME");
		}

		// Verify it reverts back to GlobalData.properties when cleared
		Assert.assertEquals(ConfigReader.getUsername(), "admin@industowers.com", 
				"Should revert back to GlobalData.properties after clearing override");
	}
}
