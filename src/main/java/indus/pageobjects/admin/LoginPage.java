package indus.pageobjects.admin;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import indus.abstractcomponent.AbstractComponent;
import indus.utils.ConfigReader;
import indus.annotations.StepName;

public class LoginPage extends AbstractComponent {

	public LoginPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	// ── Tabs ─────────────────────────────────────────────────────────────
	@FindBy(xpath = "//div//span[contains(text(),'Email & Password')] | //button[contains(.,'Email & Password')]")
	private WebElement emailLoginTab;

	@FindBy(xpath = "//div//span[contains(text(),'Phone & OTP')] | //button[contains(.,'Phone & OTP')]")
	private WebElement phoneOtpTab;

	// ── Email & Password Form ────────────────────────────────────────────
	@FindBy(xpath = "//input[@type='email']")
	private WebElement emailAddress;

	@FindBy(xpath = "//input[@type='password']")
	private WebElement password;

	@FindBy(xpath = "//button[@type='submit' and (normalize-space()='Sign In' or normalize-space()='Sign in')]")
	private WebElement signinButton;

	// ── 2FA & OTP Form ───────────────────────────────────────────────────
	@FindBy(xpath = "//input[@inputmode='numeric']")
	private List<WebElement> otpInputs;

	@FindBy(xpath = "//button[@type='submit' and contains(.,'Verify & Sign In')]")
	private WebElement verifyOtpButton;

	@FindBy(xpath = "//button[contains(.,'Resend OTP')]")
	private WebElement resendOtpButton;

	@FindBy(xpath = "//button[contains(.,'Change')]")
	private WebElement changeOtpTargetButton;

	// ── Active Sessions Modal ────────────────────────────────────────────
	@FindBy(xpath = "//h3[normalize-space()='Active Session Limit Reached']")
	private WebElement activeSessionModalTitle;

	@FindBy(xpath = "//button[contains(.,'Force Logout')]")
	private WebElement forceLogoutButton;

	// ── Validation Errors & Alerts ───────────────────────────────────────
	@FindBy(xpath = "//p[contains(@class,'text-status-fail') or contains(@class,'bg-red-50')]")
	private WebElement alertErrorBanner;

	@FindBy(xpath = "//form//p[contains(@class,'text-status-fail') or contains(@class,'text-red')]")
	private List<WebElement> fieldErrors;

	// ── Dashboard & Profile Indicators ───────────────────────────────────
	@FindBy(xpath = "//p[normalize-space()='Overall Compliance']")
	private WebElement overallCompliance;

	@FindBy(xpath = "//h1[contains(.,'Access Control')] | //a[contains(@href,'access-control')]")
	private WebElement superAdminAccessControlIndicator;

	@FindBy(xpath = "//header//button[.//span[contains(@class,'hidden md:inline-flex')] and .//div]")
	private WebElement profile;

	@FindBy(xpath = "//button[contains(normalize-space(.),'Sign out')]")
	private WebElement signOut;

	@FindBy(xpath = "//div[contains(@class,'flex justify-end')]//button[.//text()[contains(.,'Sign out')]]")
	private WebElement confirmSignOut;

	// ─────────────────────────────────────────────────────────────────────
	// Tab Actions
	// ─────────────────────────────────────────────────────────────────────
	@StepName("Click Email & Password tab")
	public void goToLogin() {
		driver.get(ConfigReader.getUrl());
	}

	public void loginByEmail() {
		waitElementToAppear(emailLoginTab);
		waitElementToBeClickable(emailLoginTab);
		emailLoginTab.click();
	}

	@StepName("Click Phone & OTP tab")
	public void loginByPhone() {
		waitElementToAppear(phoneOtpTab);
		waitElementToBeClickable(phoneOtpTab);
		phoneOtpTab.click();
	}

	// ─────────────────────────────────────────────────────────────────────
	// Input Fields
	// ─────────────────────────────────────────────────────────────────────
	@StepName("Enter User Email Address")
	public void enterUserEmail(String username) {
		waitForVisibility(emailAddress);
		waitElementToBeClickable(emailAddress);
		clearAndType(emailAddress, username);
	}

	@StepName("Enter User Password")
	public void enterPassword(String pwd) {
		waitForVisibility(password);
		waitElementToBeClickable(password);
		clearAndType(password, pwd);
	}

	@StepName("Click Sign In Button")
	public void clickLogin() {
		waitElementToBeClickable(signinButton);
		signinButton.click();
	}

	// ─────────────────────────────────────────────────────────────────────
	// 2FA / OTP Handling
	// ─────────────────────────────────────────────────────────────────────
	@StepName("Check if 2FA OTP Screen is displayed")
	public boolean isOtpScreenDisplayed() {
		try {
			if (otpInputs != null && !otpInputs.isEmpty()) {
				return otpInputs.get(0).isDisplayed();
			}
		} catch (Exception ignored) {}
		return false;
	}

