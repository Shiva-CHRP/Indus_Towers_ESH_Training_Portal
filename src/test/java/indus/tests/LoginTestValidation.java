package indus.tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import indus.annotations.TestInfo;
import indus.listener.Listener;
import indus.testcomponents.BaseTest;
import indus.utils.AssertUtils;
import indus.utils.ConfigReader;
import indus.utils.TestDataUtil;
import indus.utils.ToastResponse;

@Listeners(Listener.class)
public class LoginTestValidation extends BaseTest {

	@Test(priority = 1, groups = {"Smoke", "Sanity", "Regression"})
	@TestInfo(author = "Shiva", module = "Login", description = "Verify Admin login using valid email and password", priority = "P1")
	public void verify_Login_With_Valid_Email() {
		loginPage.loginByEmail();
		loginPage.enterUserEmail(TestDataUtil.getData("loginValidation.validLogin.useremail"));
		loginPage.enterPassword(TestDataUtil.getData("loginValidation.validLogin.password"));
		loginPage.clickLogin();

		// Evict active session if concurrent session limit is prompted
		boolean evicted = loginPage.handleActiveSessionsIfPresent();

		// Capture toast notification if displayed
		if (!evicted) {
			try {
				ToastResponse toast = toastUtils.captureToast();
				if ("success".equalsIgnoreCase(toast.getType())) {
					softAssert.assertTrue(toast.getMessage().contains("Login successful") 
							|| toast.getMessage().contains("Redirecting"), "Success toast message mismatch");
				}
				toastUtils.waitForToastToDisappear();
			} catch (Exception ignored) {
				// Toast dismissed or fast navigation to dashboard
			}
		} else {
			toastUtils.waitForToastToDisappear();
		}

		String actualText = loginPage.adminOverallCompliance();
		AssertUtils.assertEquals(actualText, "OVERALL COMPLIANCE", "Admin Overview Dashboard Loaded Successfully");

		// Clean sign out without Thread.sleep
		loginPage.signOutFromPortal();
	}

	@Test(priority = 2, groups = {"Regression"})
	@TestInfo(author = "Shiva", module = "Login", description = "Verify validation error when submitting empty email and password", priority = "P2")
	public void verify_Login_With_Empty_Credentials() {
		loginPage.loginByEmail();
		loginPage.clearFields();
		loginPage.clickLogin();

		boolean hasEmailError = loginPage.hasFieldError("Email is required");
		softAssert.assertTrue(hasEmailError, "Validation message 'Email is required.' should be displayed");
	}

	@Test(priority = 3, groups = {"Sanity", "Regression"})
	@TestInfo(author = "Shiva", module = "Login", description = "Verify validation error when submitting malformed email address", priority = "P2")
	public void verify_Login_With_Invalid_Email_Format() {
		loginPage.loginByEmail();
		loginPage.clearFields();
		loginPage.enterUserEmail(TestDataUtil.getData("loginValidation.invalidEmailFormat.useremail"));
		loginPage.enterPassword(TestDataUtil.getData("loginValidation.invalidEmailFormat.password"));
		loginPage.clickLogin();

		boolean hasFormatError = loginPage.hasFieldError("valid email");
		softAssert.assertTrue(hasFormatError, "Validation message 'Enter a valid email address.' should be displayed");
	}

	@Test(priority = 4, groups = {"Regression"})
	@TestInfo(author = "Shiva", module = "Login", description = "Verify validation error when password length is less than 6 characters", priority = "P2")
	public void verify_Login_With_Short_Password() {
		loginPage.loginByEmail();
		loginPage.clearFields();
		loginPage.enterUserEmail(TestDataUtil.getData("loginValidation.passwordLength.useremail"));
		loginPage.enterPassword(TestDataUtil.getData("loginValidation.passwordLength.password"));
		loginPage.clickLogin();

		boolean hasLengthError = loginPage.hasFieldError("at least 6 characters");
		softAssert.assertTrue(hasLengthError, "Validation message 'Password must be at least 6 characters.' should be displayed");
	}

	@Test(priority = 5, groups = {"Sanity", "Regression"})
	@TestInfo(author = "Shiva", module = "Login", description = "Verify error notification when submitting unregistered credentials", priority = "P1")
	public void verify_Login_With_Unregistered_Credentials() {
		loginPage.loginByEmail();
		loginPage.clearFields();
		loginPage.enterUserEmail(TestDataUtil.getData("loginValidation.invalidCredentials.useremail"));
		loginPage.enterPassword(TestDataUtil.getData("loginValidation.invalidCredentials.password"));
		loginPage.clickLogin();

		try {
			ToastResponse toast = toastUtils.captureToast();
			softAssert.assertEquals(toast.getType(), "ERROR", "Toast type should be ERROR for invalid credentials");
			toastUtils.waitForToastToDisappear();
		} catch (Exception e) {
			String alertMsg = loginPage.getAlertErrorText();
			softAssert.assertTrue(!alertMsg.isEmpty(), "Error alert should be displayed for invalid credentials");
		}
	}

	@Test(priority = 6, groups = {"Smoke", "Sanity", "Regression", "E2E"})
	@TestInfo(author = "Shiva", module = "Login", description = "Verify Super Admin login flow requiring 2FA OTP verification", priority = "P1")
	public void verify_SuperAdmin_Login_With_2FA() {
		loginPage.loginByEmail();
		loginPage.clearFields();

		String saEmail = ConfigReader.getSuperAdminEmail();
		String saPassword = ConfigReader.getSuperAdminPassword();
		String saOtp = ConfigReader.getStaticOtp();

		loginPage.enterUserEmail(saEmail);
		loginPage.enterPassword(saPassword);
		loginPage.clickLogin();

		// Handle 2FA OTP verification
		loginPage.waitForOtpScreen();
		loginPage.enterOtp(saOtp);
		loginPage.clickVerifyOtp();

		// Handle session limit eviction if modal pops up
		loginPage.handleActiveSessionsIfPresent();

		// Verify Super Admin lands on Access Control surface or Admin overview
		boolean isSuperAdminSurface = loginPage.isSuperAdminAccessControlDisplayed();
		softAssert.assertTrue(isSuperAdminSurface, "Super Admin should navigate to Access Control dashboard");

		// Clean sign out
		loginPage.signOutFromPortal();
	}

	@Test(priority = 7, groups = {"Regression"})
	@TestInfo(author = "Shiva", module = "Login", description = "Verify 2FA OTP error when submitting invalid 6-digit OTP", priority = "P2")
	public void verify_SuperAdmin_Login_With_Invalid_OTP() {
		loginPage.loginByEmail();
		loginPage.clearFields();

		String saEmail = ConfigReader.getSuperAdminEmail();
		String saPassword = ConfigReader.getSuperAdminPassword();
		String invalidOtp = TestDataUtil.getData("loginValidation.superAdminLogin.invalidOtp");

		loginPage.enterUserEmail(saEmail);
		loginPage.enterPassword(saPassword);
		loginPage.clickLogin();

		// Handle 2FA OTP screen
		loginPage.waitForOtpScreen();
		loginPage.enterOtp(invalidOtp);
		loginPage.clickVerifyOtp();

		// Assert OTP error feedback
		try {
			ToastResponse toast = toastUtils.captureToast();
			softAssert.assertEquals(toast.getType(), "ERROR", "Toast type should be ERROR for invalid OTP");
			toastUtils.waitForToastToDisappear();
		} catch (Exception e) {
			String alertMsg = loginPage.getAlertErrorText();
			softAssert.assertTrue(!alertMsg.isEmpty(), "Error banner should be displayed for invalid OTP");
		}
	}
}