	@StepName("Wait for 2FA OTP Screen to appear")
	public void waitForOtpScreen() {
		By otpLocator = By.xpath("//input[@inputmode='numeric']");
		waitUtils.waitForVisibility(otpLocator);
	}

	@StepName("Enter 6-digit 2FA OTP")
	public void enterOtp(String otp) {
		waitForOtpScreen();
		if (otpInputs != null && !otpInputs.isEmpty()) {
			// Clear first cell if needed
			otpInputs.get(0).clear();
			
			// Distribute OTP digits across input cells
			for (int i = 0; i < otp.length() && i < otpInputs.size(); i++) {
				WebElement cell = otpInputs.get(i);
				cell.sendKeys(String.valueOf(otp.charAt(i)));
			}
		}
	}

	@StepName("Click Verify & Sign In for 2FA")
	public void clickVerifyOtp() {
		waitElementToBeClickable(verifyOtpButton);
		verifyOtpButton.click();
	}

	@StepName("Handle Active Sessions Limit if modal appears")
	public boolean handleActiveSessionsIfPresent() {
		try {
			By modalBy = By.xpath("//h3[normalize-space()='Active Session Limit Reached']");
			WebElement title = waitUtils.waitForVisibility(modalBy);
			if (title != null && title.isDisplayed()) {
				waitElementToBeClickable(forceLogoutButton);
				forceLogoutButton.click();
				return true;
			}
		} catch (Exception ignored) {
			// Modal did not appear, proceed normally
		}
		return false;
	}

	// ─────────────────────────────────────────────────────────────────────
	// Composite Login Flows
	// ─────────────────────────────────────────────────────────────────────
	@StepName("Perform Standard Admin Login")
	public void login(String userEmail, String userPassword) {
		loginByEmail();
		enterUserEmail(userEmail);
		enterPassword(userPassword);
		clickLogin();
		handleActiveSessionsIfPresent();
	}

	@StepName("Perform Super Admin Login with 2FA OTP")
	public void loginAsSuperAdmin(String userEmail, String userPassword, String otp) {
		loginByEmail();
		enterUserEmail(userEmail);
		enterPassword(userPassword);
		clickLogin();

		// Handle 2FA OTP verification
		enterOtp(otp);
		clickVerifyOtp();

		// Evict previous active session if limit reached
		handleActiveSessionsIfPresent();
	}

	// ─────────────────────────────────────────────────────────────────────
	// Validation Error Queries
	// ─────────────────────────────────────────────────────────────────────
	public String getAlertErrorText() {
		try {
			waitElementToAppear(alertErrorBanner);
			return alertErrorBanner.getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	public boolean hasFieldError(String expectedSnippet) {
		for (WebElement err : fieldErrors) {
			try {
				if (err.isDisplayed() && err.getText().toLowerCase().contains(expectedSnippet.toLowerCase())) {
					return true;
				}
			} catch (Exception ignored) {}
		}
		return false;
	}

	// ─────────────────────────────────────────────────────────────────────
	// Post-Login Surface Indicators
	// ─────────────────────────────────────────────────────────────────────
	@StepName("Admin Overview Page Header Verification")
	public String adminOverallCompliance() {
		waitElementToAppear(overallCompliance);
		return overallCompliance.getText();
	}

	@StepName("Verify Super Admin Access Control is displayed")
	public boolean isSuperAdminAccessControlDisplayed() {
		try {
			waitElementToAppear(superAdminAccessControlIndicator);
			return superAdminAccessControlIndicator.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// ─────────────────────────────────────────────────────────────────────
	// Sign Out (Synchronization Clean without Thread.sleep)
	// ─────────────────────────────────────────────────────────────────────
	@StepName("Click on the Profile Icon")
	public void clickProfile() {
		waitElementToAppear(profile);
		waitElementToBeClickable(profile);
		profile.click();
	}

	@StepName("Click Sign out Button")
	public void clickSignOut() {
		waitElementToBeClickable(signOut);
		signOut.click();
	}

	@StepName("Click Confirm Sign out Button")
	public void clickConfirmSignOut() {
		waitElementToBeClickable(confirmSignOut);
		confirmSignOut.click();
	}

	@StepName("Perform Complete Sign Out and wait for Login Page")
	public void signOutFromPortal() {
		clickProfile();
		clickSignOut();
		clickConfirmSignOut();
		waitElementToAppear(emailLoginTab);
	}

	public void clearFields() {
		try {
			emailAddress.clear();
			password.clear();
		} catch (Exception e) {
			clearUsingJS(emailAddress);
			clearUsingJS(password);
		}
	}

	private void clearAndType(WebElement el, String text) {
		try {
			el.clear();
		} catch (Exception e) {
			clearUsingJS(el);
		}
		if (text != null) {
			el.sendKeys(text);
		}
	}

	private void clearUsingJS(WebElement el) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].value='';", el);
		} catch (Exception ignored) {}
	}
}
